package cn.ilunch.domain

class Tag {
    String value
    static hasMany = [products:Product]
    static belongsTo =  Product
    static constraints = {
    }
}
