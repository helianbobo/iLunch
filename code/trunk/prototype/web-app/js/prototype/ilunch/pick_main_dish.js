$(document).ready(function($){

	var undefined;

	var ilunch = $.ilunch_namespace("cn.ilunch");

	///////////////////////////////////////////////////////
	////////////////// initialization /////////////////////
	///////////////////////////////////////////////////////
	var mainDishList = null;
	var cart = null;
	var areaId = $('#area_id').val();
	if(!areaId || areaId == '')
		ilunch.fatalError("area id not found!");
	
	var today = new Date();
	var d = new Date();
	var currentM = null;
	var currentD = null;
	var currentY = null;
	var currentDay = null;
	var firstDateOfMonth = null;
	var skip = 0;
	var lastDateOfMonth = null;
	var headOffset = null;
	var ORDER_INADVANCE_DAY = 2;

	var listElem = $('#md_list');
	if(listElem.length <= 0)
		ilunch.fatalError('md_list elem not found!');
	var currentDateElem = $('#current_date');
	if(currentDateElem.length <= 0)
		ilunch.fatalError('current_date elem not found!');
	var in_total = $('#in_total');
	in_total.html('0');
	$('#last_month_btn').css({"display":"none"});
	
	function initialize() {
		currentM = d.getMonth();
		currentD = d.getDate();
		currentY = d.getFullYear();
		currentDay = d.getDay();
		firstDateOfMonth = new Date(currentY, currentM, 1);
		skip = 0;
		if(firstDateOfMonth.getDay() == 0)
			skip = 1;
		else if(firstDateOfMonth.getDay() == 6)
			skip = 2;
		
		lastDateOfMonth = new Date(currentY, currentM, 0);
		headOffset = firstDateOfMonth.getDay() > 1 ? firstDateOfMonth.getDay()-1 : 0;
		listElem.empty();
		currentDateElem.html(currentDateElem.html().replace('##YY##', currentY).replace('##MM##', 
				ilunch.doubleDigit(currentM+1)).replace('##DD##', 
						ilunch.doubleDigit(currentD)).replace('##WW##', ilunch.digitToCNSS(currentDay)));
	}
	
	initialize();
	
	///////////////////////////////////////////////////////
	////////////////// send request for data  /////////////
	///////////////////////////////////////////////////////

	ilunch.lockScreen();
	
	ilunch.getMainDishListOnSelectionPage(
			''+currentY+'-'+(1+currentM)+'-'+'01', 
			''+currentY+'-'+(1+currentM)+'-'+lastDateOfMonth.getDate(),
			areaId,
			function(data) {
				//TODO convert to array
				mainDishList = data;
			}
	);

	
	ilunch.getCart(
			function(data) {
				cart = new ilunch.Cart(data);
			}
	);
	
	///////////////////////////////////////////////////////
	////////////////// define templates ///////////////////
	///////////////////////////////////////////////////////
	var dateDisplayer = '##MM##/##DD## 周##WW## ##TODAY##';

	var dishSubTmplt1 = '<div class="dl_c_li">'+
							'<div class="date"><span class="##DATE_CSS##">##DATE##</span></div>'+
							'<div class="cai_p"><a href="#"><img src="/prototype/##IMG##" /></a></div>'+
							'<div class="cai_t">##MD_NAME##</div>'+
							'<div name="order" class="cai_s">';
	
	var dishSubTmplt2 =   	'</div>'+
					  	'</div>';

	var orderTmplt = '<div class="cai_sl">'+
						'<input onclick="dec_quantity(this);" class="jianyi" name="" type="button" value="" />'+
						'<input name="quantity" type="text" class="shuliang" maxlength="3" value="1"/>'+
						'<input onclick="inc_quantity(this)" class="jiayi" name="" type="button" value="" />'+
						'份  <span class="stress">##PRICE##元/份</span>'+
					 '</div>'+
					 '<input class="xuangou" onclick="md_order(##MD_ID##,\'##YY##-##MM##-##DD##\',\'##MD_NAME##\',\'##IMG##\',this);" onmouseover="this.className=\'xuangou_1\'" onmouseout="this.className=\'xuangou\'" name="" type="button" value="" />';
	
	var disorderTmplt = '<div class="cai_sl">已选购##SEL_N##份共##SEL_PRICE##元</div>'+
						'<input onclick="md_disorder(##MD_ID##,\'##YY##-##MM##-##DD##\',this);" class="fangqi" onmouseover="this.className=\'fangqi_1\'" onmouseout="this.className=\'fangqi\'" name="" type="button" value="" />';

	
	var dishTmplt = (dishSubTmplt1+orderTmplt+dishSubTmplt2).replace('##DATE##', dateDisplayer);
	
	var selDishTmplt = (dishSubTmplt1+disorderTmplt+dishSubTmplt2).replace('##DATE##', dateDisplayer);
	
	var oldDishTmplt = (dishSubTmplt1+'<div class="cai_sl">订餐时间已过</div>'+dishSubTmplt2).replace('##DATE##', dateDisplayer);

	var emptyTmplt = '<div class="dl_c_li"></div>';

	var nodishTmplt = ('<div class="dl_c_li">'+
						'<div class="date"><span class="##DATE_CSS##">##DATE##</span></div>'+
					  '</div>').replace('##DATE##', dateDisplayer);
	
	///////////////////////////////////////////////////////
	///// wait until data's ready then render page ////////
	///////////////////////////////////////////////////////
	
	//TODO lock the screen
	
	var busy = false;
	renderMDList = function() {
		if(cart && mainDishList && !busy) {
			busy = true;
			
			//render logic start
			var lastDateOfMonth = new Date(currentY, currentM, 0).getDate();
			//size of a typical calendar month excludes weekends is 5*6
			for(var i = 0, date = 1+skip; i < 6; i++, date+=2) {
				for(var j = 0; j < 5; j++) {
					if(date <= lastDateOfMonth) {
						if(i == 0 && j < headOffset) {
							//last month
							listElem.append(emptyTmplt);
						}
						else if(i == 5 && j+1 > lastDateOfMonth.getDay()) {
							//next month
							listElem.append(emptyTmplt);
						}
						else {
							//current month
							var yy = currentY;
							var mm = ilunch.doubleDigit(1+currentM);
							var dd = ilunch.doubleDigit(date);
							var ww = ilunch.digitToCNSS(j+1);
							var md = getMainDishByDate(date);
							if(md) {
								var thisDate = new Date(currentY, currentM, date);
								var sel_md = cart.getOrderByIdAndDate(md.id, thisDate, true);
								var price = ilunch.getPriceByDate(md.prices, thisDate);
								var md_name = md.name;
								var img = md.imageURL;
								
								if(thisDate <= new Date(today.getFullYear(), today.getMonth(), today.getDate()+ORDER_INADVANCE_DAY)) {
									var divText = oldDishTmplt;
								}
								else {
									if(sel_md) {
										var divText = selDishTmplt;
										divText = divText.replace(/##MD_ID##/g, md.id).replace(/##SEL_N##/g, sel_md.quantity).replace(/##SEL_PRICE##/g, price*sel_md.quantity);
									}
									else {
										var divText = dishTmplt;
										divText = divText.replace(/##MD_ID##/g, md.id).replace(/##PRICE##/g, price);
									}
								}
								divText = divText.replace(/##MD_NAME##/g, md_name).replace(/##IMG##/g, img);
							}
							else
								var divText = nodishTmplt;
							divText = divText.replace(/##MM##/g, mm).replace(/##DD##/g, dd).replace(/##WW##/g, ww).replace(/##YY##/g, yy);
							if(date == currentD && currentM == new Date().getMonth()) {
								divText = divText.replace(/##TODAY##/g, '今天');
								divText = divText.replace(/##DATE_CSS##/g, 'stress');
							}
							else {
								divText = divText.replace(/##TODAY##/g, '');
								divText = divText.replace(/##DATE_CSS##/g, 'date');
							}
							listElem.append(divText);
							date++;
						}
					}
				}
			}
			in_total.html(cart.getTotalMoney());
			//render logic end
			
			busy = false;
			clearInterval(processor);
			ilunch.unlockScreen();
		}
	};

	//wait for data to be ready
	var processor = setInterval(renderMDList, 50);

	function getMainDishByDate(date) {
		for(var i in mainDishList) {
			for(var j = 0; j < mainDishList[i].prices.length; j++) {
				var d = ilunch.makeDate(mainDishList[i].prices[j].startDate);
				if(d.getDate() == date && currentM == d.getMonth() && currentY == d.getFullYear())
					return mainDishList[i];
			}
		}
	}
	
	//TODO change to use getOrderById
	function getMDById(id) {
		id = parseInt(id);
		for(var i in mainDishList) {
			if(id == mainDishList[i].id)
				return mainDishList[i];
		}
	}

	///////////////////////////////////////////////////////
	///////////////   bind event handlers   ///////////////
	///////////////////////////////////////////////////////
	$('#last_month_btn').click(function(){
		d.setMonth(currentM-1);
		initialize();
		if(currentM == new Date().getMonth() && currentY == new Date().getFullYear())
			$('#last_month_btn').css({"display":"none"});
//		mainDishList = null;
//		_getMainDishListOnSelectionPage();
		renderMDList();
	});
	
	$('#next_month_btn').click(function(){
		d.setMonth(currentM+1);
		initialize();
		if(currentM > new Date().getMonth() && currentY >= new Date().getFullYear())
			$('#last_month_btn').css({"display":"inline"});
//		mainDishList = null;
//		_getMainDishListOnSelectionPage();
		renderMDList();
	});
	
	$('#btn_confirm').click(function() {
		ilunch.saveCart(cart.toString(), function(data){
			if(data) {
				$('#confirm_form').attr({"action":"/prototype/dataAPI/pickSideDish"});
				$('#confirm_form').submit();
				return true;
			}
		});
	});
	
	md_order = function(id, date, name, imageURL, elem) {
		var md = getMDById(id);
		var quantity = parseInt($(elem).prev().find('input[name="quantity"]').val());
		date = ilunch.makeDate(date);
		if(md && quantity > 0) {
			var price = ilunch.getPriceByDate(md.prices, date);
			cart.addOrder(true, date, id, name, imageURL, price, quantity);
			var pElem = $(elem).parents('div[name="order"]');
			pElem.empty();
			pElem.append(disorderTmplt.replace(/##MD_ID##/g, id).replace(/##SEL_N##/g, quantity).replace(/##SEL_PRICE##/g, price*quantity).replace(/##YY##/g, date.getFullYear()).replace(/##MM##/g, 
					ilunch.doubleDigit(date.getMonth()+1)).replace(/##DD##/g, ilunch.doubleDigit(date.getDate())));
			in_total.html(cart.getTotalMoney());
		}
	};
	
	md_disorder = function(id, date, elem) {
		var md = getMDById(id);
		date = ilunch.makeDate(date);
		if(md) {
			var quantity = cart.getOrderByIdAndDate(id, date, true).quantity;
			cart.deleteOrder(true, date, id);
			var price = ilunch.getPriceByDate(md.prices, date);
			var pElem = $(elem).parents('div[name="order"]');
			pElem.empty();
			pElem.append(orderTmplt.replace(/##MD_NAME##/g, md.name).replace(/##IMG##/g, md.imageURL).replace(/##MD_ID##/g, md.id).replace(/##PRICE##/g, price).replace(/##YY##/g, date.getFullYear()).replace(/##MM##/g, 
					ilunch.doubleDigit(date.getMonth()+1)).replace(/##DD##/g, ilunch.doubleDigit(date.getDate())));
			in_total.html(cart.getTotalMoney());
			if(parseInt(in_total.html()) < 0)
				in_total.html('0');
		}
	};
	
	dec_quantity = function(elem) {
		var quantity = parseInt($(elem).siblings('input[name="quantity"]').val());
		if(!quantity)
			quantity = 0;
		$(elem).siblings('input[name="quantity"]').val((quantity-1)<0?0:(quantity-1));
	};
	
	inc_quantity = function(elem) {
		var quantity = parseInt($(elem).siblings('input[name="quantity"]').val());
		if(!quantity)
			quantity = 0;
		$(elem).siblings('input[name="quantity"]').val(quantity+1);
	};
});