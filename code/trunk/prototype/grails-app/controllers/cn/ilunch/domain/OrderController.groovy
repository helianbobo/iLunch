package cn.ilunch.domain

import cn.ilunch.security.ILunchUserDetails
import org.springframework.web.servlet.ModelAndView
import cn.ilunch.service.NotEnoughProductException

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
            //eq('status', Shipment.CREATED)
            if (orderList)
                inList('productOrder', orderList)
            maxResults(params.max)
            order("shipmentDate", "desc")
        }

        render(view: "list", model: [shipments: shipmentList, orders: orderList,optionOrder:'listWithinMonth',optionShipment:params.optionShipment])
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
        render(view: "list", model: [shipments: shipments, orders: orderList,optionOrder:'listPending',optionShipment:params.optionShipment])
    }

    def listCompleted = {
        Customer customer = springSecurityService.currentUser
        def cellNumber = customer.cellNumber

        params.max = Math.min(params.max ? params.int('max') : 10, 100)

        def c = ProductOrder.createCriteria()
        def orderList = c.list {
            inList('status', [ProductOrder.COMPLETED,ProductOrder.CANCELLED])

            eq('customer', customer)
            maxResults(params.max)
            order("orderDate", "desc")
        }
        def shipments = []
        orderList.each {
            shipments.addAll(it.shipments)
        }
        render(view: "list", model: [shipments: shipments, orders: orderList,optionOrder:'listCompleted',optionShipment:params.optionShipment])
    }


    def cancel = {
        def orderId = params.id
        def productOrder = ProductOrder.get(orderId)

        try {
            orderService.cancelOrder(productOrder)
            flash.message = "已取消订单"
            redirect(action: "listWithinMonth")
            return
        } catch (e) {
            flash.message = "取消订单失败"
            redirect(action: "listWithinMonth")
            return
        }
        redirect(action: "listWithinMonth")
    }

    def tryAcknowledge = {
        def orderId = params.id
        def productOrder = ProductOrder.get(orderId)
        try {
            orderService.tryReduce(productOrder)
        } catch (NotEnoughProductException e) {
            flash.message = "没货了，系统自动为您修改数量并新生成订单，请确认"
            try {
                orderService.shrinkOrder(productOrder)
                redirect(action: "listWithinMonth")
                return
            } catch (e1) {
                flash.message = "付款失败"
                redirect(action: "listWithinMonth")
                return
            }
        }
        flash.message = "请在支付宝付款，付款完成后请返回这里确认配送生成"
        redirect(action: "listWithinMonth")
    }

    def acknowledge = {
        def orderId = params.id
        def productOrder = ProductOrder.get(orderId)
        try {
            orderService.acknowledgePayment(productOrder)
        } catch (NotEnoughProductException e) {
            flash.message = "付款失败，请联系客服"
            redirect(action: "listWithinMonth")
        }
        flash.message = "配送生成成功"
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
        if (shipment.status != Shipment.CREATED) {
            flash.message = "已经被取消"
            redirect(action: "listWithinMonth")
            return
        }
        try {
            orderService.cancelShipment(shipment)
            flash.message = "已将付款返还到账户余额"
            redirect(action: "listWithinMonth")
            return
        } catch (e) {
            flash.message = "取消配送失败"
            redirect(action: "listWithinMonth")
            return
        }

    }
}
