package cn.ilunch.domain

import grails.test.*

class OrderControllerTests extends JSONRenderControllerUnitTestCase {
    void testCancel() {
        mockDomain(ProductOrder, [new ProductOrder(id: 1)])
        controller.params.id = 1

        def orderService = mockFor(cn.ilunch.service.OrderService)

        def today
        orderService.demand.cancelOrder(1..1) {order ->
            assertEquals(1, order.id)
        }

        controller.orderService = orderService.createMock()

        controller.cancel()
        assertEquals("list", controller.redirectArgs["action"])
    }
}
