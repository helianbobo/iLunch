package cn.ilunch.domain

class MainDish extends Product{

    String flavor

    static constraints = {
        flavor(nullable:true)
    }

    def type(){
        "主菜"
    }
}
