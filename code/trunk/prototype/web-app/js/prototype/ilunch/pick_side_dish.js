$(document).ready(function($){
	
	var undefined;

	var ilunch = $.ilunch_namespace("cn.ilunch");
	
	///////////////////////////////////////////////////////
	////////////////// global declaration /////////////////
	///////////////////////////////////////////////////////
	
	var sideDishList = null;
	var cart = null;
	var areaId = $('#area_id').val();
	if(!areaId || areaId == '')
		ilunch.fatalError("area id not found!");
	
	///////////////////////////////////////////////////////
	////////////////// initialization /////////////////////
	///////////////////////////////////////////////////////
	
	var d = new Date();
	var currentM = null;
	var currentD = null;
	var currentY = null;
	var currentDay = null;
	var currentPage = null;
	var NItemsPerPage = 8;
	var currentFlavor = null;
	var currentList = null;
	var flavors = [];
	
	var listElem = $('#sd_list');
	if(listElem.length <= 0)
		ilunch.fatalError('list elem not found!');
	var currentDateElem = $('#current_date');
	if(currentDateElem.length <= 0)
		ilunch.fatalError('current_date elem not found!');
	var in_total = $('#in_total');
	in_total.html('0');
	var datePicker = $('#date_picker');
	datePicker.mouseleave(function(){
		$(this).css({"display":"none"});
	});
	
	function initialize() {
		currentM = d.getMonth();
		currentD = d.getDate();
		currentY = d.getFullYear();
		currentDay = d.getDay();
		currentPage = 1;
		
		listElem.empty();
		currentDateElem.html(currentDateElem.html().replace('##YY##', currentY).replace('##MM##', 
				ilunch.doubleDigit(currentM+1)).replace('##DD##', 
						ilunch.doubleDigit(currentD)).replace('##WW##', ilunch.digitToCNSS(currentDay)));
	}
	
	initialize();
	
	///////////////////////////////////////////////////////
	////////////////// send request for data  /////////////
	///////////////////////////////////////////////////////
	
	ilunch.getSideDishListOnSelectionPage(
			''+currentY+'-'+(1+currentM)+'-'+currentD,
			areaId,
			function(data) {
//				//delete
//				for(var i = 100; i < 121; i++) {
//					var fo = {};
//					$.extend(fo, data[0], true);
//					fo.id = i;
//					fo.name = data[0].name+i;
//					data.push(fo);
//				}
//				//TODO delete
//				var f = ['变态辣','疯狂咸','无理甜'];
//				for(var i in data)
//					data[i].flavors[0] = f[Math.floor(Math.random()*3)];
				
				var arr = [];
				for(var i in data) {
					arr.push(data[i]);
					for(var j in data[i].flavors)
						flavors[data[i].flavors[j]] = false;
				}
				sideDishList = currentList = arr;
				$('#total_page').html(_getTotalPageN());
			}
	);
	
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
	
	var dateDisplayer = '##MM##/##DD## 周##WW## ##TODAY##';
	
	var orderTmplt = '<div class="xc_sl">'+
						'<input onclick="dec_quantity(##SD_ID##);" class="jianyi" name="" type="button" value="" />'+
						'<input id="quantity_##SD_ID##" type="text" class="shuliang" maxlength="3" />'+
						'<input onclick="inc_quantity(##SD_ID##)" class="jiayi" name="" type="button" value="" />'+
						'份  '+
					 '</div>'+
					 '<input class="xuangou" onclick="md_order(##SD_ID##,\'##SD_NAME##\',\'##IMG##\',this);" onmouseover="this.className=\'xuangou_1\'" onmouseout="this.className=\'xuangou\'" name="" type="button" value="" />';
	
	var disorderTmplt = '<span class="xc_sl">已选购##SEL_N##份</span>';

	
	var dishTmplt = ('<div class="xc_li">'+
						'<div class="xc_p"><a href="#"><img src="/prototype/##IMG##" /></a></div>'+
						'<div class="xc_t">##SD_NAME##</div>'+
						'<div id="order_##SD_ID##" class="cai_s">'+
							orderTmplt+
						'</div>'+
					'</div>').replace(/##DATE##/g, dateDisplayer);
	
	var noDishTmplt = '';
	
	///////////////////////////////////////////////////////
	///// wait until data's ready then render page ////////
	///////////////////////////////////////////////////////
	
	//TODO lock the screen
	
	var busy1 = false;
	renderSDList = function() {
		if(cart && sideDishList && !busy1) {
			busy1 = true;
			
			//render logic start
			
			$('#tag_list').empty();
			$('#tag_list').append('分类标签：');
			$('#tag_list').append('<a onclick="change_flavor(\'全部\')">全部</a> | ');
			for(var i in flavors) {
				if(flavors[i])
					$('#tag_list').append('<a class="on" onclick="change_flavor(\''+i+'\')">'+i+'</a> | ');
				else
					$('#tag_list').append('<a onclick="change_flavor(\''+i+'\')">'+i+'</a> | ');
			}
			
			listElem.empty();
			//get items list based on currentPage;
			var sdList = _getItemsOfPage();
			
			//see if any of the items have been seleted in cart, render accordingly;
			var i = 0;
			for(i = 0; i < sdList.length; i++) {
				if(sdList[i]) {
	                var sel_sd = cart.getOrderById(sdList[i].id, false);
	                var price = sdList[i].prices[0].price;
	                var sd_name = sdList[i].name;
	                var img = sdList[i].imageURL;
                    var divText = dishTmplt;
                    divText = divText.replace(/##SD_ID##/g, sdList[i].id).replace(/##PRICE##/g, price);
					divText = divText.replace(/##SD_NAME##/g, sd_name).replace(/##IMG##/g, img);
					listElem.append(divText);
					if(sel_sd) {
	                    in_total.html(cart.getTotalMoney());
	        			$('#order_'+sel_sd.id).append(disorderTmplt.replace(/##SEL_N##/g, sel_sd.quantity));
					}
					
				}
			}
			if(i < NItemsPerPage-1) {
				while(i++ < NItemsPerPage)
					listElem.append(noDishTmplt);
			}
			//render logic end
			
			busy1 = false;
			clearInterval(processor1);
			//TODO release screen lock here
		}
	};

	var busy2 = false;
	renderCart = function() {
		if(cart && sideDishList && !busy2) {
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
	
	//wait for data to be ready
	var processor1 = setInterval(renderSDList, 50);
	var processor2 = setInterval(renderCart, 50);
	
	function _getItemsOfPage() {
		result = [];
		for(var i = (currentPage-1)*NItemsPerPage, j = 0; j < NItemsPerPage; i++, j++) {
			result.push(currentList[i]);
		}
		return result;
	}
	
	
	function _hasNextPage() {
		if(currentList[currentPage*NItemsPerPage])
			return true;
		else
			return false;
	}
	
	function _hasPrevPage() {
		if(currentPage <= 1)
			return false;
		if(currentList[(currentPage-2)*NItemsPerPage])
			return true;
		else
			return false;
	}
	
	function _getTotalPageN() {
		if(!currentList || !currentList.length)
			return 0;
		return Math.ceil(currentList.length / NItemsPerPage);
	}
	
	///////////////////////////////////////////////////////
	///////////////   bind event handlers   ///////////////
	///////////////////////////////////////////////////////
	
	$('#next_page_btn').click(function() {
		//TODO increase page number, determine if disable this btn;
		if(_hasNextPage()) {
			currentPage++;
			$('#current_page').html(currentPage);
			processor1 = setInterval(renderSDList, 50);
		}
	});
	
	$('#last_page_btn').click(function() {
		//TODO decrease page number, determine if disable this btn;
		if(_hasPrevPage()) {
			currentPage--;
			$('#current_page').html(currentPage);
			processor1 = setInterval(renderSDList, 50);
		}
	});
	
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
				$('#confirm_form').attr({"action":"/prototype/dataAPI/pickMainDish"});
				$('#confirm_form').submit();
				return true;
			}
		});
	});
	
	$('#btn_confirm_next').click(function() {
		ilunch.saveCart(cart.toString(), function(data){
			if(data) {
				$('#confirm_form').attr({"action":"/prototype/dataAPI/confirmInfo"});
				$('#confirm_form').submit();
				return true;
			}
		});
	});
	
	md_order = function(id, name, imageURL, elem) {
		//0.render date picker then pop it up
		var quantity = parseInt($('#quantity_'+id).val());
		if(!quantity || quantity <= 0)
			return;
		var ul = datePicker.children("ul");
		ul.empty();
		var sd = cart.getCurrentWeekOrder().startDate;
		for(var i = 0, di = sd; i < 5; i++, di = new Date(di.getFullYear(), di.getMonth(), di.getDate()+1)) {
			if(di < d) {
				ul.append('<li><div>周'+ilunch.digitToCNSS(i+1)+'<br /></div></li>');
			}
			else {
				var price = ilunch.getPriceByDate(ilunch.getOrderById(sideDishList, id).prices, ilunch.makeDate(di));
				ul.append('<li><a onclick="md_order_from_date_picker('+id+',\''+ilunch.dateToString(di)+'\','+quantity+',\''+name+'\',\''+imageURL+'\')" onmouseover="this.className=\'pc_on\'" onmouseout="this.className=\'pc_off\'"><div>周'+ilunch.digitToCNSS(i+1)+'<br />'+price+'￥</div></a></li>');
			}
		}
		datePicker.css({"left":elem.offsetLeft+"px", "top":elem.offsetTop+"px", "display":"block"});
	};
	
	md_order_from_date_picker = function(id, date, quantity, name, imageURL) {
		//1.add to cart;
		var sd = ilunch.getOrderById(sideDishList, id);
		date = ilunch.makeDate(date);
		if(sd && quantity > 0 && name) {
			var price = ilunch.getPriceByDate(sd.prices, date);
			cart.addOrder(false, date, id, name, imageURL, price, quantity);
			//2.re-render cart;
			processor2 = setInterval(renderCart, 50);
			$('#quantity_'+id).val('');
			$('#order_'+id).children("span").remove();
			$('#order_'+id).append(disorderTmplt.replace(/##SEL_N##/g, cart.getOrderByIdAndDate(id, date, false).quantity));
			datePicker.css({"display":"none"});
			in_total.html(cart.getTotalMoney());
		}
	};
	
	md_disorder = function(id, date) {
		date = ilunch.makeDate(date);
		var sd = ilunch.getOrderById(sideDishList, id);
		var price = ilunch.getPriceByDate(sd.prices, date);
		var quantity = cart.getOrderByIdAndDate(id, date, false).quantity;
		if(!sd || !price || !quantity)
			return;
		//1.del from cart;
		cart.deleteOrder(false, date, id);
		//2.re-render cart;
		processor2 = setInterval(renderCart, 50);
		$('#order_'+id).children("span").remove();
		in_total.html(cart.getTotalMoney());
		if(parseInt(in_total.html()) < 0)
			in_total.html('0');
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
	
	change_flavor = function(flavor) {
		//get new item list based on selection;
		if(!flavor)
			return;
		currentList = [];
		if(flavor == '全部') {
			currentList = sideDishList;
		}
		else {
			for(var i = 0; i < sideDishList.length; i++)
				for(var j in sideDishList[i].flavors)
					if(flavor == sideDishList[i].flavors[j])
						currentList.push(sideDishList[i]);
		}
		for(var i in flavors)
			if(flavors[i])
				flavors[i] = false;
		if(flavor != '全部')
			flavors[flavor] = true;
		//change page number to 1;
		currentPage = 1;
		//render list;
		processor1 = setInterval(renderSDList, 50);
	};
});