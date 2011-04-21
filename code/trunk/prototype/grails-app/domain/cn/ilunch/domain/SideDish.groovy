package cn.ilunch.domain

class SideDish extends Product{

    String flavor

    static constraints = {
    }

    def type(){
        "配菜"
    }
}
