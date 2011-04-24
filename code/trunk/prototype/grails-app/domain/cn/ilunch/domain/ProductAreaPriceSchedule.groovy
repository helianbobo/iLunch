package cn.ilunch.domain

class ProductAreaPriceSchedule {

    Product product
    Date fromDate
    Date toDate
    DistributionArea area
    BigDecimal price

    int quantity
    int remain

    static constraints = {
        toDate(nullable:true)
        price(min:new BigDecimal("0"))
        quantity(min:1)
        remain(min:0)
    }
}
