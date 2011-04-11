package cn.ilunch.domain

class Manager extends Person{

    static belongsTo = [kitchen:Kitchen]

    static constraints = {
        kitchen(nullable: true)
    }
}
