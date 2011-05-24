package cn.ilunch.domain

class Shipment {

    Date shipmentDate
    String status
    static belongsTo = [productOrder: ProductOrder]
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
            productNames += it.product.name + ","
        }
        productNames
    }

    def getDisplayStatus = {
        System.out.println(status);
        switch(status){
            case CREATED:
                "已生成订单"
            case SHIPPED:
                "已配送"
        }
    }

    def getAmount = {
        orderItems*.price.sum()
    }
}
