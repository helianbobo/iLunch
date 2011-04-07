package cn.ilunch.domain

class ProductOrder {

    DistributionPoint point
    String status
    Date shippingDate

    BigDecimal amount

    static hasMany = [
            orderItems: OrderItem,
            shipments:Shipment,
            appliedPriceRule: PriceRule,
            payments:Payment

    ]

    static constraints = {
    }
}
