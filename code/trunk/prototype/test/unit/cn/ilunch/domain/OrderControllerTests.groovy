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
        assertEquals("listWithinMonth", controller.redirectArgs["action"])
    }

    void testSendSN(){
        def serialNumberService = mockFor(cn.ilunch.service.SerialNumberService)
        serialNumberService.demand.send(3..3) {sn->
            assertEquals('12345', sn)
        }
        controller.serialNumberService = serialNumberService.createMock()

        final shipment = new Shipment(id:1, sentCounter: 0,serialNumber: '12345')
        mockDomain(Shipment, [shipment])

        controller.params.shipmentId = 1
        controller.sendSN()
        controller.sendSN()
        controller.sendSN()
        assertEquals(3, shipment.sentCounter)
        assertEquals("发送成功", controller.flash.message)

        controller.sendSN()
        assertEquals(3, shipment.sentCounter)
        assertEquals("发送次数过多，请手动记录", controller.flash.message)
    }
}
