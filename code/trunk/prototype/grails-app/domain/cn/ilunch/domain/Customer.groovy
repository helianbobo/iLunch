package cn.ilunch.domain

class Customer extends Person {

    Building primaryBuilding

    BigDecimal accountBalance = 0
    BigDecimal pointBalance = 0


    static hasMany = [
            orders: ProductOrder,
            buildings: Building,
            payments:Payment,
            pointChanges:PointChange
    ]


    static constraints = {
    }
}
