package cn.ilunch.domain

import cn.ilunch.security.ILunchUserDetails
import org.springframework.web.servlet.ModelAndView

class OrderController {
    def springSecurityService
    def orderService

    //todo no tesecase for this
    def listWithinMonth = {
        Customer customer = springSecurityService.currentUser
        def cellNumber = customer.cellNumber

        params.max = Math.min(params.max ? params.int('max') : 10, 100)

        def c = ProductOrder.createCriteria()
        def orderList = c.list {
            gt('orderDate', new Date() - 31)
            eq('customer', customer)
            maxResults(params.max)
            order("orderDate", "desc")
        }
        def shipments = []
        orderList.each {
            shipments.addAll(it.shipments)
        }
        render(view: "list", model: [shipments: shipments, orders: orderList])
    }

    def listPending = {
        Customer customer = springSecurityService.currentUser
        def cellNumber = customer.cellNumber

        params.max = Math.min(params.max ? params.int('max') : 10, 100)

        def c = ProductOrder.createCriteria()
        def orderList = c.list {
            eq('status', ProductOrder.SUBMITTED)

            eq('customer', customer)
            maxResults(params.max)
            order("orderDate", "desc")
        }
        def shipments = []
        orderList.each {
            shipments.addAll(it.shipments)
        }
        render(view: "list", model: [shipments: shipments, orders: orderList])
    }

    def listCompleted = {
        Customer customer = springSecurityService.currentUser
        def cellNumber = customer.cellNumber

        params.max = Math.min(params.max ? params.int('max') : 10, 100)

        def c = ProductOrder.createCriteria()
        def orderList = c.list {
            eq('status', ProductOrder.COMPLETED)

            eq('customer', customer)
            maxResults(params.max)
            order("orderDate", "desc")
        }
        def shipments = []
        orderList.each {
            shipments.addAll(it.shipments)
        }
        render(view: "list", model: [shipments: shipments, orders: orderList])
    }

    def cancel = {
        def orderId = params.id
        def productOrder = ProductOrder.get(orderId)
        orderService.cancelOrder(productOrder)

        redirect(action: "list")
    }

    def acknowledge = {
        def orderId = params.id
        def productOrder = ProductOrder.get(orderId)
        orderService.acknowledgePayment(productOrder)
        redirect(action: "listWithinMonth")
    }
}
