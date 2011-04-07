package cn.ilunch.domain

class Shipment {

    Date shipmentDate
    String status

    static hasMany = [orderItems: OrderItem]

    static constraints = {
    }
}
