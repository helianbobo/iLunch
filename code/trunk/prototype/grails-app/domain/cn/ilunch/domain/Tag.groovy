package cn.ilunch.domain

class Tag {
    String value
    static hasMany = [products: Product]

    static belongsTo = Product
    static constraints = {
        value(nullable: false,unique: true)
    }

    @Override
    int hashCode() {
        return value.hashCode()
    }

    @Override
    String toString() {
        value
    }


}
