package cn.ilunch.domain

import cn.ilunch.security.ILunchUserDetails

class OrderController {
    def springSecurityService
    def orderService

    //todo no tesecase for this
    def list = {
        def status = params.status

        ILunchUserDetails user = springSecurityService.currentUser
        def cellNumber = user.cellNumber

        params.max = Math.min(params.max ? params.int('max') : 10, 100)

        def c = ProductOrder.createCriteria()
        def orderList = c.list {
            if (status)
                eq('status', status)

            eq('customer.cellNumber', cellNumber)
            maxResults(params.max)
            order("orderDate", "desc")
        }
        def shipments = []
        orderList.each{
           shipments.addAll(it.shipments)
        }
        return [shipments: shipments]
    }

    def cancel = {
        def orderId = params.id
        def productOrder = ProductOrder.get(orderId)
        orderService.cancelOrder(productOrder)

        redirect(action:"list")
    }
}
