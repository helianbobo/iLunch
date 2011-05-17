package test

class DataAPIController {

    def index = { 
		render(view:"test_data")
	}
	
	def pickMainDish = {
		render(view:"test_pick_main_dish")
	}
	
	def pickSideDish = {
		render(view:"test_pick_side_dish")
	}
	
	def confirmInfo = {
		render(view:"test_confirm_info")
	}
	
	def confirmOrder = {
		render(view:"test_confirm_order")
	}
	
	def orderSuccess = {
		render(view:"test_order_success")
	}
}
