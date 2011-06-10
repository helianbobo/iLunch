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

	var currentDateElem = $('#current_date');
	if(currentDateElem.length <= 0)
		ilunch.fatalError('current_date elem not found!');
	var orderInfoElem = $('#order_info');
	if(orderInfoElem.length <= 0)
		ilunch.fatalError('orderInfoElem elem not found!');
	var in_total = $('#in_total');
	in_total.html('0');

	currentDateElem.html(currentDateElem.html().replace('##YY##', Date.today().getFullYear()).replace('##MM##', 
    		ilunch.doubleDigit(Date.today().getMonth()+1)).replace('##DD##', 
    				ilunch.doubleDigit(Date.today().getDate())).replace('##WW##', ilunch.digitToCNSS(Date.today().getDay())));

	///////////////////////////////////////////////////////
	////////////////// send request for data  /////////////
	///////////////////////////////////////////////////////
	
	ilunch.lockScreen();
	
	$.when(
			ilunch.getCurrentUserInfo(function(data){
				user = data;
				if(user.error)
					ilunch.fatalError("获取用户信息错误，请尝试刷新页面或重新登录！");
			}),
			ilunch.getCart(
					function(data) {
						cart = new ilunch.Cart(data);
						in_total.html(cart.getTotalMoney());
//						//delete
//						cart.addOrder(true, new Date(2011,4,12), 1, "逼鱼", "images/pic_17.jpg", 2);
//						cart.addOrder(true, new Date(2011,4,9), 2, "盖浇饭", "images/pic_17.jpg", 2);
					}
			)
	).done(function() {
		renderOrderInfo();
		cart.render(true);
		ilunch.unlockScreen();
	});
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
	
	renderOrderInfo = function() {
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
				.replace(/##CONTACT##/g, contact?contact:"未提供联系人姓名").replace(/##AREA_NAME##/g, areaName).replace(/##BUILDING_NAME##/g, buildingName));
		//render logic end
	};
	
	///////////////////////////////////////////////////////
	///////////////   bind event handlers   ///////////////
	///////////////////////////////////////////////////////
	
	lastWeek = function() {
		//change current date values to the first day of last week;
		cart.getLastWeekOrder();
		cart.render();
	};
	
	nextWeek = function() {
		//change current date values to the first day of next week;
		cart.getNextWeekOrder();
		cart.render();
	};
	
	$('#btn_confirm_last').click(function() {
		ilunch.saveCart(cart.toString(), function(data){
			if(data) {
				window.location.href = ilunch.ROOT+"dataAPI/confirmInfo";
				return true;
			}
		});
	});

	$('#btn_confirm_next').click(function() {
		if(cart.isEmpty()) {
			alert("请至少添加一道主菜/配菜！");
			return;
		}
		
		ilunch.saveCart(cart.toString(), function(data){});
		var success = null;
		var orderId = null;
		ilunch.lockScreen();
		$.when(
				ilunch.confirmOrder(cart.toOrderString(user.id), function(result) {
					if(!result.error) {
						success = true;
						orderId = result.orderId;
					}
					else if(result.error.errorCode == "04"){
						success = false;
						ilunch.fatalError("使用的积分超过余额！");
					}
					else if(result.error.errorCode == "05") {
						success = false;
						ilunch.fatalError("只能订购两天之后的餐品！");
					}
					else {
						success = false;
						ilunch.fatalError("提交订单错误："+result.error.message);
					}
				})
		).done(
				function() {
					if(success != null) {

						if(success) {
							$('#confirm_form').attr({"action":ilunch.ROOT+"dataAPI/orderSuccess"});
							$('#confirm_form').find('input[type=hidden]').val(orderId);
							$('#confirm_form').submit();
						}
						
					}
					ilunch.unlockScreen();
				}
		);
		return true;
	});
	
});