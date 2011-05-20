package cn.ilunch.domain

class Shipment {

    Date shipmentDate
    String status
    ProductOrder order
    List orderItems
    String serialNumber

    static String CREATED = "CREATED"
    static String SHIPPED = "SHIPPED"

    static hasMany = [orderItems: OrderItem]

    static constraints = {
    }

    def getDisplayingProductName(){
        def productNames = ""
        orderItems.each{
            productNames += it.name + ","
        }
    }

    def getDisplayStatus = {
        switch(status){
            case CREATED:
                "已生成订单"
            case SHIPPED:
                "已配送"
        }
    }
}
