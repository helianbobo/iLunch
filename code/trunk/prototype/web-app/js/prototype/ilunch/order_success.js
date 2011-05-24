$(document).ready(function($){
	
	var undefined;

	var ilunch = $.ilunch_namespace("cn.ilunch");
	
	///////////////////////////////////////////////////////
	////////////////// global declaration /////////////////
	///////////////////////////////////////////////////////
	
	var cart = null;
	var userId = $('#user_id').val();
//	if(!userId || userId == '') {
//		ilunch.fatalError("user_id elem not found! Probably not logged on!");
//		//TODO delete
//		ilunch.fatalError("Setting userId to 3 for testing purpose");
//		userId = 3;
//	}
//	var user = null;
	
	///////////////////////////////////////////////////////
	////////////////// initialization /////////////////////
	///////////////////////////////////////////////////////
	
	var d = new Date();
	var currentM = null;
	var currentD = null;
	var currentY = null;
	var currentDay = null;

	var orderInfoElem = $('#order_info');
	if(orderInfoElem.length <= 0)
		ilunch.fatalError('orderInfoElem elem not found!');
	
	function initialize() {
		currentM = d.getMonth();
		currentD = d.getDate();
		currentY = d.getFullYear();
		currentDay = d.getDay();
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
//				//delete
//				cart.addOrder(true, new Date(2011,4,12), 1, "逼鱼", "images/pic_17.jpg", 2);
//				cart.addOrder(true, new Date(2011,4,9), 2, "盖浇饭", "images/pic_17.jpg", 2);
			}
	);
	
	///////////////////////////////////////////////////////
	////////////////// define templates ///////////////////
	///////////////////////////////////////////////////////
	
	var orderItemTmplt = '<p><span>##DELiVER_DATE##周##DELIVER_WW## 取餐</span>##ITEM_NAMES##</p>';
	
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
			$('#info_tab').html($('#info_tab').html().replace(/##PHONE_NO##/g, phone)
					.replace(/##CONTACT##/g, contact?contact:"未提供联系人姓名").replace(/##AREA_NAME##/g, areaName).replace(/##BUILDING_NAME##/g, buildingName));
			//render logic end
			
			//TODO release screen lock here
			
			busy1 = false;
			clearInterval(processor1);
		}
	};
	
	//wait for data to be ready
	var processor1 = setInterval(renderOrderInfo, 50);
	
	///////////////////////////////////////////////////////
	///////////////   bind event handlers   ///////////////
	///////////////////////////////////////////////////////

	$('#btn_confirm_next').click(function() {
		
	});
});