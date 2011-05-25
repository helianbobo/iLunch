package cn.ilunch.service

import grails.test.*
import cn.ilunch.domain.Customer
import cn.ilunch.domain.Building
import cn.ilunch.domain.DistributionPoint
import cn.ilunch.domain.DistributionArea
import cn.ilunch.domain.OrderItem
import cn.ilunch.domain.Product
import cn.ilunch.domain.Shipment
import grails.web.JSONBuilder
import cn.ilunch.domain.ProductAreaPriceSchedule
import cn.ilunch.domain.SideDish
import cn.ilunch.domain.MainDish
import cn.ilunch.domain.ProductOrder

class OrderServiceTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
        service = new OrderService()

    }

    OrderService service

    void testAcknowledgePayment() {
        def serialNumberService = mockFor(cn.ilunch.service.SerialNumberService)
        serialNumberService.demand.getCode(2..2) {id, date ->
            return "code12345"
        }
        service.serialNumberService = serialNumberService.createMock()

        Customer customer = new Customer()
        customer.cellNumber = "12345678901"
        customer.name = "liuchao"
        customer.pointBalance = 123
        mockDomain(Customer, [customer])

        Building building = new Building()
        building.name = "qq"
        building.latitude = 123.31
        building.longitude = 12.31


        customer.primaryBuilding = building

        DistributionPoint dp = new DistributionPoint()
        dp.name = "dp1"

        building.distributionPoint = dp

        DistributionArea area = new DistributionArea()
        area.name = "zhangjiang"
        area.longitude = 23.12
        area.latitude = 123.23
        dp.area = area

        ProductOrder order = new ProductOrder()
        order.amount = 10.0f
        order.customer = customer
        order.distributionPoint = dp
        order.status = ProductOrder.SUBMITTED

        mockDomain(ProductOrder, [order])

        final sd1 = new Product(id: 4, name: "dishName3", story: "story3", detailImageUrl: "detailImageUrl3")
        final sd2 = new Product(id: 3, name: "dishName2", story: "story2", detailImageUrl: "detailImageUrl2")
        final md1 = new Product(id: 2, name: "dishName4", story: "story4", detailImageUrl: "detailImageUrl4")
        final md2 = new Product(id: 1, name: "dishName1", story: "story1", detailImageUrl: "detailImageUrl1")
        mockDomain(Product, [sd1, sd2, md1, md2])

        def today = new Date()
        today.clearTime()

        OrderItem item1 = new OrderItem()
        item1.order = order
        item1.price = 10.0f
        item1.product = md1
        item1.quantity = 5
        item1.shippmentDate = today

        OrderItem item3 = new OrderItem()
        item3.order = order
        item3.price = 10.0f
        item3.product = sd1
        item3.quantity = 1
        item3.shippmentDate = today

        OrderItem item4 = new OrderItem()
        item4.order = order
        item4.price = 10.0f
        item4.product = sd2
        item4.quantity = 1
        item4.shippmentDate = today

        OrderItem item2 = new OrderItem()
        item2.order = order
        item2.price = 20.0f
        item2.product = md2
        item2.quantity = 1
        item2.shippmentDate = today + 1

        OrderItem item5 = new OrderItem()
        item5.order = order
        item5.price = 10.0f
        item5.product = sd1
        item5.quantity = 1
        item5.shippmentDate = today + 1

        OrderItem item6 = new OrderItem()
        item6.order = order
        item6.price = 10.0f
        item6.product = sd2
        item6.quantity = 1
        item6.shippmentDate = today + 1

        final orderItems = [item1, item2, item3, item4, item5, item6]
        orderItems.each {item ->
            order.addToOrderItems item
        }

        mockDomain(OrderItem, orderItems)
        mockDomain(Shipment, [])
        service.acknowledgePayment(order)

        assertEquals(ProductOrder.PAID, order.status)
        assertEquals(2, order.shipments.size())

        Shipment shipment1 = order.shipments.find {shipment ->
            shipment.shipmentDate == today
        }
        assertEquals(today, shipment1.shipmentDate)
        assertEquals(Shipment.CREATED, shipment1.status)
        assertEquals(order, shipment1.productOrder)
        assertTrue(shipment1.orderItems.contains(item1))
        assertTrue(shipment1.orderItems.contains(item3))
        assertTrue(shipment1.orderItems.contains(item4))
        assertEquals("code12345", shipment1.serialNumber)

        Shipment shipment2 = order.shipments.find {shipment ->
            shipment.shipmentDate == today + 1
        }
        assertEquals(today + 1, shipment2.shipmentDate)
        assertEquals(Shipment.CREATED, shipment2.status)
        assertEquals(order, shipment2.productOrder)
        assertTrue(shipment2.orderItems.contains(item2))
        assertTrue(shipment2.orderItems.contains(item5))
        assertTrue(shipment2.orderItems.contains(item6))
        assertEquals("code12345", shipment2.serialNumber)
    }

    public void testUnacknowledgeability() {
        ProductOrder order = new ProductOrder()
        order.amount = 10.0f
        order.status = ProductOrder.COMPLETED

        OrderItem item = new OrderItem()
        item.order = order
        item.price = 10.0f
        item.quantity = 1

        final orderItems = [item]

        order.orderItems = orderItems
        try {
            service.acknowledgePayment order
        } catch (OrderStatusException e) {
            return;
        }

        fail("COMPLETED order cannot be acknowledged");
    }

    public void testAcknoledgeEmptyOrder() {
        ProductOrder order = new ProductOrder()
        order.amount = 10.0f
        order.status = ProductOrder.SUBMITTED

        final orderItems = []

        order.orderItems = orderItems
        try {
            service.acknowledgePayment order
        } catch (OrderStatusException e) {
            return;
        }

        fail("empty order cannot be acknowledged");
    }

    public void testCancelIncancellableOrders() {
        ProductOrder order = new ProductOrder()
        order.amount = 10.0f
        order.status = ProductOrder.COMPLETED

        try {
            service.cancelOrder order
        } catch (OrderStatusException e) {
            return;
        }

        fail("completed order cannot be cancelled");
    }

    public void testCancelSubmittedOrders() {
        mockDomain(ProductOrder, [])
        Customer customer = new Customer()
        customer.cellNumber = "12345678901"
        customer.name = "liuchao"
        customer.pointBalance = 0.0
        customer.accountBalance = 0.0
        mockDomain(Customer, [customer])

        ProductOrder order = new ProductOrder()
        order.customer = customer
        order.amount = 10.0f
        order.status = ProductOrder.SUBMITTED

        service.cancelOrder order
        assertEquals(ProductOrder.CANCELLED, order.status)
        assertEquals(0.0, customer.accountBalance)
    }

    public void testCancelPaidOrders() {
        mockDomain(ProductOrder, [])
        Customer customer = new Customer()
        customer.cellNumber = "12345678901"
        customer.name = "liuchao"
        customer.pointBalance = 0.0
        customer.accountBalance = 0.0
        mockDomain(Customer, [customer])

        ProductOrder productOrder = new ProductOrder()
        productOrder.amount = 35.0f
        productOrder.status = ProductOrder.PAID
        productOrder.customer = customer

        def orderItems = []
        orderItems << new OrderItem(price: 15)
        orderItems << new OrderItem(price: 8)
        orderItems << new OrderItem(price: 12)
        final shipment1 = new Shipment(productOrder: productOrder, orderItems: orderItems, status: Shipment.CREATED)
        final shipment2 = new Shipment(productOrder: productOrder, orderItems: orderItems, status: Shipment.SHIPPED)
        productOrder.addToShipments shipment1
        productOrder.addToShipments shipment2

        service.cancelOrder productOrder

        assertEquals(ProductOrder.CANCELLED, productOrder.status)
        assertEquals(35.0f, customer.accountBalance)
    }

    public void testCompleteCancelledOrder() {
        ProductOrder order = new ProductOrder()
        order.amount = 10.0f
        order.status = ProductOrder.CANCELLED

        try {
            service.completeOrder order
        } catch (OrderStatusException e) {
            return;
        }
        fail("cancelled order cannot be completed");
    }

    public void testCompleteOrder() {
        mockDomain(ProductOrder, [])
        ProductOrder order = new ProductOrder()
        order.amount = 10.0f
        order.status = ProductOrder.PAID

        service.completeOrder order

        def today = new Date()
        today.clearTime()
        assertEquals(today, order.completeDate)
        assertEquals(ProductOrder.COMPLETED, order.status)
    }

    public void testCreateOrder() {
        Customer customer = new Customer()
        customer.cellNumber = "12345678901"
        customer.name = "liuchao"
        customer.pointBalance = 123
        mockDomain(Customer, [customer])

        Building building = new Building()
        building.name = "qq"
        building.latitude = 123.31
        building.longitude = 12.31

        customer.primaryBuilding = building

        DistributionPoint dp = new DistributionPoint()
        dp.name = "dp1"

        building.distributionPoint = dp

        DistributionArea area = new DistributionArea()
        area.name = "zhangjiang"
        area.longitude = 23.12
        area.latitude = 123.23
        dp.area = area

        mockDomain(Building, [building])
        mockDomain(DistributionPoint, [dp])
        mockDomain(DistributionArea, [area])
        mockDomain(ProductOrder, [])
        mockDomain(OrderItem, [])

        def dishName = "chicken"
        def story = "chicken story"
        final detailImageUrl = "http://www.google.com"

        final sd1 = new Product(id: 4, name: "dishName3", story: "story3", detailImageUrl: "detailImageUrl3")
        final sd2 = new Product(id: 3, name: "dishName2", story: "story2", detailImageUrl: "detailImageUrl2")
        final md1 = new Product(id: 2, name: "dishName4", story: "story4", detailImageUrl: "detailImageUrl4")
        final md2 = new Product(id: 1, name: dishName, story: story, detailImageUrl: detailImageUrl)
        mockDomain(Product, [sd1, sd2, md1, md2])
        def priceService = mockFor(cn.ilunch.service.PriceService)

        def today
        priceService.demand.queryProductSchedule(4) {mainDish, area1, date ->
            today = Date.parse('yyyy-MM-dd', '2011-04-14')
            today.clearTime()
            [new ProductAreaPriceSchedule(price: 5, fromDate: today, toDate: today + 2)]
        }
        service.priceService = priceService.createMock()
        JSONBuilder builder = new JSONBuilder()
        def json = builder.build {
            id = 1
            buildingId = 1
            pointChange = 10
            orders = array {
                order([
                        date: (new Date() + 3).format("yyyy-MM-dd"),
                        mainDishes: array {
                            mainDish([id: 1, quantity: 5])
                            mainDish([id: 2, quantity: 4])
                        },
                        sideDishes: array {
                            sideDish([id: 3, quantity: 5])
                            sideDish([id: 4, quantity: 2])
                        }
                ])
            }
        }
        def orderDetails = grails.converters.JSON.parse(json.toString())
        def orderCreated = service.createOrder(orderDetails, customer, building, 'yyyy-MM-dd')

        assertEquals(20, orderCreated.amount)
        assertEquals(customer, orderCreated.customer)
        assertEquals(dp, orderCreated.distributionPoint)

        def orderDate = new Date()
        orderDate.clearTime()
        assertEquals(orderDate, orderCreated.orderDate)
        assertEquals(10, orderCreated.pointChange.point)
        assertEquals(customer, orderCreated.pointChange.customer)
        assertEquals(orderCreated, orderCreated.pointChange.productOrder)

        assertEquals(ProductOrder.SUBMITTED, orderCreated.status)
        assertEquals(4, orderCreated.orderItems.size())
        assertTrue(orderCreated.orderItems*.product.contains(sd1))
        assertTrue(orderCreated.orderItems*.product.contains(sd2))
        assertTrue(orderCreated.orderItems*.product.contains(md1))
        assertTrue(orderCreated.orderItems*.product.contains(md2))

        def shipmentDate = new Date()
        shipmentDate.clearTime()

        def mdOrder2 = orderCreated.orderItems.find {
            it.product.equals(md2)
        }
        assertEquals(5, mdOrder2.quantity)
        assertEquals(shipmentDate + 3, mdOrder2.shippmentDate)

        def mdOrder1 = orderCreated.orderItems.find {
            it.product.equals(md1)
        }
        assertEquals(shipmentDate + 3, mdOrder1.shippmentDate)

        final sdOrder2 = orderCreated.orderItems.find {
            it.product.equals(sd2)
        }
        assertEquals(5, sdOrder2.quantity)
        assertEquals(shipmentDate + 3, sdOrder2.shippmentDate)

        final sdOrder1 = orderCreated.orderItems.find {
            it.product.equals(sd1)
        }
        assertEquals(2, sdOrder1.quantity)
        assertEquals(shipmentDate + 3, sdOrder1.shippmentDate)
    }

    public void testShrinkOrder() {
        Customer customer = new Customer()
        customer.cellNumber = "12345678901"
        customer.name = "liuchao"
        customer.pointBalance = 123
        mockDomain(Customer, [customer])

        Building building = new Building()
        building.name = "qq"
        building.latitude = 123.31
        building.longitude = 12.31

        customer.primaryBuilding = building

        DistributionPoint dp = new DistributionPoint()
        dp.name = "dp1"

        building.distributionPoint = dp

        DistributionArea area = new DistributionArea()
        area.name = "zhangjiang"
        area.longitude = 23.12
        area.latitude = 123.23
        dp.area = area

        mockDomain(Building, [building])
        mockDomain(DistributionPoint, [dp])
        mockDomain(DistributionArea, [area])
        mockDomain(ProductOrder, [])
        mockDomain(OrderItem, [])

        def dishName = "chicken"
        def story = "chicken story"
        final detailImageUrl = "http://www.google.com"

        final sd1 = new SideDish(id: 4, name: "dishName3", story: "story3", detailImageUrl: "detailImageUrl3")
        final sd2 = new SideDish(id: 3, name: "dishName2", story: "story2", detailImageUrl: "detailImageUrl2")
        final md1 = new MainDish(id: 2, name: "dishName4", story: "story4", detailImageUrl: "detailImageUrl4")

        def priceService = mockFor(cn.ilunch.service.PriceService)

        def today = new Date()
        priceService.demand.queryProductSchedule(2) {mainDish, area1, date ->
            [new ProductAreaPriceSchedule(remain: 5, quantity: 10, price: 5, fromDate: today, toDate: today + 2)]
        }
        service.priceService = priceService.createMock()

        ProductOrder oldOrder = new ProductOrder(distributionPoint: area, customer: customer, orderDate: today, pointChange: 10, amount: 150)
        OrderItem item = new OrderItem(order: oldOrder, product: md1, quantity: 10, price: 10, shippmentDate: today)
        OrderItem item2 = new OrderItem(order: oldOrder, product: sd1, quantity: 3, price: 4, shippmentDate: today)
        OrderItem item3 = new OrderItem(order: oldOrder, product: sd2, quantity: 5, price: 3, shippmentDate: today)
        OrderItem item4 = new OrderItem(order: oldOrder, product: md1, quantity: 8, price: 15, shippmentDate: today + 2)

        [item, item4, item2, item3].each {
            oldOrder.addToOrderItems it
        }

        def newOrder = service.shrinkOrder(oldOrder)
        assertEquals(oldOrder.customer, newOrder.customer)
        assertEquals(oldOrder.distributionPoint, newOrder.distributionPoint)
        assertEquals(ProductOrder.CANCELLED, oldOrder.status)
        assertEquals(ProductOrder.SUBMITTED, newOrder.status)

        assertEquals(oldOrder.orderItems[0].quantity - 5, newOrder.orderItems[0].quantity)
        assertEquals(oldOrder.orderItems[0].price, newOrder.orderItems[0].price)
        assertEquals(today, newOrder.orderItems[0].shippmentDate)
        assertEquals(newOrder, newOrder.orderItems[0].order)

        assertEquals(newOrder.orderItems[0].price * newOrder.orderItems[0].quantity, oldOrder.orderItems[0].price * (oldOrder.orderItems[0].quantity - 5))

        assertEquals(newOrder.orderItems[1].quantity, 5)
        assertEquals(oldOrder.orderItems[1].price, newOrder.orderItems[1].price)
        assertEquals(today + 2, newOrder.orderItems[1].shippmentDate)
        assertEquals(newOrder, newOrder.orderItems[1].order)

        assertEquals(newOrder.orderItems[1].price * newOrder.orderItems[1].quantity, oldOrder.orderItems[1].price * 5)

        assertEquals(5, newOrder.orderItems[3].quantity)
        assertEquals(oldOrder.orderItems[3].price, newOrder.orderItems[3].price)
        assertEquals(today, newOrder.orderItems[3].shippmentDate)
        assertEquals(newOrder, newOrder.orderItems[3].order)

        assertEquals(152, newOrder.amount)

    }

    void testCancelShipment() {
        final customer = new Customer(accountBalance: 0)
        mockDomain(Customer, [customer])
        final ProductOrder productOrder = new ProductOrder(customer: customer, status: ProductOrder.PAID)
        def orderItems = []
        orderItems << new OrderItem(price: 15)
        orderItems << new OrderItem(price: 8)
        orderItems << new OrderItem(price: 12)
        final shipment = new Shipment(productOrder: productOrder, orderItems: orderItems)
        mockDomain(Shipment, [shipment])

        service.cancelShipment(shipment);
        assertEquals(35, customer.accountBalance)
        assertEquals("CANCELLED", shipment.status)
    }
}
