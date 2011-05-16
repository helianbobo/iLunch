package cn.ilunch.service

import cn.ilunch.domain.ProductOrder
import cn.ilunch.domain.Shipment
import cn.ilunch.domain.PointChange
import cn.ilunch.domain.OrderItem

import cn.ilunch.domain.Product
import cn.ilunch.domain.DistributionArea
import cn.ilunch.exception.EntityNotFoundException
import cn.ilunch.domain.ProductAreaPriceSchedule
import cn.ilunch.domain.MainDish

class OrderService {

    static transactional = true
    def grailsApplication
    def priceService

    def shrinkOrder(ProductOrder oldOrder) {
        oldOrder.status = ProductOrder.CANCELLED

        oldOrder.save()
        def today = new Date()
        today.clearTime()
        ProductOrder newOrder = new ProductOrder(orderDate: today, distributionPoint: oldOrder.distributionPoint, customer: oldOrder.customer, pointChange: oldOrder.pointChange)
        def amount = 0.0
        oldOrder.orderItems.each {oldItem ->
            OrderItem item = null;
            if (!(oldItem.product instanceof  MainDish)) {
                item = new OrderItem(shippmentDate:oldItem.shippmentDate, order: newOrder, product: oldItem.order, price: oldItem.price, quantity: oldItem.quantity)
            } else {
                def schedule = priceService.queryProductSchedule(oldItem.product, oldOrder.distributionPoint.area, oldItem.shippmentDate)[0]
                if (!schedule) {
                    throw new EntityNotFoundException([entity: ProductAreaPriceSchedule, product: product, area: distributionPoint.area, date: shippmentDate])
                }
                def quantity = schedule.remain >= oldItem.quantity ? oldItem.quantity : schedule.remain
                item = new OrderItem(shippmentDate:oldItem.shippmentDate,order: newOrder, product: oldItem.order, price: oldItem.price, quantity: quantity)
            }
            amount += (item.quantity * item.price)
            newOrder.addToOrderItems item
        }
        newOrder.amount = amount
        newOrder.status = ProductOrder.SUBMITTED
        newOrder.save()
        return newOrder
    }

    def acknowledgePayment(ProductOrder productOrder) {
        if (!productOrder.orderItems)
            throw new OrderStatusException("empty order cannot be acknowledged")

        if (!productOrder.acknowledgeable()) {
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

    def cancelOrder(ProductOrder productOrder) {
        switch (productOrder.status) {
            case ProductOrder.SUBMITTED:
                break;
            case ProductOrder.PAID:
                productOrder.customer.accountBalance += productOrder.amount
                break;

            default: throw new OrderStatusException("current order status: ${productOrder.status}, only orders with status set to SUBMITTED or PAID can be cancelled")
        }
        productOrder.status = ProductOrder.CANCELLED
        productOrder.save()
    }

    def completeOrder(ProductOrder productOrder) {
        switch (productOrder.status) {
            case ProductOrder.PAID:
                break;

            default: throw new OrderStatusException("current order status: ${productOrder.status}, only orders with status set to PAID can be completed")
        }

        def today = new Date()
        today.clearTime()
        productOrder.completeDate = today
        productOrder.status = ProductOrder.COMPLETED
        productOrder.save()
    }

    def createOrder(def orderDetails, customer, building, dateFormatString) {
        def pointChangePoint = orderDetails.pointChange
        def distributionPoint = building.distributionPoint
        def area = distributionPoint.area


        def amount = 0.0f

        ProductOrder productOrder = new ProductOrder(status: ProductOrder.SUBMITTED)
        def today = new Date()
        today.clearTime()
        productOrder.orderDate = today
        productOrder.distributionPoint = distributionPoint

        if (pointChangePoint) {
            PointChange pointChange = new PointChange(customer: customer, point: pointChangePoint, productOrder: productOrder)
            productOrder.pointChange = pointChange
        }

        orderDetails.orders.each {order ->
            def shippmentDate = Date.parse(dateFormatString, order.date)
            order.mainDishes.each {mainDish ->
                OrderItem orderItem = createOrderItem(mainDish, shippmentDate, productOrder, area)
                amount += orderItem.price
                productOrder.addToOrderItems orderItem
            }
            order.sideDishes.each {sideDish ->
                OrderItem orderItem = createOrderItem(sideDish, shippmentDate, productOrder, area)
                amount += orderItem.price
                productOrder.addToOrderItems orderItem
            }
        }
        productOrder.amount = amount//we need to take pointChangePoint into account afterwards
        productOrder.customer = customer
        productOrder.save()
    }

    private def createOrderItem(def productInfo, Date shippmentDate, ProductOrder productOrder, DistributionArea area) {
        def productId = productInfo.id
        def quantity = productInfo.quantity

        def product = Product.get(productId)
        if (!product) {
            throw new EntityNotFoundException([entity: Product, id: productId])
        }

        def schedule = priceService.queryProductSchedule(product, area, shippmentDate)[0]
        if (!schedule) {
            throw new EntityNotFoundException([entity: ProductAreaPriceSchedule, product: product, area: area, date: shippmentDate])
        }
        return new OrderItem(order: productOrder, product: product, price: schedule.price, quantity: quantity, shippmentDate: shippmentDate)

    }
}
