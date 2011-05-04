package test

class DataAPIController {

    def index = { 
		render(view:"test_data")
	}
	
	def pickMainDish = {
		render(view:"test_pick_main_dish")
	}
}
