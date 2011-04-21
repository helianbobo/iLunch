package cn.ilunch.service

import cn.ilunch.domain.ProductOrder
import cn.ilunch.domain.Shipment

class OrderService {

    static transactional = true

    def acknowledgePayment(ProductOrder productOrder) {
        if (!productOrder.orderItems)
            throw new OrderStatusException("empty order cannot be acknowledged")

        if (!productOrder.acknowledgeable()){
            throw new OrderStatusException("only orders with status set to SUBMITTED can be acknowledged")
        }


        def dateItemRegistry = productOrder.orderItems.groupBy {orderItem ->
            orderItem.shippmentDate
        }

        dateItemRegistry.each {date, items ->
            Shipment shipment = new Shipment(shipmentDate: date, status: Shipment.CREATED, order: productOrder, orderItems: items)
            productOrder.addToShipments shipment
        }

        productOrder.status = ProductOrder.PAID
        productOrder.save()
    }

    def cancelOrder(ProductOrder productOrder){
        switch(productOrder.status){
            case ProductOrder.SUBMITTED :
                break;
            case ProductOrder.PAID:
                productOrder.customer.accountBalance += productOrder.amount
                break;

            default: throw new OrderStatusException("current order status: ${productOrder.status}, only orders with status set to SUBMITTED or PAID can be cancelled")
        }
        productOrder.status = ProductOrder.CANCELLED
        productOrder.save()
    }

    def completeOrder(ProductOrder productOrder){
         switch(productOrder.status){
            case ProductOrder.PAID :
                break;

            default: throw new OrderStatusException("current order status: ${productOrder.status}, only orders with status set to PAID can be completed")
        }

        def today = new Date()
        today.clearTime()
        productOrder.completeDate = today
        productOrder.status = ProductOrder.COMPLETED
        productOrder.save()
    }
}
