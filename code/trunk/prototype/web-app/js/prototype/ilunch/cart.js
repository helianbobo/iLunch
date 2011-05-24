(function($){
	
	var undefined;
	
	var ilunch = $.ilunch_namespace("cn.ilunch");
	
	/**
	 * Constructor
	 * @param cart - cart json object or json text
	 */
	ilunch.Cart = function(cart){
		this.__classname__ = "ilunch.Cart";
		
		//TODO remove unnecesary field in cart
		if(typeof(cart)==='string')
			cart = $.parseJSON(cart);
		else if(!cart) {
			cart = 	{
						area : "",
						distributionPoint : '',
						pointChange : 0,
						products : []
					};
		}
		this.cart = {};
		$.extend(true, this.cart, cart);
		
		this._currentWeekCursor = 0;
	};
	
	/////////////////////////////////
	//////// Private Methods  ///////
	/////////////////////////////////
	
	ilunch.Cart.prototype._getWeekOrder = function(startDate) {
		var endDate = new Date(startDate.getFullYear(), startDate.getMonth(), startDate.getDate()+4);
		retval = [];
		for(var i = 0; i < this.cart.products.length; i++) {
			var pdate = ilunch.makeDate(this.cart.products[i].date);
			if(pdate <= endDate && pdate >= startDate)
				retval.push(this.cart.products[i]);
		}
		return {startDate:startDate, endDate:endDate, products:retval};
	};
	
	ilunch.Cart.prototype._getWeekOrderByCurrentCursor = function() {
		var d = new Date();
		return this._getWeekOrder(new Date(d.getFullYear(), d.getMonth(), d.getDate()-((d.getDay()+6)%7)+this._currentWeekCursor*7));
	};
	
	/////////////////////////////////
	//////// Public Methods   ///////
	/////////////////////////////////
	/**
	 * Get products within the week indicated by current week cursor. 
	 * current week cursor is initialized to 0 representing current week.
	 * 
	 * @return {startDate, endDate, products:[{date:xxx,mainDishes:[...],sideDishes:[...]},{...},...]}
	 */
	ilunch.Cart.prototype.getCurrentWeekOrder = function() {
		return this._getWeekOrderByCurrentCursor();
	};
	
	/**
	 * Get products within last week indicated by current week cursor.
	 * Current week cursor will be deducted by 1
	 * 
	 * @return refer to getCurrentWeekOrder()
	 */
	ilunch.Cart.prototype.getLastWeekOrder = function() {
		this._currentWeekCursor--;
		return this._getWeekOrderByCurrentCursor();
	};
	
	/**
	 * Get products within next week indicated by current week cursor.
	 * Current week cursor will be incremented by 1
	 * 
	 * @return refer to getCurrentWeekOrder()
	 */
	ilunch.Cart.prototype.getNextWeekOrder = function() {
		this._currentWeekCursor++;
		return this._getWeekOrderByCurrentCursor();
	};
	
	/**
	 * Get products within the week that contains aDate.
	 * 
	 * @param aDate - a Date object or 'yyyy-mm-dd' string
	 * @return refer to getCurrentWeekOrder()
	 */
	ilunch.Cart.prototype.getWeekOrder = function(aDate) {
		var d = ilunch.makeDate(aDate);
		return this._getWeekOrder(new Date(d.getFullYear(), d.getMonth(), d.getDate()-((d.getDay()+6)%7)));
	};
	
	/**
	 * Add a new product to cart.
	 * 
	 * @param isMainDish - true for maindish and false for sidedish
	 */
	ilunch.Cart.prototype.addOrder = function(isMainDish, date, id, name, imageURL, price, quantity) {
		var dateIndex = -1;
		var products = this.cart.products;
		for(var i = 0; i < products.length; i++) {
			var pDate = ilunch.makeDate(products[i].date);
			var oDate = ilunch.makeDate(date);
			if((pDate >= oDate) && (pDate <= oDate)) {
				dateIndex = i;
				var dishes = products[i][isMainDish?'mainDishes':'sideDishes'];
				for(var j = 0; j < dishes.length; j++) {
					if(dishes[j].id == id) {
						dishes[j].quantity += quantity;
						return;
					}
				}
			}
		}
		if(dateIndex >= 0) {
			products[dateIndex][isMainDish?'mainDishes':'sideDishes'].push({id:id, name:name, imageURL:imageURL, price:price, quantity:quantity});
		}
		else {
			var dish = {id:id, name:name, imageURL:imageURL, price:price, quantity:quantity};
			var data = {
					date: date,
					mainDishes:[],
					sideDishes:[]
			};
			if(isMainDish)
				data.mainDishes.push(dish);
			else
				data.sideDishes.push(dish);
			products.push(data);
		}
	};
	
	/**
	 * delete a product in cart.
	 */
	ilunch.Cart.prototype.deleteOrder = function(isMainDish, date, id) {
		var products = this.cart.products;
		for(var i = 0; i < products.length; i++) {
			var pDate = ilunch.makeDate(products[i].date);
			var oDate = ilunch.makeDate(date);
			if((pDate >= oDate) && (pDate <= oDate)) {
				var dishes = products[i][isMainDish?'mainDishes':'sideDishes'];
				for(var j = 0; j < dishes.length; j++) {
					if(dishes[j].id == id) {
						dishes.splice(j, 1);
						if(products[i].mainDishes.length == 0 && products[i].sideDishes.length == 0)
							products.splice(i, 1);
						return;
					}
				}
			}
		}
	};
	
	/**
	 * get cart. 
	 * 
	 * DO NOT USE...
	 */
	ilunch.Cart.prototype.getCart = function() {
		return this.cart;
	};
	
	ilunch.Cart.prototype.getOrderByIdAndDate = function(id, date, isMainDish) {
		var products = this.cart.products;
		for(var i = 0; i < products.length; i++) {
			var pDate = ilunch.makeDate(products[i].date);
			var oDate = ilunch.makeDate(date);
			if((pDate >= oDate) && (pDate <= oDate)) {
				var dishes = products[i][isMainDish?'mainDishes':'sideDishes'];
				for(var j = 0; j < dishes.length; j++) {
					if(dishes[j].id == id) {
						return dishes[j];
					}
				}
			}
		}
	};
	
	ilunch.Cart.prototype.getOrderById = function(id, isMainDish) {
		var products = this.cart.products;
		for(var i = 0; i < products.length; i++) {
			var dishes = products[i][isMainDish?'mainDishes':'sideDishes'];
			for(var j = 0; j < dishes.length; j++) {
				if(dishes[j].id == id) {
					return dishes[j];
				}
			}
		}
	};
	
	ilunch.Cart.prototype.getOrdersByDate = function(date, isMainDish) {
		var products = this.cart.products;
		for(var i = 0; i < products.length; i++) {
			var pDate = ilunch.makeDate(products[i].date);
			var oDate = ilunch.makeDate(date);
			if((pDate >= oDate) && (pDate <= oDate)) {
				var dishes = products[i][isMainDish?'mainDishes':'sideDishes'];
				return dishes;
			}
		}
		return [];
	};
	
	ilunch.Cart.prototype.getTotalMoney = function() {
		var ct = 0;
		var products = this.cart.products;
		for(var i = 0; i < products.length; i++) {
			for(var j = 0; j < products[i].mainDishes.length; j++)
				ct += (products[i].mainDishes[j].price*products[i].mainDishes[j].quantity);
			for(var j = 0; j < products[i].sideDishes.length; j++)
				ct += (products[i].sideDishes[j].price*products[i].sideDishes[j].quantity);
		}
		return ct;
	};
	
	/**
	 * Serialize cart to JSON string
	 * 
	 * @return json string
	 */
	ilunch.Cart.prototype.toString = function() {
		var strarr = [];
		strarr.push('{');
		if(this.cart.area != null && this.cart.area != undefined)
			strarr.push('"area":"'+this.cart.area+'",');
		if(this.cart.distributionPoint != null && this.cart.distributionPoint != undefined)
			strarr.push('"distributionPoint":"'+this.cart.distributionPoint+'",');
		if(this.cart.buildingId != null && this.cart.buildingId != undefined)
			strarr.push('"buildingId":'+this.cart.buildingId+',');
		if(this.cart.pointChange != null && this.cart.pointChange != undefined)
			strarr.push('"pointChange":'+this.cart.pointChange+',');
		strarr.push('"products":[');
		for(var i = 0; i < this.cart.products.length; i++) {
			strarr.push('{');
			var d = this.cart.products[i].date;
			if(typeof(d) == 'object')
				d = (d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate());
			strarr.push('"date":"'+d+'",');
			strarr.push('"mainDishes":[');
			for(var j = 0; j < this.cart.products[i].mainDishes.length; j++) {
				strarr.push('{');
				strarr.push('"id":'+this.cart.products[i].mainDishes[j].id+',');
				strarr.push('"name":"'+this.cart.products[i].mainDishes[j].name+'",');
				strarr.push('"imageURL":"'+this.cart.products[i].mainDishes[j].imageURL+'",');
				strarr.push('"price":"'+this.cart.products[i].mainDishes[j].price+'",');
				strarr.push('"quantity":'+this.cart.products[i].mainDishes[j].quantity);
				strarr.push('}');
				if(j != this.cart.products[i].mainDishes.length-1)
					strarr.push(',');
			}
			strarr.push('],');
			
			strarr.push('"sideDishes":[');
			for(var j = 0; j < this.cart.products[i].sideDishes.length; j++) {
				strarr.push('{');
				strarr.push('"id":'+this.cart.products[i].sideDishes[j].id+',');
				strarr.push('"name":"'+this.cart.products[i].sideDishes[j].name+'",');
				strarr.push('"imageURL":"'+this.cart.products[i].sideDishes[j].imageURL+'",');
				strarr.push('"price":"'+this.cart.products[i].sideDishes[j].price+'",');
				strarr.push('"quantity":'+this.cart.products[i].sideDishes[j].quantity);
				strarr.push('}');
				if(j != this.cart.products[i].sideDishes.length-1)
					strarr.push(',');
			}
			strarr.push(']');
			
			strarr.push('}');
			if(i != this.cart.products.length-1)
				strarr.push(',');
		}
		strarr.push(']');
		strarr.push();
		strarr.push('}');
		return strarr.join('');
	};
	
	ilunch.Cart.prototype.toOrderString = function(userId) {
		var strarr = [];
		strarr.push('{');
		if(userId != null && userId != undefined)
			strarr.push('"id":'+userId+',');
		if(this.cart.buildingId != null && this.cart.buildingId != undefined)
			strarr.push('"buildingId":'+this.cart.buildingId+',');
		if(this.cart.pointChange != null && this.cart.pointChange != undefined)
			strarr.push('"pointChange":'+this.cart.pointChange+',');
		strarr.push('"orders":[');
		for(var i = 0; i < this.cart.products.length; i++) {
			strarr.push('{');
			var d = this.cart.products[i].date;
			if(typeof(d) == 'object')
				d = (d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate());
			strarr.push('"date":"'+d+'",');
			strarr.push('"mainDishes":[');
			for(var j = 0; j < this.cart.products[i].mainDishes.length; j++) {
				strarr.push('{');
				strarr.push('"id":'+this.cart.products[i].mainDishes[j].id+',');
				strarr.push('"quantity":'+this.cart.products[i].mainDishes[j].quantity);
				strarr.push('}');
				if(j != this.cart.products[i].mainDishes.length-1)
					strarr.push(',');
			}
			strarr.push('],');
			
			strarr.push('"sideDishes":[');
			for(var j = 0; j < this.cart.products[i].sideDishes.length; j++) {
				strarr.push('{');
				strarr.push('"id":'+this.cart.products[i].sideDishes[j].id+',');
				strarr.push('"quantity":'+this.cart.products[i].sideDishes[j].quantity);
				strarr.push('}');
				if(j != this.cart.products[i].sideDishes.length-1)
					strarr.push(',');
			}
			strarr.push(']');
			
			strarr.push('}');
			if(i != this.cart.products.length-1)
				strarr.push(',');
		}
		strarr.push(']');
		strarr.push();
		strarr.push('}');
		return strarr.join('');
	};
	
	ilunch.Cart.prototype.isEmpty = function() {
		var isEmpty = true;
		var products = this.cart.products;
		for(var i = 0; i < products.length; i++) {
			if(products[i].mainDishes.length > 0 || products[i].sideDishes.length > 0)
				return false;
		}
		return isEmpty;
	};
	
})(jQuery);