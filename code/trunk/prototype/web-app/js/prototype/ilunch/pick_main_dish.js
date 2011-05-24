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
	
	var d = new Date();
	var currentM = null;
	var currentD = null;
	var currentY = null;
	var currentDay = null;
	var firstDateOfMonth = null;
	var skip = 0;
	var lastDateOfMonth = null;
	var headOffset = null;

	var listElem = $('#md_list');
	if(listElem.length <= 0)
		ilunch.fatalError('md_list elem not found!');
	var currentDateElem = $('#current_date');
	if(currentDateElem.length <= 0)
		ilunch.fatalError('current_date elem not found!');
	var in_total = $('#in_total');
	in_total.html('0');
	
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
	
	function _getMainDishListOnSelectionPage() {
		ilunch.getMainDishListOnSelectionPage(
				''+currentY+'-'+(1+currentM)+'-'+'01', 
				''+currentY+'-'+(1+currentM)+'-'+lastDateOfMonth.getDate(),
				areaId,
				function(data) {
					//TODO convert to array
					mainDishList = data;
				}
		);
	}
	
	_getMainDishListOnSelectionPage();
	
	ilunch.getCart(
			function(data) {
				cart = new ilunch.Cart(data);
			}
	);
	
	///////////////////////////////////////////////////////
	////////////////// define templates ///////////////////
	///////////////////////////////////////////////////////
	var dateDisplayer = '##MM##/##DD## 周##WW## ##TODAY##';
	
	var nodishTmplt = '<div class="dl_c_li"><div class="date"><span class="##DATE_CSS##">##DATE##</span></div></div>'.replace('##DATE##', dateDisplayer);
	
	var orderTmplt = '<div class="cai_sl">'+
						'<input onclick="dec_quantity(##MD_ID##);" class="jianyi" name="" type="button" value="" />'+
						'<input id="quantity_##MD_ID##" type="text" class="shuliang" maxlength="3" value="1"/>'+
						'<input onclick="inc_quantity(##MD_ID##)" class="jiayi" name="" type="button" value="" />'+
						'份  <span class="stress">##PRICE##元/份</span>'+
					 '</div>'+
					 '<input class="xuangou" onclick="md_order(##MD_ID##,\'##YY##-##MM##-##DD##\',\'##MD_NAME##\',\'##IMG##\');" onmouseover="this.className=\'xuangou_1\'" onmouseout="this.className=\'xuangou\'" name="" type="button" value="" />';
	
	var disorderTmplt = '<div class="cai_sl">已选购##SEL_N##份共##SEL_PRICE##元</div>'+
						'<input onclick="md_disorder(##MD_ID##,\'##YY##-##MM##-##DD##\');" class="fangqi" onmouseover="this.className=\'fangqi_1\'" onmouseout="this.className=\'fangqi\'" name="" type="button" value="" />';

	
	var dishTmplt = ('<div class="dl_c_li">'+
						'<div class="date"><span class="##DATE_CSS##">##DATE##</span></div>'+
						'<div class="cai_p"><a href="#"><img src="/prototype/##IMG##" /></a></div>'+
						'<div class="cai_t">##MD_NAME##</div>'+
						'<div id="order_##MD_ID##" class="cai_s">'+
							orderTmplt+
						'</div>'+
					'</div>').replace(/##DATE##/g, dateDisplayer);
	
	var selDishTmplt = ('<div class="dl_c_li">'+
							'<div class="date"><span class="##DATE_CSS##">##DATE##</span></div>'+
							'<div class="cai_p"><a href="#"><img src="/prototype/##IMG##" /></a></div>'+
							'<div class="cai_t">##MD_NAME##</div>'+
							'<div id="order_##MD_ID##" class="cai_s">'+
								disorderTmplt+
							'</div>'+
						'</div>').replace(/##DATE##/g, dateDisplayer);
	
	var oldDishTmplt = ('<div class="dl_c_li">'+
							'<div class="date"><span class="##DATE_CSS##">##DATE##</span></div>'+
							'<div class="cai_p"><a href="#"><img class="old_md" src="/prototype/##IMG##" /></a></div>'+
							'<div class="cai_t">##MD_NAME##</div>'+
							'<div class="cai_s">'+
								'<div class="cai_sl">订餐时间已过</div>'+
							'</div>'+
						'</div>').replace('##DATE##', dateDisplayer);

	var emptyTmplt = '<div class="dl_c_li"></div>';
	
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
								if(thisDate < d) {
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
								divText = divText.replace('##TODAY##', '今天');
								divText = divText.replace('##DATE_CSS##', 'stress');
							}
							else {
								divText = divText.replace('##TODAY##', '');
								divText = divText.replace('##DATE_CSS##', 'date');
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
			//TODO release screen lock here
		}
	};

	//wait for data to be ready
	var processor = setInterval(renderMDList, 50);

	function getMainDishByDate(date) {
		for(var i in mainDishList) {
			for(var j = 0; j < mainDishList[i].prices.length; j++) {
				var d = ilunch.makeDate(mainDishList[i].prices[j].startDate);
				if(d.getDate() == date && currentM == d.getMonth())
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
		mainDishList = null;
		_getMainDishListOnSelectionPage();
		processor = setInterval(renderMDList, 50);
	});
	
	$('#next_month_btn').click(function(){
		d.setMonth(currentM+1);
		initialize();
		mainDishList = null;
		_getMainDishListOnSelectionPage();
		processor = setInterval(renderMDList, 50);
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
	
	md_order = function(id, date, name, imageURL) {
		var md = getMDById(id);
		var quantity = parseInt($('#quantity_'+id).val());
		date = ilunch.makeDate(date);
		if(md && quantity > 0) {
			var price = ilunch.getPriceByDate(md.prices, date);
			cart.addOrder(true, date, id, name, imageURL, price, quantity);
			$('#order_'+id).empty();
			$('#order_'+id).append(disorderTmplt.replace(/##MD_ID##/g, id).replace(/##SEL_N##/g, quantity).replace(/##SEL_PRICE##/g, price*quantity).replace(/##YY##/g, date.getFullYear()).replace(/##MM##/g, 
					ilunch.doubleDigit(date.getMonth()+1)).replace(/##DD##/g, ilunch.doubleDigit(date.getDate())));
			in_total.html(cart.getTotalMoney());
		}
	};
	
	md_disorder = function(id, date) {
		var md = getMDById(id);
		date = ilunch.makeDate(date);
		if(md) {
			var quantity = cart.getOrderByIdAndDate(id, date, true).quantity;
			cart.deleteOrder(true, date, id);
			var price = ilunch.getPriceByDate(md.prices, date);
			$('#order_'+id).empty();
			$('#order_'+id).append(orderTmplt.replace(/##MD_NAME##/g, md.name).replace(/##IMG##/g, md.imageURL).replace(/##MD_ID##/g, md.id).replace(/##PRICE##/g, price).replace(/##YY##/g, date.getFullYear()).replace(/##MM##/g, 
					ilunch.doubleDigit(date.getMonth()+1)).replace(/##DD##/g, ilunch.doubleDigit(date.getDate())));
			in_total.html(cart.getTotalMoney());
			if(parseInt(in_total.html()) < 0)
				in_total.html('0');
		}
	};
	
	dec_quantity = function(id) {
		var quantity = parseInt($('#quantity_'+id).val());
		if(!quantity)
			quantity = 0;
		$('#quantity_'+id).val((quantity-1)<0?0:(quantity-1));
	};
	
	inc_quantity = function(id) {
		var quantity = parseInt($('#quantity_'+id).val());
		if(!quantity)
			quantity = 0;
		$('#quantity_'+id).val(quantity+1);
	};
});