<html>
	<head>
		<script type="text/javascript" src="${resource(dir:'js/prototype/ilunch', file:'jquery-1.5.2.min.js')}" ></script>
		<script type="text/javascript" src="${resource(dir:'js/prototype/ilunch', file:'JSONUtil.js')}" ></script>
		<script type="text/javascript" src="${resource(dir:'js/prototype/ilunch', file:'dataapi.js')}" ></script>
		<script type="text/javascript" src="${resource(dir:'js/prototype/ilunch', file:'cart.js')}" ></script>
	</head>
	<body>
		<div>
		<button onclick="getDistributionAreaList();">getDistributionAreaList</button>
		<button onclick="getMainDishInfo();">getMainDishInfo</button>
		<button onclick="getSideDishInfo();">getSideDishInfo</button>
		<button onclick="getMainDishListOnIndexPage();">getMainDishListOnIndexPage</button>
		<button onclick="getMainDishListOnSelectionPage();">getMainDishListOnSelectionPage</button>
		<button onclick="getSideDishListOnIndexPage();">getSideDishListOnIndexPage</button>
		<button onclick="getSideDishListOnSelectionPage();">getSideDishListOnSelectionPage</button>
		<button onclick="getUserInfo();">getUserInfo</button>
		<button onclick="getCart();">getCart</button>
		<button onclick="saveCart();">saveCart</button>
		<button onclick="confirmOrder();">confirmOrder</button>
		<button onclick="login();">login</button>
		<button onclick="getCurrentUser();">getCurrentUser</button>
		
		</div>
		<div>
		<textarea id="show" cols="100" rows="100"></textarea>
		</div>
		<script>
			
			var ilunch = $.ilunch_namespace("cn.ilunch");
			
			function show(data) {
				$("#show").html(JsonUti.convertToString(data));
			}
			function getDistributionAreaList() {
				ilunch.getDistributionAreaList(show);
			}
			function getMainDishInfo() {
				ilunch.getMainDishInfo(3, 1,show);
			}
			function getSideDishInfo() {
				ilunch.getSideDishInfo(1, 1,show);
			}
			function getMainDishListOnIndexPage() {
				ilunch.getMainDishListOnIndexPage('2010-10-10', 1, show);
			}
			function getMainDishListOnSelectionPage() {
				ilunch.getMainDishListOnSelectionPage('2012-10-10', '2012-10-10', 1, show);
			}
			function getSideDishListOnIndexPage() {
				ilunch.getSideDishListOnIndexPage('2010-10-10', 1, show);
			}
			function getSideDishListOnSelectionPage() {
				ilunch.getSideDishListOnSelectionPage('2010-10-10', 1, show);
			}
			function getUserInfo() {
				ilunch.getUserInfo("3", show);
			}
			function getCart() {
				ilunch.getCart(show);
			}
			function saveCart() {
				ilunch.saveCart(new ilunch.Cart(
					{
						area : "张江高科",
						distributionPoint : '凌阳大厦',
						pointChange : 10,
						products : [
							{
								date : '2011-05-10',
								mainDishes : [
									{
										id : 3,
										name :  '茄汁鱼',
										imageURL : "images/pic_15.jpg",
										price : 35,
										quantity : 3
									}
								],
								sideDishes : [
									{
										id : 2,
										name :  "豆角",
										imageURL : "images/pic_8.jpg",
										price : 35,
										quantity : 2
									}
								]
							},
							{
								date : '2011-05-12',
								mainDishes : [
									{
										id : 5,
										name :  "咖喱鱼",
										imageURL : "images/pic_15.jpg",
										price : 35,
										quantity : 4
									}
								],
								sideDishes : [
									{
										id : 4,
										name :  "凉皮",
										imageURL : "images/pic_8.jpg",
										price : 35,
										quantity : 5
									}
								]
							}
						]
					}).toString(), 
					show
				);
			}
			function confirmOrder() {
				ilunch.confirmOrder(new ilunch.Cart(
						{
							area : "张江高科",
							buildingId : 6,
							distributionPoint : '凌阳大厦',
							pointChange : 10,
							products : [
								{
									date : '2011-05-26',
									mainDishes : [],
									sideDishes : [
										{
											id : 2,
											quantity : 2
										}
									]
								}
							]
						}).toOrderString(3), 
						show);
			}
			
			function login() {
				ilunch.login('13764511823', 'jleo', false, show);
			}
			function getCurrentUser() {
				ilunch.getCurrentUserInfo(show);	
			}
			
		</script>
	</body>
</html>