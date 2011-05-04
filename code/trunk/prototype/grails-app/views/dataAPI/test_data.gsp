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
				ilunch.getCart('2010-10-10', '2011-10-10', show);
			}
			function saveCart() {
				ilunch.saveCart(show);
			}
			function confirmOrder() {
				ilunch.confirmOrder(show);
			}
			
		</script>
	</body>
</html>