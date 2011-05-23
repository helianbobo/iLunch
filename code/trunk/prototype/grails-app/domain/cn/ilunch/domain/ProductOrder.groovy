package cn.ilunch.domain

class ProductOrder {
    public static String SUBMITTED = "SUBMITTED"
    public static String COMPLETED = "COMPLETED"
    public static String PAID = "PAID"
    public static String CANCELLED = "CANCELLED"

    Customer customer
    DistributionPoint distributionPoint
    String status

    Date orderDate
    Date completeDate

    PointChange pointChange

    BigDecimal amount

    List shipments
    List orderItems

    static hasMany = [
            orderItems: OrderItem,
            shipments: Shipment,
            appliedPriceRule: PriceRule,
            payments: Payment
    ]

    static constraints = {
        pointChange(nullable: true)
        shipments(nullable: true)
        amount(min: new BigDecimal(0))
        completeDate(nullable: true)
    }

    boolean acknowledgeable() {
        status in [SUBMITTED]
    }

    boolean cancellable() {
        status in [SUBMITTED, PAID]
    }

    def getDisplayStatus() {
        switch (status) {
            case SUBMITTED:
                return "未付款"

            case PAID:
                return "已支付"

            case COMPLETED:
                return "已完成"
            case CANCELLED:
                return "已取消"
        }
        return "未知"
    }
}
