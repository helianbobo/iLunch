package cn.ilunch.domain

import grails.web.JSONBuilder
import grails.converters.JSON
import cn.ilunch.exception.EntityNotFoundException

class ProductOrderControllerTests extends JSONRenderControllerUnitTestCase {
    JSON json
    protected void setUp() {
        super.setUp()
        def configObject = new ConfigObject()
        configObject.cn.ilunch.date.format = "yyyy-MM-dd"
        controller.metaClass.grailsApplication = [config: configObject]
        fixJsonRender controller

        JSONBuilder builder = new JSONBuilder()
        json = builder.build {
            id = 1
            buildingId = 1
            pointChange = 10
            orders = array {
                order([
                        date: new Date().format("yyyy-MM-dd"),
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
         controller.request.metaClass.getJSON = {
            json.toString()
        }
    }

//    void testConfirm() {
//        Customer customer = new Customer()
//        customer.cellNumber = "12345678901"
//        customer.name = "liuchao"
//        customer.pointBalance = 123
//        mockDomain(Customer, [customer])
//
//        Building building = new Building()
//        building.name = "qq"
//        building.latitude = 123.31
//        building.longitude = 12.31
//
//        customer.primaryBuilding = building
//
//        DistributionPoint dp = new DistributionPoint()
//        dp.name = "dp1"
//
//        building.distributionPoint = dp
//
//        DistributionArea area = new DistributionArea()
//        area.name = "zhangjiang"
//        area.longitude = 23.12
//        area.latitude = 123.23
//        dp.area = area
//
//        mockDomain(Building, [building])
//        mockDomain(DistributionPoint, [dp])
//        mockDomain(DistributionArea, [area])
//        mockDomain(ProductOrder, [])
//        mockDomain(OrderItem, [])
//
//        def dishName = "chicken"
//        def story = "chicken story"
//        final detailImageUrl = "http://www.google.com"
//
//
//        final sd1 = new Product(id: 4, name: "dishName3", story: "story3", detailImageUrl: "detailImageUrl3")
//        final sd2 = new Product(id: 3, name: "dishName2", story: "story2", detailImageUrl: "detailImageUrl2")
//        final md1 = new Product(id: 2, name: "dishName4", story: "story4", detailImageUrl: "detailImageUrl4")
//        final md2 = new Product(id: 1, name: dishName, story: story, detailImageUrl: detailImageUrl)
//        mockDomain(Product, [sd1, sd2,md1,md2])
////        mockDomain(SideDish, [sd1,sd2])
////        mockDomain(MainDish, [md1,md2])
//
//
//        def priceService = mockFor(cn.ilunch.service.PriceService)
//
//        def today
//        priceService.demand.queryProductSchedule(4) {mainDish, area1, date ->
//            today = Date.parse('yyyy-MM-dd', '2011-04-14')
//            today.clearTime()
//            [new ProductAreaPriceSchedule(price: 5, fromDate: today, toDate: today + 2)]
//        }
//        controller.priceService = priceService.createMock()
//
//
//        JSONBuilder builder = new JSONBuilder()
//        JSON json = builder.build {
//            id = customer.id
//            buildingId = building.id
//            pointChange = 10
//            orders = array {
//                order([
//                        date: new Date().format("yyyy-MM-dd"),
//                        mainDishes : array{
//                            mainDish([id:1,quantity:5])
//                            mainDish([id:2,quantity:4])
//                        },
//                        sideDishes : array{
//                            sideDish([id:3,quantity:5])
//                            sideDish([id:4,quantity:2])
//                        }
//                ])
//            }
//        }
//        controller.request.metaClass.getJSON = {
//            json.toString()
//        }
//        controller.confirm()
//        ProductOrder orderCreated = ProductOrder.findAll()[0]
//        assertEquals(20, orderCreated.amount)
//        assertEquals(customer, orderCreated.customer)
//        assertEquals(dp, orderCreated.distributionPoint)
//
//        def orderDate = new Date()
//        orderDate.clearTime()
//        assertEquals(orderDate, orderCreated.orderDate)
//        assertEquals(10, orderCreated.pointChange.point)
//        assertEquals(customer, orderCreated.pointChange.customer)
//        assertEquals(orderCreated, orderCreated.pointChange.productOrder)
//
//        assertEquals(ProductOrder.SUBMITTED, orderCreated.status)
//        assertEquals(4, orderCreated.orderItems.size())
//        assertTrue(orderCreated.orderItems*.product.contains(sd1))
//        assertTrue(orderCreated.orderItems*.product.contains(sd2))
//        assertTrue(orderCreated.orderItems*.product.contains(md1))
//        assertTrue(orderCreated.orderItems*.product.contains(md2))
//
//        def shipmentDate = new Date()
//        shipmentDate.clearTime()
//
//        def mdOrder2 = orderCreated.orderItems.find {
//            it.product.equals(md2)
//        }
//        assertEquals(5, mdOrder2.quantity)
//        assertEquals(shipmentDate, mdOrder2.shippmentDate)
//
//        def mdOrder1 = orderCreated.orderItems.find {
//            it.product.equals(md1)
//        }
//        assertEquals(shipmentDate, mdOrder1.shippmentDate)
//
//        final sdOrder2 = orderCreated.orderItems.find {
//            it.product.equals(sd2)
//        }
//        assertEquals(5, sdOrder2.quantity)
//        assertEquals(shipmentDate, sdOrder2.shippmentDate)
//
//        final sdOrder1 = orderCreated.orderItems.find {
//            it.product.equals(sd1)
//        }
//        assertEquals(2, sdOrder1.quantity)
//        assertEquals(shipmentDate, sdOrder1.shippmentDate)
//    }

    void testConfirm() {

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

        mockDomain(Building, [building])

        def orderService = mockFor(cn.ilunch.service.OrderService)

        orderService.demand.createOrder(1) {orderDetails, c, b,format ->
            new ProductOrder(id:1)
        }

        controller.orderService = orderService.createMock()

        controller.confirm()
        assertEquals('{"orderId":1,"result":"success"}',controller.response.contentAsString)
    }

    void testConfirmEntityNotFound() {
        mockDomain(Customer, [])
        mockDomain(Building, [])


        controller.confirm()

        assertEquals('exception', controller.forwardArgs.controller)
        assertEquals('entityNotFound', controller.forwardArgs.action)
        assertEquals(1, controller.forwardArgs.params.id)
    }

    void testConfirmServiceException() {

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

        mockDomain(Building, [building])
        JSONBuilder builder = new JSONBuilder()
        JSON json = builder.build {
            id = customer.id
            buildingId = building.id
            pointChange = 10
            orders = array {
                order([
                        date: new Date().format("yyyy-MM-dd"),
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
        def orderService = mockFor(cn.ilunch.service.OrderService)

        orderService.demand.createOrder(1) {orderDetails, c, b,format ->
            throw new EntityNotFoundException([a:'b'])
        }

        controller.orderService = orderService.createMock()

        controller.confirm()

        assertEquals('exception', controller.forwardArgs.controller)
        assertEquals('entityNotFound', controller.forwardArgs.action)
        assertEquals('b', controller.forwardArgs.params.a)
    }
}
