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


	currentDateElem.html(currentDateElem.html().replace('##YY##', Date.today().getFullYear()).replace('##MM##', 
    		ilunch.doubleDigit(Date.today().getMonth()+1)).replace('##DD##', 
    				ilunch.doubleDigit(Date.today().getDate())).replace('##WW##', ilunch.digitToCNSS(Date.today().getDay())));
	$('#area_selector').html($('#area_selector').html().replace(/##AREA_NAME##/g, $('#area_name').html()));
	
	///////////////////////////////////////////////////////
	////////////////// send request for data  /////////////
	///////////////////////////////////////////////////////
	
	ilunch.lockScreen();
	
	function getUser() {
		var phone = $('#authLink').find("input[name=phone]").val();
		if(phone) {
			var contact = $('#authLink').find("input[name=contact]").val();
			var points = $('#authLink').find("input[name=points]").val();
			user = {id:uid, phoneNumber:phone, nickname:contact, points:points};
			var dfd = jQuery.Deferred();
			dfd.resolve();
			return dfd.promise();
		}
		else {
			return ilunch.getCurrentUserInfo(function(data) {
						user = data;
					});
		}
	}
	
	$.when(
		getUser(),
		ilunch.getCart(
			function(data) {
				cart = new ilunch.Cart(data);
	            in_total.html(cart.getTotalMoney());
	//				//delete
	//				cart.addOrder(true, new Date(2011,4,12), 1, "逼鱼", "images/pic_17.jpg", 2);
	//				cart.addOrder(true, new Date(2011,4,9), 2, "盖浇饭", "images/pic_17.jpg", 2);
			}
		),
        ilunch.getDistributionAreaList(function(data){
            daList = data.areas;
        })
	).done(function() {
		renderPointChange();
		cart.render();
		renderBuildingSelector();
		ilunch.unlockScreen();
	});



	renderPointChange = function() {

        //render log start...
        if (user.id) {
            var phone = user.phoneNumber;
            var contact = user.nickname;
            var points = user.points;
            //TODO validate pointChange balance
            $('#change_point').html(points ? points : 0);
            $('#point_control').css({
                        "display": "block"
                    });
        }
        else {
            $('#point_control').css({
                        "display": "none"
                    });
        }
        //render logic end
    };
	
	renderBuildingSelector = function() {
        //render log start...

        var map = new google.maps.Map(document.getElementById("map_canvas"), {
        	zoom: 15,
        	mapTypeId: google.maps.MapTypeId.ROADMAP
	    });

        $('#building_list').change(function(e){
            //TODO change sel_building value
            selBuilding = $("#building_list").find("option:selected").text();
            $('#sel_building').html(selBuilding);
            cart.getCart().distributionPoint = selBuilding;
            cart.getCart().area = $('#area_name').html();
            var buildingId = parseInt($(this).val());
            cart.getCart().buildingId = buildingId;
            changeMapCenter(buildingId);
        });
        
        $('#building_list').empty();
        var dpId = cart.getCart().buildingId;
        var i = 0;
        var buildingPositions = {};
        for (i = 0; i < daList.length; i++) {
            if (daList[i].id == areaId) {
            	for (var j = 0; j < daList[i].buildings.length; j++) {
                	var selected = (dpId == daList[i].buildings[j].id ? ' selected="selected"' : ''); 
                    $('#building_list').append('<option value="' + daList[i].buildings[j].id + '"'+selected+'>' + daList[i].buildings[j].name + '</option>');
                    var pos = new google.maps.LatLng(daList[i].buildings[j].latitude, daList[i].buildings[j].longitude);
                    buildingPositions[''+daList[i].buildings[j].id] = pos;
                    var marker = new google.maps.Marker({
                    	position: pos, 
                    	map: map, 
                    	title:daList[i].buildings[j].name
                    });
//                    google.maps.event.addListener(marker, 'click', function(e) {
//                    	var latLng = e;
//                    	for(var k in buildingPositions) {
//                    		if(buildingPositions[k].equals(latLng)) {
//                    			$('#building_list').val(k);
//                    			$('#building_list').change();
//                    			break;
//                    		}
//                    	}
//                    });
                }
            	break;
            }
        }
        $('#building_list').change();
        
        //render map
        function changeMapCenter(bId) {
        	map.setCenter(buildingPositions[''+bId]);
        }

//        google.maps.event.addListener(map, 'zoom_changed', function() {
//        	setTimeout(moveToDarwin, 1500);
//        });
//
        
//
//        function moveToDarwin() {
//          var darwin = new google.maps.LatLng(-12.461334, 130.841904);
//          map.setCenter(darwin);
//        }
        
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
				window.location.href = ilunch.ROOT+"dataAPI/pickSideDish";
				return true;
			}
		});
	});
	
	$('#btn_confirm_next').click(function() {
        var userId = $('#userId').val();

		if(!userId) {
			alert("请先登录!");
            $('#logon_lnk').trigger('click');
			return;
		}
		
		if(cart.isEmpty()) {
			alert("请至少添加一道主菜/配菜！");
			return;
		}
		
		var usedPoints = parseInt($('#change_point_input').val());
		usedPoints = usedPoints ? usedPoints : 0; 
		if(user.id)
			var totalPoints = user.points;
		else
			var totalPoints = parseInt($('#authLink').find("input[name=points]").val());
		if(usedPoints > totalPoints) {
			ilunch.fatalError("使用的积分不得超过您所有拥有的积分！");
			return;
		}
		cart.getCart().pointChange = usedPoints;
		
		ilunch.saveCart(cart.toString(), function(data){
			if(data) {
				window.location.href = ilunch.ROOT+"dataAPI/confirmOrder";
				return true;
			}
		});
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
		cart.render();
		in_total.html(cart.getTotalMoney());
		if(parseInt(in_total.html()) < 0)
			in_total.html('0');
	};
});