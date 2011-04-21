package cn.ilunch.domain

class OrderItem {

    ProductOrder order
    Product product
    BigDecimal price
    Integer quantity
    Date shippmentDate

    static constraints = {
    }
}
