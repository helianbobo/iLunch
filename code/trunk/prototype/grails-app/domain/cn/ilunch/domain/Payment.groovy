package cn.ilunch.domain

class Payment {

    Customer customer
    BigDecimal amount
    String status
    ProductOrder order

    String alipayTransactionId

    static constraints = {
    }
}
