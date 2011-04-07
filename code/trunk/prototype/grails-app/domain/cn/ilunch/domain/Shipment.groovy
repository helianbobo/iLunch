package cn.ilunch.domain

class Shipment {

    Date shipmentDate
    String status
    ProductOrder order

    static hasMany = [orderItems: OrderItem]

    static constraints = {
    }
}
