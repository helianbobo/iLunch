package cn.ilunch.domain

class SideDish extends Product{



    static constraints = {
    }

    def type(){
        "配菜"
    }
    def imageSizeDesc(){
[small:"54*54",medium:"119*119",large:"343*343",story:"426*280"]
}
}
