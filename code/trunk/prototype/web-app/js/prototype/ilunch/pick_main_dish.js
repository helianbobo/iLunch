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
	var MD_SHOW_NUM = 5;
	var currentPage = 1;
	var mdCache = [];
	var mdList = [];
	var CACHE_CAPACITY = 30;

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
//		firstDateOfMonth = new Date(currentY, currentM, 1);
//		skip = 0;
//		if(firstDateOfMonth.getDay() == 0)
//			skip = 1;
//		else if(firstDateOfMonth.getDay() == 6)
//			skip = 2;
//		
//		lastDateOfMonth = new Date(currentY, currentM+1, 0);
//		headOffset = firstDateOfMonth.getDay() > 1 ? firstDateOfMonth.getDay()-1 : 0;
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
	
	$.when(
		ilunch.getMainDishListOnSelectionPage(
			ilunch.dateToString(new Date(today.getFullYear(), today.getMonth(), today.getDate()+ORDER_INADVANCE_DAY)), 
			ilunch.dateToString(new Date(today.getFullYear(), today.getMonth(), today.getDate()+ORDER_INADVANCE_DAY+CACHE_CAPACITY)),
			null,
			areaId,
			function(data) {
				var arr = [];
				for(var i in data) {
					arr.push(data[i]);
				}
				mainDishList = arr;
				fillMDCache();
			}
		), 
		ilunch.getCart(
			function(data) {
				cart = new ilunch.Cart(data);
				cart.getCart().ORDER_INADVANCE_DAY = ORDER_INADVANCE_DAY;
			}
		)
	).done(function() {
		renderMDList();
		ilunch.unlockScreen();
	});

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

	var orderTmplt = '<div class="xc_sl">'+
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

    renderMDList = function(){
        //render logic start
    	listElem.empty();
    	var renderList = getRenderList();
    	for(var i = 0; i < renderList.length; i++) {
    		var md = renderList[i].md;
    		var thisDate = renderList[i].date;
    		var yy = thisDate.getFullYear();
    		var mm = ilunch.doubleDigit(thisDate.getMonth()+1);
    		var dd = ilunch.doubleDigit(thisDate.getDate());
    		var ww = ilunch.digitToCNSS(thisDate.getDay());
    		var sel_md = cart.getOrderByIdAndDate(md.id, thisDate, true);
    		var price = renderList[i].price;
    		var md_name = md.name;
    		var img = md.imageURL;
    		if (sel_md) {
    			var divText = selDishTmplt;
    			divText = divText.replace(/##MD_ID##/g, md.id).replace(/##SEL_N##/g, sel_md.quantity).replace(/##SEL_PRICE##/g, price * sel_md.quantity);
    		}
    		else {
    			var divText = dishTmplt;
    			divText = divText.replace(/##MD_ID##/g, md.id).replace(/##PRICE##/g, price);
    		}
    		divText = divText.replace(/##MD_NAME##/g, md_name).replace(/##IMG##/g, img);
    		divText = divText.replace(/##MM##/g, mm).replace(/##DD##/g, dd).replace(/##WW##/g, ww).replace(/##YY##/g, yy);
    		if (thisDate <= today && thisDate >= today) {
    			divText = divText.replace(/##TODAY##/g, '今天');
    			divText = divText.replace(/##DATE_CSS##/g, 'stress');
    		}
    		else {
    			divText = divText.replace(/##TODAY##/g, '');
    			divText = divText.replace(/##DATE_CSS##/g, 'date');
    		}
    		listElem.append(divText);
    	}
    	in_total.html(cart.getTotalMoney());
    	//DEPRECATED
        //size of a typical calendar month excludes weekends is 5*6
//        for (var i = 0, date = 1 + skip; i < 6; i++, date += 2) {
//            for (var j = 0; j < 5; j++) {
//                if (date <= lastDateOfMonth.getDate()) {
//                    if (i == 0 && j < headOffset) {
//                        //last month
//                        listElem.append(emptyTmplt);
//                    }
//                    else 
//                        if (i == 5 && j + 1 > lastDateOfMonth.getDay()) {
//                            //next month
//                            listElem.append(emptyTmplt);
//                        }
//                        else {
//                            //current month
//                            var yy = currentY;
//                            var mm = ilunch.doubleDigit(1 + currentM);
//                            var dd = ilunch.doubleDigit(date);
//                            var ww = ilunch.digitToCNSS(j + 1);
//                            var md = getMainDishByDate(date);
//                            if (md) {
//                                var thisDate = new Date(currentY, currentM, date);
//                                var sel_md = cart.getOrderByIdAndDate(md.id, thisDate, true);
//                                var price = ilunch.getPriceByDate(md.prices, thisDate);
//                                var md_name = md.name;
//                                var img = md.imageURL;
//                                
//                                if (thisDate <= new Date(today.getFullYear(), today.getMonth(), today.getDate() + ORDER_INADVANCE_DAY)) {
//                                    var divText = oldDishTmplt;
//                                }
//                                else {
//                                    if (sel_md) {
//                                        var divText = selDishTmplt;
//                                        divText = divText.replace(/##MD_ID##/g, md.id).replace(/##SEL_N##/g, sel_md.quantity).replace(/##SEL_PRICE##/g, price * sel_md.quantity);
//                                    }
//                                    else {
//                                        var divText = dishTmplt;
//                                        divText = divText.replace(/##MD_ID##/g, md.id).replace(/##PRICE##/g, price);
//                                    }
//                                }
//                                divText = divText.replace(/##MD_NAME##/g, md_name).replace(/##IMG##/g, img);
//                            }
//                            else 
//                                var divText = nodishTmplt;
//                            divText = divText.replace(/##MM##/g, mm).replace(/##DD##/g, dd).replace(/##WW##/g, ww).replace(/##YY##/g, yy);
//                            if (date == currentD && currentM == new Date().getMonth()) {
//                                divText = divText.replace(/##TODAY##/g, '今天');
//                                divText = divText.replace(/##DATE_CSS##/g, 'stress');
//                            }
//                            else {
//                                divText = divText.replace(/##TODAY##/g, '');
//                                divText = divText.replace(/##DATE_CSS##/g, 'date');
//                            }
//                            listElem.append(divText);
//                            date++;
//                        }
//                }
//            }
//            in_total.html(cart.getTotalMoney());
    		//DEPRECATED END
//            //render logic end
//        }
    };

	function getMainDishByDate(date) {
		for(var i in mainDishList) {
			for(var j = 0; j < mainDishList[i].prices.length; j++) {
				var sd = ilunch.makeDate(mainDishList[i].prices[j].startDate);
				var ed = (mainDishList[i].prices[j].endDate==null?null:ilunch.makeDate(mainDishList[i].prices[j].endDate));
				if(sd <= date && (!ed || date <= ed))
					return [mainDishList[i], mainDishList[i].prices[j].price];
			}
		}
		return null;
	}
	
	function fillMDCache() {
		var sd = null;
		if(mdCache.length > 0)
			sd = mdCache[mdCache.length-1].date;
		var d = (sd==null?
				new Date(today.getFullYear(), today.getMonth(), today.getDate()+ORDER_INADVANCE_DAY):
					new Date(sd.getFullYear(), sd.getMonth(), sd.getDate()+1));
		for(var i = 0; i < CACHE_CAPACITY; i++, d = new Date(d.getFullYear(), d.getMonth(), d.getDate()+1)) {
			if(d.getDay() == 6 || d.getDay() == 0)
				continue;
			var md = getMainDishByDate(d);
			if(md)
				mdCache.push({md:md[0], date:d, price:md[1]});
		}
		mdList = $.merge(mdList, mainDishList);
	}

	///////////////////////////////////////////////////////
	///////////////   bind event handlers   ///////////////
	///////////////////////////////////////////////////////
	$('#last_month_btn').click(function(){	
		ilunch.lockScreen();
//		d.setMonth(currentM-1);
//		initialize();
//		if(currentM == new Date().getMonth() && currentY == new Date().getFullYear())
//			$(this).css({"display":"none"});
		if(hasLastPage()) {
			currentPage--;
			if(!hasLastPage())
				$(this).css({"display":"none"});
			if(hasNextPage())
				$('#next_month_btn').css({"display":"inline"});
			renderMDList();
		}
		else {
			ilunch.fatalError("[pick_main_dish::last_month_btn::click]shouldn't be here!!");
		}
		ilunch.unlockScreen();
	});
	
	$('#next_month_btn').click(function(){	
		ilunch.lockScreen();
//		d.setMonth(currentM+1);
//		initialize();
//		if(currentM > new Date().getMonth() && currentY >= new Date().getFullYear())
//			$('#last_month_btn').css({"display":"inline"});
//			
		var thisPageFull = (mdCache.length % MD_SHOW_NUM) == 0;
		if(hasNextPage()) {
			currentPage++;
			renderMDList();
			$('#last_month_btn').css({"display":"inline"});
			ilunch.unlockScreen();
		}
		else {
			var tmpD = mdCache.length > 0 ? 
					new Date(mdCache[mdCache.length-1].date.getFullYear(), mdCache[mdCache.length-1].date.getMonth(), mdCache[mdCache.length-1].date.getDate()+1) : 
						new Date(today.getFullYear(), today.getMonth(), today.getDate()+ORDER_INADVANCE_DAY);
			$.when(
				ilunch.getMainDishListOnSelectionPage(
					ilunch.dateToString(tmpD), 
					ilunch.dateToString(new Date(tmpD.getFullYear(), tmpD.getMonth(), tmpD.getDate()+CACHE_CAPACITY)),
					null,
					areaId,
					function(data) {
						var arr = [];
						for(var i in data) {
							arr.push(data[i]);
						}
						mainDishList = arr;
						fillMDCache();
					}
				)
			).done(function() {
				if(hasNextPage()) {
					if(thisPageFull)
						currentPage++;
					renderMDList();
					$('#last_month_btn').css({"display":"inline"});
				}
				else {
					$(this).css({"display":"none"});
					alert("没有更多可预订的主菜了！");
				}
				ilunch.unlockScreen();
			});
		}
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
		var md = ilunch.getOrderById(mdList, id);
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
		var md = ilunch.getOrderById(mdList, id);
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

	hasNextPage = function() {
		if(mdCache[currentPage*MD_SHOW_NUM])
			return true;
		else
			return false;
	};
	
	hasLastPage = function() {
		if(currentPage <= 1)
			return false;
		if(mdCache[(currentPage-2)*MD_SHOW_NUM])
			return true;
		else
			return false;
	};
	
	getRenderList = function() {
		var arr = [];
		for(var i = (currentPage-1)*MD_SHOW_NUM, j = 0; j < MD_SHOW_NUM && mdCache[i]; j++, i++)
			arr.push(mdCache[i]);
		return arr;
	};
});