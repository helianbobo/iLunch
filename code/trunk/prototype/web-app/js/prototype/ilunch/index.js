$(document).ready(function($){
	
	var undefined;

	var ilunch = $.ilunch_namespace("cn.ilunch");
	
	///////////////////////////////////////////////////////
	////////////////// global declaration /////////////////
	///////////////////////////////////////////////////////

	var areaId = $('#area_id').val();
	if(areaId.length <= 0)
		ilunch.fatalError("area id not found!");
	var firstMD = null;
	var cart = null;
	
	///////////////////////////////////////////////////////
	////////////////// initialization /////////////////////
	///////////////////////////////////////////////////////


	///////////////////////////////////////////////////////
	////////////////// send request for data  /////////////
	///////////////////////////////////////////////////////
	
	ilunch.lockScreen();
	
	$.when(
		ilunch.getMainDishListOnSelectionPage(
			ilunch.dateToString(Date.today().addDays(ilunch.ReserveDay)), 
			null,
			1,
			areaId,
			function(data) {
				for(var i in data) {
					firstMD = data[i];
					break;
				}
			}
		),
		ilunch.getCart(
			function(data) {
				cart = new ilunch.Cart(data);
			}
		)
	).done(function() {
		renderOnSale();
		ilunch.startCountDown(ilunch.makeDate(firstMD.prices[0].startDate));
		ilunch.unlockScreen();
	});
	
	///////////////////////////////////////////////////////
	////////////////// define templates ///////////////////
	///////////////////////////////////////////////////////
	
	
	///////////////////////////////////////////////////////
	///// wait until data's ready then render page ////////
	///////////////////////////////////////////////////////
	
	renderOnSale = function() {
		if(!firstMD)
			return;
		var elem = $('.tejia')[0];
		elem.innerHTML = elem.innerHTML.replace(/##MD_NAME##/g, firstMD.name).replace(/##PRICE##/g, firstMD.prices[0].price)
				.replace(/##REMAIN##/g, firstMD.remain).replace(/##MD_IMG##/g, ilunch.ROOT+firstMD.imageURL);
	};
	
	///////////////////////////////////////////////////////
	///////////////   bind event handlers   ///////////////
	///////////////////////////////////////////////////////
	
	onSelectOnSale = function() {
		var md = firstMD;
		var quantity = 1;
		date = ilunch.makeDate(firstMD.prices[0].startDate);
		if(md && quantity > 0) {
			var price = md.prices[0].price;
			cart.addOrder(true, date, md.id, md.name, md.imageURL, price, quantity);
			
			ilunch.saveCart(cart.toString(), function(data){
				if(data) {
					window.location.href = ilunch.ROOT+"dataAPI/pickSideDish";
					return true;
				}
			});
		}
	};
	
});