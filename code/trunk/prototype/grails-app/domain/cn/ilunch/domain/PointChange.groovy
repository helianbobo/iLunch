package cn.ilunch.domain

class PointChange {

    Customer customer
    BigDecimal point
    String reason
    static belongsTo = [productOrder: ProductOrder]
    static constraints = {
        reason(nullable: true)
    }
}
