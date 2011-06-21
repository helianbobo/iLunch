$(document).ready(function($){
	
	var undefined;

	var ilunch = $.ilunch_namespace("cn.ilunch");
	
	///////////////////////////////////////////////////////
	////////////////// global declaration /////////////////
	///////////////////////////////////////////////////////
	
	var sideDishList = null;
	var cart = null;
	var areaId = $('#area_id').val();
	if(areaId.length <= 0)
		ilunch.fatalError("area id not found!");
	
	///////////////////////////////////////////////////////
	////////////////// initialization /////////////////////
	///////////////////////////////////////////////////////

	var currentPage = 1;
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
	
	listElem.empty();
	currentDateElem.html(currentDateElem.html().replace('##YY##', Date.today().getFullYear()).replace('##MM##', 
    		ilunch.doubleDigit(Date.today().getMonth()+1)).replace('##DD##', 
    				ilunch.doubleDigit(Date.today().getDate())).replace('##WW##', ilunch.digitToCNSS(Date.today().getDay())));

	///////////////////////////////////////////////////////
	////////////////// send request for data  /////////////
	///////////////////////////////////////////////////////
	
	ilunch.lockScreen();
	
	$.when(
		ilunch.getSideDishListOnSelectionPage(
			ilunch.dateToString(Date.today()),
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
					for(var j = 0; j < data[i].flavors.length; j++)
						flavors[data[i].flavors[j].value] = false;
				}
				sideDishList = currentList = arr;
				$('#total_page').html(_getTotalPageN());
			}
		),
		ilunch.getCart(
			function(data) {
				cart = new ilunch.Cart(data);
                in_total.html(cart.getTotalMoney());
//				//delete
//				cart.addOrder(true, new Date(2011,4,12), 1, "逼鱼", "images/pic_17.jpg", 2);
//				cart.addOrder(true, new Date(2011,4,9), 2, "盖浇饭", "images/pic_17.jpg", 2);
			}
		)
	).done(function() {
		renderSDList();
		cart.render();
		ilunch.unlockScreen();
	});
	
	
	
	
	///////////////////////////////////////////////////////
	////////////////// define templates ///////////////////
	///////////////////////////////////////////////////////
	
	var dateDisplayer = '##MM##/##DD## 周##WW## ##TODAY##';
	
	var orderTmplt = '<div class="xc_sl">'+
						'<input onclick="dec_quantity(##SD_ID##);" class="jianyi" name="" type="button" value="" />'+
						'<input id="quantity_##SD_ID##" type="text" class="shuliang" maxlength="3" value="1"/>'+
						'<input onclick="inc_quantity(##SD_ID##)" class="jiayi" name="" type="button" value="" />'+
						'份  '+
					 '</div>'+
					 '<input class="xuangou" onclick="md_order(##SD_ID##,\'##SD_NAME##\',this);" onmouseover="this.className=\'xuangou_1\'" onmouseout="this.className=\'xuangou\'" name="" type="button" value="" />';
	
	var disorderTmplt = '<span class="xc_sl">已选购##SEL_N##份</span>';

	
	var dishTmplt = ('<div class="xc_li">'+
						'<div class="xc_p"><a onclick="onSDDetail(##SD_ID##);"><img src="##IMG##" /></a></div>'+
						'<div class="xc_t">##SD_NAME##</div>'+
						'<div id="order_##SD_ID##" class="cai_s">'+
							orderTmplt+
						'</div>'+
					'</div>').replace(/##DATE##/g, dateDisplayer);
	
	var noDishTmplt = '';
	
	///////////////////////////////////////////////////////
	///// wait until data's ready then render page ////////
	///////////////////////////////////////////////////////
	
	renderSDList = function() {
        //render logic start
        
        $('#tag_list').empty();
        $('#tag_list').append('分类标签：');
        $('#tag_list').append('<a onclick="change_flavor(\'全部\')">全部</a> | ');
        for (var i in flavors) {
            if (flavors[i]) 
                $('#tag_list').append('<a class="on" onclick="change_flavor(\'' + i + '\')">' + i + '</a> | ');
            else 
                $('#tag_list').append('<a onclick="change_flavor(\'' + i + '\')">' + i + '</a> | ');
        }
        
        listElem.empty();
        //get items list based on currentPage;
        var sdList = _getItemsOfPage();
        
        //see if any of the items have been seleted in cart, render accordingly;
        var i = 0;
        for (i = 0; i < sdList.length; i++) {
            if (sdList[i]) {
                var sel_sd = cart.getOrderById(sdList[i].id, false);
                var price = sdList[i].prices[0].price;
                var sd_name = sdList[i].name;
                var img = ilunch.makeIMGPath(sdList[i].id,'medium');
                var divText = dishTmplt;
                divText = divText.replace(/##SD_ID##/g, sdList[i].id).replace(/##PRICE##/g, price);
                divText = divText.replace(/##SD_NAME##/g, sd_name).replace(/##IMG##/g, img);
                listElem.append(divText);
                if (sel_sd) {
                    in_total.html(cart.getTotalMoney());
                    $('#order_' + sel_sd.id).append(disorderTmplt.replace(/##SEL_N##/g, sel_sd.quantity));
                }
                
            }
        }
        if (i < NItemsPerPage - 1) {
            while (i++ < NItemsPerPage) 
                listElem.append(noDishTmplt);
        }
        //render logic end
	};
	
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
			renderSDList();
		}
	});
	
	$('#last_page_btn').click(function() {
		//TODO decrease page number, determine if disable this btn;
		if(_hasPrevPage()) {
			currentPage--;
			$('#current_page').html(currentPage);
			renderSDList();
		}
	});
	
	
	lastWeek = function(elem) {
		//change current date values to the first day of last week;
		cart.getLastWeekOrder();
		cart.render();
		if(elem) {
			var sd_id = $('#date_picker').find("input[type=hidden]").val();
			if($('#sd_detail_dialog').css("display") == 'block')
				$('#sd_detail_dialog').find("input[name=doOrder]").click();
			else {
				var sd_id = $('#date_picker').find("input[type=hidden]").val();
				$('#order_'+sd_id).find("input.xuangou").click();
			}
		}
	};
	
	nextWeek = function(elem) {
		//change current date values to the first day of next week;
		cart.getNextWeekOrder();
		cart.render();
		if(elem) {
			if($('#sd_detail_dialog').css("display") == 'block')
				$('#sd_detail_dialog').find("input[name=doOrder]").click();
			else {
				var sd_id = $('#date_picker').find("input[type=hidden]").val();
				$('#order_'+sd_id).find("input.xuangou").click();
			}
		}
	};
	
	$('#btn_confirm_last').click(function() {
		ilunch.saveCart(cart.toString(), function(data){
			if(data) {
				window.location.href = ilunch.ROOT+"dataAPI/pickMainDish";
				return true;
			}
		});
	});
	
	$('#btn_confirm_next').click(function() {
		if(cart.isEmpty()) {
			alert("请至少添加一道主菜/配菜！");
			return;
		}
		ilunch.saveCart(cart.toString(), function(data){
			if(data) {
				window.location.href = ilunch.ROOT+"dataAPI/confirmInfo";
				return true;
			}
		});
	});
	
	md_order = function(id, name, elem) {
		//0.render date picker then pop it up
		var quantity = parseInt($('#quantity_'+id).val());
		if(!quantity || quantity <= 0)
			return;
		
		datePicker.find("input[type=hidden]").val(id);
		var ul = datePicker.children("ul");
		ul.empty();
		var sd = cart.getCurrentWeekOrder().startDate;
		var pickableDate = Date.today().addDays(ilunch.ReserveDay-1);
		for(var i = 0, di = sd; i < 5; i++, di = di.clone().next().day()) {
			var dateTitle = '周'+ilunch.digitToCNSS(i+1)+' '+(di.getMonth()+1)+'/'+di.getDate();
			//less than or equals
			if(di.compareTo(pickableDate) != 1) {
				ul.append('<li><div>'+dateTitle+'<br /></div></li>');
			}
			else {
				var price = ilunch.getPriceByDate(ilunch.getOrderById(sideDishList, id).prices, ilunch.makeDate(di));
				ul.append('<li><a onclick="md_order_from_date_picker('+id+',\''+ilunch.dateToString(di)+'\','+quantity+',\''+name+'\')" onmouseover="this.className=\'pc_on\'" onmouseout="this.className=\'pc_off\'"><div>'+dateTitle+'<br />'+price+'￥</div></a></li>');
			}
		}
		var pos = $.position(elem, $(elem).closest('.pc_l'));
		datePicker.css({"left":pos.x, "top": pos.y, "display":"block"});
	};
	
	md_order_from_date_picker = function(id, date, quantity, name) {
		//1.add to cart;
		var sd = ilunch.getOrderById(sideDishList, id);
		date = ilunch.makeDate(date);
		if(sd && quantity > 0 && name) {
			var price = ilunch.getPriceByDate(sd.prices, date);
			cart.addOrder(false, date, id, name, price, quantity);
			//2.re-render cart;
			cart.render();
			$('#quantity_'+id).val('1');
			$('#order_'+id).children("span").remove();
			$('#order_'+id).append(disorderTmplt.replace(/##SEL_N##/g, cart.getOrderByIdAndDate(id, date, false).quantity));
			datePicker.css({"display":"none"});
			in_total.html(cart.getTotalMoney());
			if($('#sd_detail_dialog').css("display") == 'block') {
				closeSDDetail();
			}
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
		cart.render();
		$('#order_'+id).children("span").remove();
		in_total.html(cart.getTotalMoney());
		if(parseInt(in_total.html()) < 0)
			in_total.html('0');
	};
	
	dec_quantity = function(id) {
		var quantity = parseInt($('#quantity_'+id).val());
		if(!quantity)
			quantity = 0;
		quantity = (quantity-1)<0?0:(quantity-1);
		$('#quantity_'+id).val(quantity);
		$('#sd_detail_dialog').find("input[name=quantity]").val(quantity);
	};
	
	inc_quantity = function(id) {
		var quantity = parseInt($('#quantity_'+id).val());
		if(!quantity)
			quantity = 0;
		quantity = quantity+1;
		$('#quantity_'+id).val(quantity);
		$('#sd_detail_dialog').find("input[name=quantity]").val(quantity);
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
				for(var j = 0; j < sideDishList[i].flavors.length; j++)
					if(flavor == sideDishList[i].flavors[j].value)
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
		renderSDList();
	};
	
	onSDDetail = function(id) {
		ilunch.lockScreen();
        //get sd info and render
        var sd = ilunch.getOrderById(sideDishList, id);
        $('#sd_detail_dialog').find("img[name=img]").attr({"src":ilunch.makeIMGPath(sd.id,'large')});
        $('#sd_detail_dialog').find("strong[name=name]").html(sd.name);
        var flvs = '';
        for(var i = 0; i < sd.flavors.length; i++)
        	flvs += (sd.flavors[i].value+" ");
        $('#sd_detail_dialog').find("li[name=flavor]").html(flvs);
        $('#sd_detail_dialog').find("li[name=story]").html(sd.story);
        
        var ctrlTmpl = '<input class="jianyi" type="button" onclick="dec_quantity(##SD_ID##);" />'+ 
					   '<input class="shuliang_2" name="quantity" type="text" value="##QUANTITY##" />'+ 
					   '<input class="jiayi" type="button" onclick="inc_quantity(##SD_ID##);"/>'+
					   '<input onclick="md_order(##SD_ID##,\'##SD_NAME##\',this);" class="button_10" onmouseover="this.className=\'button_10_1\'" onmouseout="this.className=\'button_10\'" type="button" name="doOrder" />';
		
        var quantity = $('#quantity_'+id).val();
        ctrlTmpl = ctrlTmpl.replace(/##SD_ID##/g, id).replace(/##QUANTITY##/g, quantity).replace(/##SD_NAME##/g, sd.name);
        $('#sd_detail_dialog').find("li.sl").empty();
        $('#sd_detail_dialog').find("li.sl").append(ctrlTmpl);
        $('#sd_detail_dialog').show();
	};
	
	closeSDDetail = function() {
		$('#sd_detail_dialog').hide();
		ilunch.unlockScreen();
	};
});