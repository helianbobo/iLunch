package cn.ilunch.domain

class Customer extends Person {

    Building primaryBuilding

    BigDecimal accountBalance
    BigDecimal pointBalance


    static hasMany = [
            orders: ProductOrder,
            buildings: Building,
            payments:Payment,
            pointChanges:PointChange
    ]


    static constraints = {
    }
}
