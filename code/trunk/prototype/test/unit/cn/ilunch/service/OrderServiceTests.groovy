package cn.ilunch.service

import grails.test.*
import cn.ilunch.domain.ProductOrder
import cn.ilunch.domain.Customer
import cn.ilunch.domain.Building
import cn.ilunch.domain.DistributionPoint
import cn.ilunch.domain.DistributionArea
import cn.ilunch.domain.OrderItem
import cn.ilunch.domain.Product
import cn.ilunch.domain.Shipment

class OrderServiceTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
        service = new OrderService()
    }

    OrderService service

    void testAcknowledgePayment() {
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
        assertEquals(order, shipment1.order)
        assertTrue(shipment1.orderItems.contains(item1))
        assertTrue(shipment1.orderItems.contains(item3))
        assertTrue(shipment1.orderItems.contains(item4))

        Shipment shipment2 = order.shipments.find {shipment ->
            shipment.shipmentDate == today + 1
        }
        assertEquals(today + 1, shipment2.shipmentDate)
        assertEquals(Shipment.CREATED, shipment2.status)
        assertEquals(order, shipment2.order)
        assertTrue(shipment2.orderItems.contains(item2))
        assertTrue(shipment2.orderItems.contains(item5))
        assertTrue(shipment2.orderItems.contains(item6))
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
        try{
            service.acknowledgePayment order
        }catch(OrderStatusException e){
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
        try{
            service.acknowledgePayment order
        }catch(OrderStatusException e){
            return;
        }

        fail("empty order cannot be acknowledged");
    }

    public void testCancelIncancellableOrders(){
        ProductOrder order = new ProductOrder()
        order.amount = 10.0f
        order.status = ProductOrder.COMPLETED

        try{
            service.cancelOrder order
        }catch(OrderStatusException e){
            return;
        }

        fail("completed order cannot be cancelled");
    }

    public void testCancelSubmittedOrders(){
        mockDomain(ProductOrder,[])
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

    public void testCancelPaidOrders(){
        mockDomain(ProductOrder,[])
        Customer customer = new Customer()
        customer.cellNumber = "12345678901"
        customer.name = "liuchao"
        customer.pointBalance = 0.0
        customer.accountBalance = 0.0
        mockDomain(Customer, [customer])

        ProductOrder order = new ProductOrder()
        order.amount = 10.0f
        order.status = ProductOrder.PAID
        order.customer = customer

        service.cancelOrder order

        assertEquals(ProductOrder.CANCELLED, order.status)
        assertEquals(10.0f, customer.accountBalance)
    }

    public void testCompleteCancelledOrder(){
        ProductOrder order = new ProductOrder()
        order.amount = 10.0f
        order.status = ProductOrder.CANCELLED

        try{
            service.completeOrder order
        }catch(OrderStatusException e){
            return;
        }
        fail("cancelled order cannot be completed");
    }

    public void testCompleteOrder(){
        mockDomain(ProductOrder,[])
        ProductOrder order = new ProductOrder()
        order.amount = 10.0f
        order.status = ProductOrder.PAID

        service.completeOrder order

        def today = new Date()
        today.clearTime()
        assertEquals(today, order.completeDate)
        assertEquals(ProductOrder.COMPLETED, order.status)
    }
}
