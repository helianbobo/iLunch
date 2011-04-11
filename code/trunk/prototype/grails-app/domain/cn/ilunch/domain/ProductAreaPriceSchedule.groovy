package cn.ilunch.domain

class ProductAreaPriceSchedule {

    Product product
    Date fromDate
    Date toDate
    DistributionArea area
    BigDecimal price

    static constraints = {
    }
}
