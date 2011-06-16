(function($){
	
	var undefined;
	
	$.extend({
		/**
		 * to create/import a namespace
		 * 
		 * @param {Object} ns
		 */
		ilunch_namespace : function(ns) {
			if (!ns)
				throw new Error("name required");
			if (typeof (ns) != "string")
				throw new Error("name has to be a string");
			if (ns.charAt(0) == '.' || ns.charAt(ns.length - 1) == '.' || ns.indexOf("..") != -1)
				throw new Error("illegal name: " + ns);

			ns = ns.split(".");
			var o = window;
			for ( var i = 0; i < ns.length; i++) {
				o[ns[i]] = o[ns[i]] || {};
				o = o[ns[i]];
			}
			return o;
		}
	});
	
	// declare package
	var ilunch = $.ilunch_namespace("cn.ilunch");
	
	
	/**
	 * validate if certain properties are exist
	 */
	ilunch.validateData = function(data, props, name) {
		for(var i = 0; i < props.length; i++) {
			if(!data.hasOwnProperty(props[i]) ) {   //|| data[props[i]] == undefined || data[props[i]] == null
				ilunch.fatalError(props[i]+' in '+name+' not found!');
				return false;
			}
		}
		return true;
	};
	
	/**
	 * indicates that current processing should be aborted. Needs refine.
	 */
	ilunch.fatalError = function(errmsg) {
		alert("FATAL ERROR(NEEDS REFINE): "+errmsg);
	};
	
	/**
	 * validate cart json object schema
	 * for internal use
	 */
	_validateCart = function(data) {
		if(!data || data.length <= 0)
			return false;
		if(!ilunch.validateData(data, ['area', 'distributionPoint', 'pointChange', 'products'], 'cart'))
			return false;
		for(var i = 0; i < data.products.length; i++) {
			if(!ilunch.validateData(data.products[i], ['date', 'mainDishes', 'sideDishes'], 'cart.products['+i+']'))
				return;
			for(var j = 0; j < data.products[i].mainDishes.length; j++) {
				if(!ilunch.validateData(data.products[i].mainDishes[j], ['id', 'name', 'quantity'], 'cart.products['+i+'].mainDishes['+j+']'))
					return false;
			}
			for(var j = 0; j < data.products[i].sideDishes.length; j++) {
				if(!ilunch.validateData(data.products[i].sideDishes[j], ['id', 'name', 'quantity'], 'cart.products['+i+'].sideDishes['+j+']'))
					return false;
			}
		}
		return true;
	};

	/////////////////////////////////
	///////  Global Settings ////////
	/////////////////////////////////
	
	ilunch.VERSION = "1.0";

	ilunch.BUILD_NUMBER = 1;

	ilunch.enableDebug = false;

	ilunch.LOG = function(msg, level) {
		// TODO support log level
		if (typeof (console) != 'undefined')
			console.log(msg);
	};

	/**
	 * Change this when needed
	 */
	var root = window.location.pathname;
	ilunch.ROOT = '/prototype/';
	
	/**
	 * Ajax global setting goes here
	 */
	$.ajaxSetup({
		timeout: 5000,
		error: function(jqXHR, textStatus, errorThrown){
			if(textStatus == 'timeout') {
				
			}
			else if(textStatus == 'error') {
				document.write('error occurred:'+JsonUti.convertToString(jqXHR.responseText));
			}
			else if(textStatus == 'abort') {
				
			}
			else {
				
			}
		},
		statusCode: {
			404: function(){alert('server returned 404')}
		}
	});
	
	////////////////////////////////////////
	//            Data APIs       //////////
	///////////////////////////////////////
	
	ilunch.getMainDishInfo = function(productId, areaId, handler) {
		if(productId == null || productId == undefined) {
			ilunch.fatalError("Main dish id not found!");
			return;
		}
        if(areaId == null || areaId == undefined) {
			ilunch.fatalError("Area id not found!");
			return;
		}
		params = {
			productId: productId,
            areaId:areaId
		};
		$.getJSON(ilunch.ROOT+'product/showDetail', params, function(data) {
			if(data.error) {
				
			}
			else {
				if(!ilunch.validateData(data, ['name', 'price', 'startDate','endDate', 'imageURL', 'story','remain','quantity'], 'maindish'))
					return;
			}
			handler(data);
		});
	};
	
	ilunch.getSideDishInfo = function(productId,areaId, handler) {
		if(productId == null || productId == undefined) {
			ilunch.fatalError("Side dish id not found!");
			return;
		}
        if(areaId == null || areaId == undefined) {
			ilunch.fatalError("Area id not found!");
			return;
		}
		params = {
			productId: productId,
            areaId:areaId
		};
		$.getJSON(ilunch.ROOT+'product/showDetail', params, function(data) {
			if(data.error) {
				
			}
			else {
				if(!ilunch.validateData(data, ['name', 'price', 'startDate','endDate', 'imageURL', 'story','remain','quantity'], 'sidedish'))
					return;
			}
			handler(data);
		});
	};

	ilunch.getMainDishListOnIndexPage = function(date, areaId, handler, max) {
		if(!date || !areaId) {
			ilunch.fatalError("date or areaId not found!");
			return;
		}
		params = {
			date:date,
			areaId:areaId
		};
		if(max)
			params.max = max;
		$.getJSON(ilunch.ROOT+'product/listAllMainDishOnIndexPage', params, function(data) {
			if(data.error) {
				
			}
			else {
				data = data.products;
				if(!data || data.length <= 0) {
					ilunch.fatalError("maindishes list is empty");
					return;
				}
				for(var i = 0; i < data.length; i++) {
					if(!ilunch.validateData(data[i], ['id', 'name', 'prices', 'imageURL'], 'MainDishList['+i+']'))
						return;
                    for(var j = 0; j < data[i].prices.length; j++) {
						if(!ilunch.validateData(data[i].prices[j], ['startDate', 'endDate', 'price'], 'MainDishList['+i+'].prices['+j+']'))
							return;
					}
				}
			}
			handler(data);
		});
	};

	ilunch.getMainDishListOnSelectionPage = function(fromDate, toDate, max, areaId, handler) {
		if(!fromDate || !areaId) {
			ilunch.fatalError('invalide fromDate or toDate or areaId!');
			return;
		}
		params = {
			date: fromDate,
			areaId: areaId
		};
		if(toDate)
			params.toDate = toDate;
		if(max)
			params.max = max;
		return $.getJSON(ilunch.ROOT+'product/listAllMainDishOnSelectionPage', params, function(data) {
					if(data.error) {
						ilunch.fatalError("[DataAPI::listAllMainDishOnSelectionPage]Server responded data with error:"+data.error.message);
						return;
					}
					else {
						data = data.products;
						if(!data || data.length <= 0) {
							ilunch.fatalError("mainDishes list is empty");
							return;
						}
						for(var i = 0; i < data.length; i++) {
							if(!ilunch.validateData(data[i], ['id', 'name', 'prices', 'imageURL'], 'MainDishList['+i+']'))
								return;
		                    for(var j = 0; j < data[i].prices.length; j++) {
								if(!ilunch.validateData(data[i].prices[j], ['startDate', 'endDate', 'price'], 'MainDishList['+i+'].prices['+j+']'))
									return;
							}
						}
					}
					handler(data);
				});
	};
	
	ilunch.getSideDishListOnIndexPage = function(date, areaId, handler, max) {
		if(!date || !areaId) {
			ilunch.fatalError("date or areaId not found!");
			return;
		}
		params = {
			date:date,
			areaId:areaId
		};
		if(max)
			params.max = max;
		$.getJSON(ilunch.ROOT+'product/listAllSideDishOnIndexPage', params, function(data) {
			if(data.error) {
				
			}
			else {
				data = data.products;
				if(!data || data.length <= 0) {
					ilunch.fatalError("sideDishes list is empty");
					return;
				}
				for(var i = 0; i < data.length; i++) {
					if(!ilunch.validateData(data[i], ['id', 'name', 'flavors', 'prices', 'imageURL'], 'SideDishList['+i+']'))
						return;
					for(var j = 0; j < data[i].prices.length; j++) {
						if(!ilunch.validateData(data[i].prices[j], ['startDate', 'endDate', 'price'], 'SideDishList['+i+'].prices['+j+']'))
							return;
					}
				}
			}
			handler(data);
		});
	};

	ilunch.getSideDishListOnSelectionPage = function(date, areaId, handler) {
		if(!date || !areaId) {
			ilunch.fatalError("date or areaId not found!");
			return;
		}
		params = {
			date:date,
			areaId:areaId
		};
		return	$.getJSON(ilunch.ROOT+'product/listAllSideDishOnSelectionPage', params, function(data) {
			if(data.error) {
				ilunch.fatalError("[DataAPI::listAllSideDishOnSelectionPage]Server responded data with error:"+data.error.message);
				return;
			}
			else {
				data = data.products;
				if(!data || data.length <= 0) {
					ilunch.fatalError("sideDishes list is empty");
					return;
				}
				for(var i = 0; i < data.length; i++) {
					if(!ilunch.validateData(data[i], ['id', 'name', 'flavors', 'prices', 'imageURL'], 'SideDishList['+i+']'))
						return;
					for(var j = 0; j < data[i].prices.length; j++) {
						if(!ilunch.validateData(data[i].prices[j], ['startDate', 'endDate', 'price'], 'SideDishList['+i+'].prices['+j+']'))
							return;
					}
				}
			}
			handler(data);
		});
	};

    ilunch.getCurrentUserInfo = function(handler) {

		params = {
		};
		return	$.getJSON(ilunch.ROOT+'person/loggedInUserPreference', params, function(data) {
			if(data.error) {

			}
			else {
				if(!ilunch.validateData(data, ['id', 'phoneNumber'], 'user'))
					return;
//				if(!ilunch.validateData(data.distributionArea, ['id', 'name'], 'user.distributionArea'))
//					return;
//				if(!ilunch.validateData(data.building, ['id', 'name', 'longitude', 'latitude'], 'user.building'))
//					return;
			}
			handler(data);
		});
	};

	ilunch.getUserInfo = function(id, handler) {
		if(!id) {
			ilunch.fatalError("[getUserInfo]invalid id");
			return;
		}
		params = {
			id:id	
		};
		return	$.getJSON(ilunch.ROOT+'person/preference', params, function(data) {
			if(data.error) {
				
			}
			else {
				if(!ilunch.validateData(data, ['id', 'phoneNumber', 'distributionArea', 'building'], 'user'))
					return;
				if(!ilunch.validateData(data.distributionArea, ['id', 'name'], 'user.distributionArea'))
					return;
				if(!ilunch.validateData(data.building, ['id', 'name', 'longitude', 'latitude'], 'user.building'))
					return;
			}
			handler(data);
		});
	};
	
	ilunch.getDistributionAreaList = function(handler) {
		return	$.getJSON(ilunch.ROOT+'distributionArea/list', function(data) {
			if(data.error) {
				
			}
			else {
				if(data.length <= 0)
					return;
				for(var i = 0; i < data.length; i++) {
					if(!ilunch.validateData(data[i], ['id', 'name', 'buildings'], 'DAList['+i+']'))
						return;
					if(data[i]['buildings'].length <= 0)
						return;
					for(var j = 0; j < data[i]['buildings'].length; j++)
						if(!ilunch.validateData(data[i]['buildings'][j], ['id', 'name'], 'DAList['+i+'].buildings['+j+']'))
							return;
				}
			}
			handler(data);
		});
	};
	
	ilunch.getCart = function(handler) {
		return	$.getJSON(ilunch.ROOT+'person/cart', function(data) {
					if(data.error) {
						data = null;
					}
					else {
						if(!_validateCart(data))
							return;
					}
					handler(data);
				});
	};
	
	ilunch.saveCart = function(data, handler) {
		if(!data)
			return;
		if(!_validateCart($.parseJSON(data)))
			return;
		return $.ajax(ilunch.ROOT+'person/saveCart', {
            cache:false,
//			processData:false,
			data:{cartInfo:data},
			success:function(data) {
				if(data.error) {

				}
				else {

				}
				handler(data);
			},
			complete:function(data) {
				if(data.error) {

				}
				else {

				}
				handler(data);
			},
			dataType:"json",
			type: 'POST'
		});
	};
	
	ilunch.confirmOrder = function(data, handler) {
		if(!data)
			return;
		return	$.ajax(ilunch.ROOT+'productOrder/confirm', {
			processData:false,
                    cache:false,
			data:data,
			success:function(data) {
				if(data.error) {
//					ilunch.fatalError("[confirmOrder]Fail to confirm order! Err:"+data.error.message);
//					data = data.error.errorCode;
				}
				else {
//					data = 0;
				}
				handler(data);
			},
			dataType:"json",
			type: 'POST',
			contentType : 'application/json'
		});
	};
	
	ilunch.login = function(username, password, rememberMe, handler) {
		if(!username || username == '')
			return '请填写用户名';
		if(!password || password == '')
			return '请填写密码';
		var params = {
			'j_username' : username,
			'j_password' : password
		};
		if(rememberMe)
			params._spring_security_remember_me = 'on';
			
		return	$.ajax(ilunch.ROOT+'j_spring_security_check', 
			{
				data : params,
                cache:false,
				type : 'POST',
				dataType : "json",
				success : function(data) {
					if(data.error) {
						
					}
					else {
						
					}
					handler(data);
				},
				statusCode: {
					401: function(){alert('server returned 401')}
				}
			}
		);
	};
	
	ilunch.register = function(username, password, handler) {
		if(!username || username == '') {
			ilunch.fatalError('[Register]no username found!');
			return;
		}
		if(!password || password == '') {
			ilunch.fatalError('[Register]no password found!');
			return;
		}
		var params = {
			'cellNumber' : username,
			'password' : password
		};
		
		return	$.ajax(ilunch.ROOT+'person/register', 
			{
				data : params, 
				type : 'POST',
                cache:false,
				dataType : "json",
				success : function(data) {
					if(data.error) {
						
					}
					else {
						
					}
					handler(data);
				},
				statusCode: {
					401: function(){alert('server returned 401')}
				}
			}
		);
	};
	
	////////////////////////////////////////
	//            Utils          //////////
	///////////////////////////////////////
	
	//TODO Deprecated
	ilunch.getPriceByDate = function(prices, date) {
		for(var i = 0; i < prices.length; i++) {
			if(!prices[i])
				continue;
			if(!prices[i].startDate)
				continue;
			var sd = ilunch.makeDate(prices[i].startDate);
			var ed = (!prices[i].endDate) ? null : ilunch.makeDate(prices[i].endDate);
			if(sd <= date && (!ed || ed >= date))
				return prices[i].price;
		}
	};
	
	ilunch.getOrderById = function(orderList, id) {
		for(var i = 0; i < orderList.length; i++)
			if(orderList[i].id == id)
				return orderList[i];
	};
	
	ilunch.digitToCNSS = function(d) {
		if(d < 0 || d > 6)
			return;
		var cnss = '';
		switch(d) {
		case 1:
			cnss = '一';
			break;
		case 2:
			cnss = '二';
			break;
		case 3:
			cnss = '三';
			break;
		case 4:
			cnss = '四';
			break;
		case 5:
			cnss = '五';
			break;
		case 6:
			cnss = '六';
			break;
		case 0:
			cnss = '日';
			break;
		default:
			break;
		}
		return cnss;
	};
	
	ilunch.doubleDigit = function(d) {
		if(d < 0)
			return;
		if(d >= 10)
			return d+'';
		if(d >= 0 && d < 10)
			return '0'+d;
	};
	
	ilunch.makeDate = function(dStr) {
		if(typeof(dStr) == 'object')
			return dStr;
		var p = /(\d+)-(\d+)-(\d+)/;
		var r = p.exec(dStr);
		return new Date(parseInt(r[1], 10), parseInt(r[2], 10)-1, parseInt(r[3], 10));
	};
	
	ilunch.dateToString = function(d) {
		return ''+d.getFullYear()+'-'+ilunch.doubleDigit(d.getMonth()+1)+'-'+ilunch.doubleDigit(d.getDate());
	};
	
	ilunch.lockScreen = function() {
		//is exists?
		var elem = $('#ilunch_screen_locker');
		if(elem.length <= 0) {
			$(document.body).append('<div id="ilunch_screen_locker" class="black_overlay"><div style="position:absolute;left:50%;top:50%;z-index:1002"><img src="'+ilunch.ROOT+'images/wait01.gif" /></div></div>');
			elem = $('#ilunch_screen_locker');
		}
		//enable if off
		if(elem.css("display") == "none") {
			elem.css({"display":"block"});
		}
	};
	
	ilunch.unlockScreen = function() {
		//is exists?
		var elem = $('#ilunch_screen_locker');
		if(elem.length <= 0) {
			return;
		}
		if(elem.css("display") == "block") {
			elem.css({"display":"none"});
		}
	};
	
	ilunch.startCountDown = function(dueDate) {
		if(!dueDate)
			return;
		var elem = $('#countdown');
		if(elem.length <= 0)
			return;
		var indicators = elem.find(".stress");
		var diff = dueDate - new Date();
		if(!diff || diff < 0)
			return;
		if(ilunch.countDownStarted)
			return;
		ilunch.countDownStarted = true;
		var hour = Math.floor(diff/(3600*1000));
		diff -= hour*3600*1000;
		var minute = Math.floor(diff/(60*1000));
		diff -= minute*60*1000;
		var second = Math.floor(diff/1000);
		
		function setValue() {
			indicators[0].innerHTML = ilunch.doubleDigit(hour);
			indicators[1].innerHTML= ilunch.doubleDigit(minute);
			indicators[2].innerHTML= ilunch.doubleDigit(second);
		}
		
		setValue();
		
		function doCountDown() {
			second = parseInt(indicators[2].innerHTML, 10);
			minute = parseInt(indicators[1].innerHTML, 10);
			hour = parseInt(indicators[0].innerHTML, 10);
			second--;
			if(second < 0) {
				second = 59;
				minute--;
				if(minute < 0) {
					minute = 59;
					hour--;
					if(hour < 0) {
						hour = 0;
						clearInterval(pcd);
					}
				}
			}
			
			setValue();
		}
		
		var pcd = setInterval(doCountDown, 1000);
	};
	
})(jQuery);