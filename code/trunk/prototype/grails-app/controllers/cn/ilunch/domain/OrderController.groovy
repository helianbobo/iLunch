package cn.ilunch.domain

import cn.ilunch.security.ILunchUserDetails
import org.springframework.web.servlet.ModelAndView

class OrderController {
    def springSecurityService
    def orderService
    def serialNumberService
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
        def shipmentCriteria = Shipment.createCriteria()
        def shipmentList = shipmentCriteria.list {
            gt('shipmentDate', new Date() - 31)
            eq('status', Shipment.CREATED)
            inList('productOrder', orderList)
            maxResults(params.max)
            order("shipmentDate", "desc")
        }

        render(view: "list", model: [shipments: shipmentList, orders: orderList])
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

        redirect(action: "listWithinMonth")
    }

    def acknowledge = {
        def orderId = params.id
        def productOrder = ProductOrder.get(orderId)
        orderService.acknowledgePayment(productOrder)
        redirect(action: "listWithinMonth")
    }

    def sendSN = {
        def shipmentId = params.shipmentId
        if (shipmentId == null) {
            flash.message = "订单ID未知"
            redirect(action: "listWithinMonth")
            return
        }
        def shipment = Shipment.get(shipmentId)
        if (shipment.sentCounter >= 3) {
            flash.message = "发送次数过多，请手动记录"
            redirect(action: "listWithinMonth")
            return
        } else {
            serialNumberService.send(shipment.serialNumber)
            shipment.sentCounter++
            shipment.save()
            System.out.println("sending sms:" + shipment.serialNumber);
            flash.message = "发送成功"
            redirect(action: "listWithinMonth")
            return
        }
    }

    def cancelShipment = {
        def shipmentId = params.shipmentId
        if (shipmentId == null) {
            flash.message = "订单ID未知"
            redirect(action: "listWithinMonth")
            return
        }
        def shipment = Shipment.get(shipmentId)
        orderService.cancelShipment(shipment)
        if (shipmentId == null) {
            flash.message = "已将付款返还到账户余额"
            redirect(action: "listWithinMonth")
            return
        }
    }
}
