package cn.ilunch.domain

class Shipment {

    Date shipmentDate
    String status
    static belongsTo = [productOrder: ProductOrder]
    List orderItems
    String serialNumber
    int sentCounter

    static String CREATED = "CREATED"
    static String SHIPPED = "SHIPPED"
    static String CANCELLED = "CANCELLED"

    static hasMany = [orderItems: OrderItem]

    static constraints = {
        sentCounter(min: 0)
    }

    def getDisplayingProductName() {
        def productNames = ""
        orderItems.each {
            productNames += it.product.name + ","
        }
        productNames
    }

    def getDisplayStatus = {
        System.out.println(status);
        switch (status) {
            case CREATED:
                return "已生成配送单"
            case SHIPPED:
                return "已配送"
            case CANCELLED:
                return "已取消配送"
        }
        return ""
    }

    def getAmount = {
        orderItems*.price.sum()
    }
}
