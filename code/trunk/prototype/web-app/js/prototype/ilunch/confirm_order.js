$(document).ready(function($){
	
	var undefined;

	var ilunch = $.ilunch_namespace("cn.ilunch");
	
	///////////////////////////////////////////////////////
	////////////////// global declaration /////////////////
	///////////////////////////////////////////////////////
	
	var cart = null;
	var daList = null;
	var selBuilding = null;
	var points = null;
	var areaId = $('#area_id').val();
	if(!areaId || areaId == '')
		ilunch.fatalError("area id not found!");
//	var userId = $('#user_id').val();
//	if(!userId || userId == '') {
//		ilunch.fatalError("user_id elem not found! Probably not logged on!");
//		//TODO delete
//		ilunch.fatalError("Setting userId to 3 for testing purpose");
//		userId = 3;
//	}
	var user = null;
	
	///////////////////////////////////////////////////////
	////////////////// initialization /////////////////////
	///////////////////////////////////////////////////////
	
	var d = new Date();
	var currentM = null;
	var currentD = null;
	var currentY = null;
	var currentDay = null;

	var currentDateElem = $('#current_date');
	if(currentDateElem.length <= 0)
		ilunch.fatalError('current_date elem not found!');
	var orderInfoElem = $('#order_info');
	if(orderInfoElem.length <= 0)
		ilunch.fatalError('orderInfoElem elem not found!');
	var in_total = $('#in_total');
	in_total.html('0');
	
	function initialize() {
		currentM = d.getMonth();
		currentD = d.getDate();
		currentY = d.getFullYear();
		currentDay = d.getDay();

		currentDateElem.html(currentDateElem.html().replace('##YY##', currentY).replace('##MM##', 
				ilunch.doubleDigit(currentM+1)).replace('##DD##', 
						ilunch.doubleDigit(currentD)).replace('##WW##', ilunch.digitToCNSS(currentDay)));
	}
	
	initialize();
	
	///////////////////////////////////////////////////////
	////////////////// send request for data  /////////////
	///////////////////////////////////////////////////////
	ilunch.getCurrentUserInfo(function(data){
		user = data;
	});

	ilunch.getCart(
			function(data) {
				cart = new ilunch.Cart(data);
                in_total.html(cart.getTotalMoney());
//				//delete
//				cart.addOrder(true, new Date(2011,4,12), 1, "逼鱼", "images/pic_17.jpg", 2);
//				cart.addOrder(true, new Date(2011,4,9), 2, "盖浇饭", "images/pic_17.jpg", 2);
			}
	);
	
	///////////////////////////////////////////////////////
	////////////////// define templates ///////////////////
	///////////////////////////////////////////////////////
	
	var userInfoTmplt = '<li class="pay_online"><p>联系电话：##PHONE_NO##   联系人：##CONTACT##</p><p>配送地址：##AREA_NAME## ##BUILDING_NAME##</p></li>';
	
	var orderItemTmplt = '<li>'+
              				'<p><span class="stress">价格##ITEM_PRICE##元</span>尝鲜套餐：##ITEM_NAMES## </p>'+
              				'<p>配送时间：周##DELIVER_WW##  ##DELiVER_DATE## </p>'+
              			 '</li>';
	
	///////////////////////////////////////////////////////
	///// wait until data's ready then render page ////////
	///////////////////////////////////////////////////////
	
	//TODO lock the screen

	var busy1 = false;
	renderOrderInfo = function() {
		if(!busy1 && user.id && cart) {
			busy1 = true;
			
			//render log start...
			orderInfoElem.empty();
			
			var products = cart.getCart().products ? cart.getCart().products : [];
			for(var i = 0; i < products.length; i++) {
				if(products[i].mainDishes.length == 0 && products[i].sideDishes.length == 0)
					continue;
				var deliverDate = products[i].date;
				var deliverW = ilunch.digitToCNSS(ilunch.makeDate(products[i].date).getDay());
				var itemPrice = 0;
				var itemNames = '';
				var md = products[i].mainDishes[0];
				if(md) {
					itemPrice += (md.price*md.quantity);
					itemNames += (md.name+'(x'+md.quantity+') +');
				}
				for(var j = 0; j < products[i].sideDishes.length; j++) {
					var sd = products[i].sideDishes[j];
					itemPrice += (sd.price*sd.quantity);
					itemNames += (sd.name+'(x'+sd.quantity+') +');
				}
				if(itemNames[itemNames.length-1] == '+')
					itemNames = itemNames.substring(0, itemNames.length-1);
				
				orderInfoElem.append(orderItemTmplt.replace(/##ITEM_PRICE##/g, itemPrice).
						replace(/##ITEM_NAMES##/g, itemNames).
						replace(/##DELIVER_WW##/g, deliverW).
						replace(/##DELiVER_DATE##/g, deliverDate));
			}
			
			var areaName = cart.getCart().area;
			var buildingName = cart.getCart().distributionPoint;
			var phone = user.phoneNumber;
			var contact = user.nickname;
			var points = user.points;
			orderInfoElem.append(userInfoTmplt.replace(/##PHONE_NO##/g, phone)
					.replace(/##CONTACT##/g, contact).replace(/##AREA_NAME##/g, areaName).replace(/##BUILDING_NAME##/g, buildingName));
			//render logic end
			
			//TODO release screen lock here
			
			busy1 = false;
			clearInterval(processor1);
		}
	};
	
	var busy2 = false;
	renderCart = function() {
		if(cart && !busy2) {
			busy2 = true;
			
			//render logic start
			
			//2. render md and sd for each of the week days
			var retval = cart.getCurrentWeekOrder();
			var wStartDate = retval.startDate;
			var wEndDate = retval.endDate;
			var products = retval.products;

			//2.1 render calendar title;
			$('#cart_date').empty();
			for(var di = wStartDate; di <= wEndDate; di = new Date(di.getFullYear(), di.getMonth(), di.getDate()+1)) {
				$('#cart_date').append('<li>'+ilunch.doubleDigit(di.getMonth()+1)+'/'+ilunch.doubleDigit(di.getDate())+'</li>');
			}

			//2.2 render MD row;
			$('#cart_dashboard').empty();
			for(var di = wStartDate; di <= wEndDate; di = new Date(di.getFullYear(), di.getMonth(), di.getDate()+1)) {
				var md = cart.getOrdersByDate(di, true);
				if(md && md.length > 0) {
					md = md[0];
					$('#cart_dashboard').append('<li><img class="sdpic" src="/prototype/'+md.imageURL+'" /></li>');
				}
				else {
					$('#cart_dashboard').append('<li><img src="/prototype/images/zc_y.png" /></li>');
				}
			}
			
			//get the number of SD row;
			var NsdRow = 0;
			for(var i = 0; i < products.length; i++) {
				if(products[i].sideDishes.length > NsdRow)
					NsdRow = products[i].sideDishes.length;
			}
			if(NsdRow < 1)
				NsdRow = 1;
			//2.4 render each SD row
            for (var ri = 0; ri < NsdRow; ri++) {
                for (var di = wStartDate; di <= wEndDate; di = new Date(di.getFullYear(), di.getMonth(), di.getDate() + 1)) {
					var isRendered = false;
                    for (var i = 0; i < products.length; i++) {
						var od = ilunch.makeDate(products[i].date);
                        if (od >= di && od <= di) {
							if (products[i].sideDishes.length > ri) {
								//render this SD here
								var sd = products[i].sideDishes[ri];
								$('#cart_dashboard').append('<li><img class="sdpic" src="/prototype/'+sd.imageURL+'" /><div class="n">x'+sd.quantity+'</div></li>');
								isRendered = true;
								break;
							}
						}
                    }
					if(!isRendered) {
						//render a empty SD here
						$('#cart_dashboard').append('<li><img src="/prototype/images/pc_y.png" /></li>');
					}
                }
            }
			//render logic end
			
            busy2 = false;
			clearInterval(processor2);
			//TODO release screen lock here
		}
	};
	
	//wait for data to be ready
	var processor1 = setInterval(renderOrderInfo, 50);
	var processor2 = setInterval(renderCart, 50);
	
	///////////////////////////////////////////////////////
	///////////////   bind event handlers   ///////////////
	///////////////////////////////////////////////////////
	
	$('#last_week_btn').click(function() {
		//change current date values to the first day of last week;
		cart.getLastWeekOrder();
		processor2 = setInterval(renderCart, 50);
	});
	
	$('#next_week_btn').click(function() {
		//change current date values to the first day of next week;
		cart.getNextWeekOrder();
		processor2 = setInterval(renderCart, 50);
	});
	
	$('#btn_confirm_last').click(function() {
		ilunch.saveCart(cart.toString(), function(data){
			if(data) {
				$('#confirm_form').attr({"action":"/prototype/dataAPI/confirmInfo"});
				$('#confirm_form').submit();
				return true;
			}
		});
	});

	$('#btn_confirm_next').click(function() {
		ilunch.saveCart(cart.toString(), function(data){
			
		});
		//TODO lock screen
		var success = null;
		ilunch.confirmOrder(cart.toOrderString(user.id), function(result) {
			if(result == 0) {
				success = true;
			}
			else if(result == "04"){
				success = false;
				ilunch.fatalError("使用的积分超过余额！");
			}
		});
		function wait() {
			if(success != null) {

				if(success) {
					$('#confirm_form').attr({"action":"/prototype/dataAPI/orderSuccess"});
					$('#confirm_form').submit();
				}

				clearInterval(p);
				//TODO unlock screen

			}

		}
		var p = setInterval(wait, 50);
		return true;
	});
});