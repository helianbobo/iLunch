package cn.ilunch.domain

class Shipment {

    Date shipmentDate
    String status
    ProductOrder order
    List orderItems

    static String CREATED = "CREATED"
    static String SHIPPED = "SHIPPED"

    static hasMany = [orderItems: OrderItem]

    static constraints = {
    }
}
