package cn.ilunch.domain

class MainDish extends Product{



    static constraints = {

    }

    def type(){
        "主菜"
    }

def imageSizeDesc(){
[small:"51*51",medium:"174*150",large:"224*194",story:"426 * 280"]
}

}
