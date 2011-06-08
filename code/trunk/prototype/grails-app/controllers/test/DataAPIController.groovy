package test

import cn.ilunch.domain.ProductOrder
import cn.ilunch.domain.Shipment

class DataAPIController {
    def springSecurityService

    def index = {
        render(view: "test_data")
    }

    def pickMainDish = {
        render(view: "test_pick_main_dish")
    }

    def pickSideDish = {
        render(view: "test_pick_side_dish")
    }

    def confirmInfo = {
        render(view: "test_confirm_info")
    }

    def confirmOrder = {
        render(view: "test_confirm_order")
    }

    def orderSuccess = {
        def orderId = params.orderId
        if (!orderId) {
            render(view: "test_order_success")
            return
        }
        def order = ProductOrder.get(orderId)
        final distributionPoint = order.getDistributionPoint()
        def contactor = springSecurityService.currentUser

        def dateItemRegistry = order.orderItems.groupBy {orderItem ->
            orderItem.shippmentDate
        }

        def shipments = []
        dateItemRegistry.each {date, items ->
            shipments << new Shipment(shipmentDate: date, orderItems: items)
        }
        render(view: "test_order_success", model: [order:order, contactor: contactor, shipments: shipments, distributionPoint: distributionPoint])
    }
}
