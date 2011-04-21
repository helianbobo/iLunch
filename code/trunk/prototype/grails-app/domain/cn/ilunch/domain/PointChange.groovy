package cn.ilunch.domain

class PointChange {

    Customer customer
    BigDecimal point
    String reason
    ProductOrder productOrder

    static constraints = {
        reason(nullable:true)
    }
}
