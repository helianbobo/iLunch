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
	var userInfoElem = $('#user_info');
	if(userInfoElem.length <= 0)
		ilunch.fatalError('user_info elem not found!');
	var in_total = $('#in_total');
	in_total.html('0');
	if($('#area_name').length <= 0)
		ilunch.fatalError('area_name elem not found!');
	
	function initialize() {
		currentM = d.getMonth();
		currentD = d.getDate();
		currentY = d.getFullYear();
		currentDay = d.getDay();

		currentDateElem.html(currentDateElem.html().replace('##YY##', currentY).replace('##MM##', 
				ilunch.doubleDigit(currentM+1)).replace('##DD##', 
						ilunch.doubleDigit(currentD)).replace('##WW##', ilunch.digitToCNSS(currentDay)));
		$('#area_selector').html($('#area_selector').html().replace(/##AREA_NAME##/g, $('#area_name').html()));
	}
	
	initialize();
	
	///////////////////////////////////////////////////////
	////////////////// send request for data  /////////////
	///////////////////////////////////////////////////////
	ilunch.getCurrentUserInfo(function(data) {
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
	
	ilunch.getDistributionAreaList(
			function(data) {
				daList = data.areas;
			}
	);
	
	///////////////////////////////////////////////////////
	////////////////// define templates ///////////////////
	///////////////////////////////////////////////////////
	
	var notLoggedTmplt = '';
	
	var loggedOnTmplt = '<div class="al_reg">'+
							'<div class="lxfs">'+
						    '<div>您的手机号码：##PHONE_NO##</div>'+
						    '<p>联系人：##CONTACT##</p>'+
						    '</div>'+
						    '<div class="qc_dd">'+
//						    '<div>您使用过的取餐位置：</div>'+
//						    '<p><input name="" type="radio" value="" />##AREA_NAME####BUILLDING_NAME##</p>'+
						    '</div>'+
						 '</div>';
	
	///////////////////////////////////////////////////////
	///// wait until data's ready then render page ////////
	///////////////////////////////////////////////////////
	
	//TODO lock the screen
	
	var busy1 = false;
	renderUserInfo = function() {
		if(!busy1 && user) {
			busy1 = true;
			
			//render log start...
			userInfoElem.empty();
			if(user.id) {
				var phone = user.phoneNumber;
				var contact = user.nickname;
				var points = user.points;
				userInfoElem.append(loggedOnTmplt.replace(/##PHONE_NO##/g, phone).replace(/##CONTACT##/g, contact?contact:"未提供联系人姓名"));
				//TODO validate pointChange balance
				$('#change_point').html(points?points:0);
				$('#point_control').css({"display":"block"});
				$('#logon_tip').css({"display":"none"});
			}
			else {
				userInfoElem.append(notLoggedTmplt);
				$('#point_control').css({"display":"none"});
				$('#logon_tip').css({"display":"block"});
			}
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
								$('#cart_dashboard').append('<li><img class="sdpic" src="/prototype/'+sd.imageURL+'" /><div class="n">x'+sd.quantity+'</div><div class="no"><a onclick="md_disorder('+sd.id+',\''+ilunch.dateToString(di)+'\')"><img src="/prototype/images/no.png" /></a></div></li>');
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
	
	var busy3 = false;
	renderBuildingSelector = function() {
		if(!busy3 && cart && daList) {
			busy3 = true;
			
			//render log start...

			$('#building_list').change(function(e) {
				//TODO change sel_building value
				selBuilding = $("#building_list").find("option:selected").text();
				$('#sel_building').html(selBuilding);
				cart.getCart().distributionPoint = selBuilding;
				cart.getCart().area = $('#area_name').html();
				cart.getCart().buildingId = parseInt($("#building_list").find("option:selected").val());
			});
			
			$('#building_list').empty();
			for(var i = 0; i < daList.length; i++) {
				if(daList[i].id == areaId) {
					if(daList[i].buildings[0])
						$('#building_list').append('<option value="'+daList[i].buildings[0].id+'" selected="selected">'+daList[i].buildings[0].name+'</option>');
					for(var j = 1; j < daList[i].buildings.length; j++) {
						$('#building_list').append('<option value="'+daList[i].buildings[j].id+'">'+daList[i].buildings[j].name+'</option>');
					}
				}
			}
			$('#building_list').change();
			//render logic end
			
			//TODO release screen lock here
			
			busy3 = false;
			clearInterval(processor3);
		}
	};
	
	//wait for data to be ready
	var processor1 = setInterval(renderUserInfo, 50);
	var processor2 = setInterval(renderCart, 50);
	var processor3 = setInterval(renderBuildingSelector, 50);
	
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
				$('#confirm_form').attr({"action":"/prototype/dataAPI/pickSideDish"});
				$('#confirm_form').submit();
				return true;
			}
		});
	});
	
	$('#btn_confirm_next').click(function() {
		if(!user.id) {
			alert("请先登录!");
			return;
		}
		
		var usedPoints = parseInt($('#change_point_input').val());
		usedPoints = usedPoints ? usedPoints : 0; 
		if(usedPoints > user.points) {
			ilunch.fatalError("使用的积分不得超过您所有拥有的积分！");
			return;
		}
		cart.getCart().pointChange = usedPoints;
		
		ilunch.saveCart(cart.toString(), function(data){
			if(data) {
				$('#confirm_form').attr({"action":"/prototype/dataAPI/confirmOrder"});
				$('#confirm_form').submit();
				return true;
			}
		});
	});
	
	$('#logon_lnk').click(function(e) {
		$('#logon_dialog').css({"left":e.clientX+"px","top":e.clientY+"px"});
		$('#logon_dialog').show();
	});
	
	$('#logon_dialog_cancel').click(function() {
		$('#logon_dialog').hide();
	}); 
	
	$('#logon_dialog_reg_btn').click(function() {
		var x = $('#logon_dialog').css("left");
		var y = $('#logon_dialog').css("top");
		$('#logon_dialog').hide();
		$('#reg_dialog').css({"left":x,"top":y});
		$('#reg_dialog').show();
	});
	
	$('#reg_dialog_logon_btn').click(function() {
		var x = $('#reg_dialog').css("left");
		var y = $('#reg_dialog').css("top");
		$('#reg_dialog').hide();
		$('#logon_dialog').css({"left":x,"top":y});
		$('#logon_dialog').show();
	});
	
	$('#reg_dialog_cancel').click(function() {
		$('#reg_dialog').hide();
	}); 
	
	$('#logon_dialog_confirm').click(function() {
		var un = $('#logon_dialog_un').val();
		if(!un || un == '') {
			$('#dialog_err').html('请填写手机号码');
			return;
		}
		// get pwd
		var pwd = $('#logon_dialog_pwd').val();
		if(!pwd || pwd == '') {
			$('#dialog_err').html('请填写密码');
			return;
		}
		// call logon API
		//TODO lock screen
		var status = null;
		ilunch.login(un, pwd, true, function(data) {
			if(data.error)
				status = data.error;
			else if(data.success) {
				//assign userId and user
				ilunch.getCurrentUserInfo(function(data) {
					user = data;
					status = 'OK';
				});
			}
		});
		function wait() {
			if(status != null) {
				if(status == 'OK') {
					// re-render userinfo
					processor1 = setInterval(renderUserInfo, 50);
					$('#dialog_err').html('');
					$('#logon_dialog').hide();
				}
				else {
					// show error msg
					$('#dialog_err').html(status);
				}
				//TODO unlock screen
				clearInterval(p);
			}
		}
		var p = setInterval(wait, 50);
	}); 
	
	$('#reg_dialog_confirm').click(function() {
		// get un
		var un = $('#reg_dialog_phone').val();
		if(!un || un == '') {
			$('#dialog_err_reg').html('请填写手机号码！');
			return;
		}
		// get pwd
		var pwd = $('#reg_dialog_pwd').val();
		if(!pwd || pwd == '') {
			$('#dialog_err_reg').html('请填写密码！');
			return;
		}
		var pwd2 = $('#reg_dialog_pwd2').val();
		if(!pwd || pwd == '') {
			$('#dialog_err_reg').html('请确认密码！');
			return;
		}
		if(pwd != pwd2) {
			$('#dialog_err_reg').html('两次输入的密码不一致！');
			return;
		}
		// call reg API
		//TODO lock screen
		var status = null;
		ilunch.register(un, pwd, function(data) {
			if(data.error)
				status = data.error.message;
			else {
				ilunch.login(un, pwd, true, function(data) {
					if(data.error)
						ilunch.fatalError("logon failed!");
					else if(data.success) {
						//assign userId and user
						ilunch.getCurrentUserInfo(function(data) {
							user = data;
							status = 'OK';
						});
					}
				});
			}
		});
		function wait2() {
			if(status != null) {
				if(status == 'OK') {
					// re-render userinfo
					processor1 = setInterval(renderUserInfo, 50);
					$('#dialog_err_reg').html('');
					$('#reg_dialog').hide();
				}
				else {
					// show error msg
					$('#dialog_err_reg').html(status);
				}
				//TODO unlock screen
				clearInterval(p2);
			}
		}
		var p2 = setInterval(wait2, 50);
	}); 

	md_disorder = function(id, date) {
		date = ilunch.makeDate(date);
		var sd = cart.getOrderByIdAndDate(id, date, false);
		var price = sd.price;
		var quantity = sd.quantity;
		if(!sd || !price || !quantity)
			return;
		//1.del from cart;
		cart.deleteOrder(false, date, id);
		//2.re-render cart;
		processor2 = setInterval(renderCart, 50);
		in_total.html(cart.getTotalMoney());
		if(parseInt(in_total.html()) < 0)
			in_total.html('0');
	};
});