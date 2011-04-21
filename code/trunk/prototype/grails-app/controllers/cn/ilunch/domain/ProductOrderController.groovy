package cn.ilunch.domain

class ProductOrderController {
    def priceService

    def confirm = {
        def orderDetailsJSON = request.getJSON()
        def orderDetails  = grails.converters.JSON.parse(orderDetailsJSON)

        def userId = orderDetails.id
        def buildingId = orderDetails.buildingId
        def pointChangePoint = orderDetails.pointChange

        def customer = Customer.get(userId)
        def building = Building.get(buildingId)
        def distributionPoint = building.distributionPoint
        def area = distributionPoint.area
        def dateFormatString = grailsApplication.config.cn.ilunch.date.format

        def amount = 0.0f

        ProductOrder productOrder = new ProductOrder(status: ProductOrder.SUBMITTED)
        def today = new Date()
        today.clearTime()
        productOrder.orderDate = today
        productOrder.distributionPoint = distributionPoint

        if(pointChangePoint){
            PointChange pointChange = new PointChange(customer:customer,point: pointChangePoint, productOrder:productOrder)
            productOrder.pointChange = pointChange
        }
        try {
            orderDetails.orders.each {order ->
                def shippmentDate = Date.parse(dateFormatString, order.date)
                order.mainDishes.each {mainDish->
                    OrderItem orderItem = createOrderItem(mainDish, shippmentDate, productOrder, area)
                    amount += orderItem.price
                    productOrder.addToOrderItems orderItem
                }
                order.sideDishes.each {sideDish->
                    OrderItem orderItem = createOrderItem(sideDish, shippmentDate, productOrder, area)
                    amount += orderItem.price
                    productOrder.addToOrderItems orderItem
                }
            }
            productOrder.amount = amount
            productOrder.customer = customer
            productOrder.save()
        }catch(e){
           return
        }
    }

    private def createOrderItem(def productInfo, Date shippmentDate, ProductOrder productOrder, DistributionArea area) {
        def productId = productInfo.id
        def quantity = productInfo.quantity

        def product = Product.get(productId)
        if (!product) {
            forward(controller: "exception", action: "entityNotFound", params: [id: productId, entityName: Product])
            throw new RuntimeException("break")
        }

        def schedule = priceService.queryProductSchedule(product, area, shippmentDate)[0]
        if (!schedule) {
            forward(controller: "exception", action: "scheduleNotFound")
            throw new RuntimeException("break")
        }
        new OrderItem(order: productOrder, product: product, price: schedule.price, quantity: quantity, shippmentDate:shippmentDate)

    }
}
