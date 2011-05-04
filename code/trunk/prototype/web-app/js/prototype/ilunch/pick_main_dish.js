$(document).ready(function($){

	var undefined;

	var ilunch = $.ilunch_namespace("cn.ilunch");

	///////////////////////////////////////////////////////
	////////////////// initialization /////////////////////
	///////////////////////////////////////////////////////
	var mainDishList = null;
	
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
	if(!listElem)
		ilunch.fatalError('list elem not found!');
	var currentDateElem = $('#current_date');
	if(!currentDateElem)
		ilunch.fatalError('current_date elem not found!');
	
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
	ilunch.getMainDishListOnSelectionPage(
			''+currentY+'-'+(1+currentM)+'-'+'01', 
			''+currentY+'-'+(1+currentM)+'-'+lastDateOfMonth.getDate(),
			1,
			function(data) {
				mainDishList = data;
			}
	);
	
	///////////////////////////////////////////////////////
	////////////////// define templates ///////////////////
	///////////////////////////////////////////////////////
	var dateDisplayer = '##MM##/##DD## 周##WW## ##TODAY##';
	
	var nodishTmplt = '<div class="dl_c_li"><div class="date"><span class="stress">##DATE##</span></div></div>'.replace('##DATE##', dateDisplayer);
	
	var orderTmplt = '<div class="cai_sl">'+
						'<input onclick="dec_quantity(##MD_ID##);" class="jianyi" name="" type="button" value="" />'+
						'<input id="quantity_##MD_ID##" type="text" class="shuliang" maxlength="3" />'+
						'<input onclick="inc_quantity(##MD_ID##)" class="jiayi" name="" type="button" value="" />'+
						'份  <span class="stress">##PRICE##元/份</span>'+
					 '</div>'+
					 '<input class="xuangou" onclick="md_order(##MD_ID##);" onmouseover="this.className=\'xuangou_1\'" onmouseout="this.className=\'xuangou\'" name="" type="button" value="" />';
	
	var disorderTmplt = '<div class="cai_sl">已选购##SEL_N##份共##SEL_PRICE##元</div>'+
						'<input onclick="md_disorder(##MD_ID##);" class="fangqi" onmouseover="this.className=\'fangqi_1\'" onmouseout="this.className=\'fangqi\'" name="" type="button" value="" />';

	
	var dishTmplt = ('<div class="dl_c_li">'+
						'<div class="date"><span class="stress">##DATE##</span></div>'+
						'<div class="cai_p"><a href="#"><img src="/prototype/##IMG##" /></a></div>'+
						'<div class="cai_t">##MD_NAME##</div>'+
						'<div id="order_##MD_ID##" class="cai_s">'+
							orderTmplt+
						'</div>'+
					'</div>').replace('##DATE##', dateDisplayer);
	
	var selDishTmplt = ('<div class="dl_c_li">'+
							'<div class="date"><span class="stress">##DATE##</span></div>'+
							'<div class="cai_p"><a href="#"><img src="/prototype/##IMG##" /></a></div>'+
							'<div class="cai_t">##MD_NAME##</div>'+
							'<div id="order_##MD_ID##" class="cai_s">'+
								disorderTmplt+
							'</div>'+
						'</div>').replace('##DATE##', dateDisplayer);
	
	var oldDishTmplt = ('<div class="dl_c_li">'+
							'<div class="date"><span class="stress">##DATE##</span></div>'+
							'<div class="cai_p"><a href="#"><img src="/prototype/##IMG##" /></a></div>'+
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
		if(mainDishList && !busy) {
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
							var mm = ilunch.doubleDigit(1+currentM);
							var dd = ilunch.doubleDigit(date);
							var ww = ilunch.digitToCNSS(j+1);
							var md = getMainDishByDate(date);
							if(md) {
								var divText = dishTmplt;
								var md_name = md.name;
								var img = md.imageURL;
								var price = md.prices[0].price;
								divText = divText.replace('##MD_NAME##', md_name).replace('##IMG##', img).replace(/##MD_ID##/g, md.id).replace(/##PRICE##/g, price);
							}
							else
								var divText = nodishTmplt;
							divText = divText.replace('##MM##', mm).replace('##DD##', dd).replace('##WW##', ww);
							if(date == currentD && currentM == new Date().getMonth())
								divText = divText.replace('##TODAY##', '今天');
							else
								divText = divText.replace('##TODAY##', '');
							listElem.append(divText);
							date++;
						}
					}
				}
			}
			//render logic end
			
			clearInterval(processor);
			//TODO release screen lock here
		}
	};

	//wait for data to be ready
	var processor = setInterval(renderMDList, 50);
	
	function getMainDishByDate(date) {
		for(var i in mainDishList) {
			var d = new Date(mainDishList[i].prices[0].startDate);
			if(d.getDate() == date)
				return mainDishList[i];
		}
	}
	
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
		ilunch.getMainDishListOnSelectionPage(
				''+currentY+'-'+(1+currentM)+'-'+'01', 
				''+currentY+'-'+(1+currentM)+'-'+lastDateOfMonth.getDate(),
				1,
				function(data) {
					mainDishList = data;
				}
		);
		busy = false;
		processor = setInterval(renderMDList, 50);
	});
	
	$('#next_month_btn').click(function(){
		d.setMonth(currentM+1);
		initialize();
		ilunch.getMainDishListOnSelectionPage(
				''+currentY+'-'+(1+currentM)+'-'+'01', 
				''+currentY+'-'+(1+currentM)+'-'+lastDateOfMonth.getDate(),
				1,
				function(data) {
					mainDishList = data;
				}
		);
		busy = false;
		processor = setInterval(renderMDList, 50);
	});
	
	md_order = function(id) {
		var md = getMDById(id);
		var quantity = parseInt($('#quantity_'+id).val());
		
		if(md && quantity > 0) {
			//TODO add to cart
			
			var price = md.prices[0].price;
			$('#order_'+id).empty();
			$('#order_'+id).append(disorderTmplt.replace(/##MD_ID##/g, id).replace(/##SEL_N##/g, quantity).replace(/##SEL_PRICE##/g, price*quantity));
		}
	};
	
	md_disorder = function(id) {
		var md = getMDById(id);
		if(md) {
			//TODO add to cart
			
			var price = md.prices[0].price;
			$('#order_'+id).empty();
			$('#order_'+id).append(orderTmplt.replace('##MD_NAME##', md.name).replace('##IMG##', md.imageURL).replace(/##MD_ID##/g, md.id).replace(/##PRICE##/g, price));
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