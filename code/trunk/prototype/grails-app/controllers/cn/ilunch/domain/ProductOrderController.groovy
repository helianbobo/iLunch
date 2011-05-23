package cn.ilunch.domain
import cn.ilunch.exception.EntityNotFoundException
import cn.ilunch.service.DeprecatedOrderException

class ProductOrderController {

    def orderService

    def confirm = {
        def orderDetailsJSON = request.getJSON()
        def orderDetails = orderDetailsJSON

        def userId = orderDetails.id
        def buildingId = orderDetails.buildingId

        def customer = Customer.get(userId)
        def building = Building.get(buildingId)

        if (!customer) {
            forward(controller: "exception", action: "entityNotFound", params: [id: userId, entityName: Customer])
            return;
        }

        if (!building) {
            forward(controller: "exception", action: "entityNotFound", params: [id: buildingId, entityName: Building])
            return;
        }
        def pointChangePoint = orderDetails.pointChange as float
        if (pointChangePoint > (customer.pointBalance as float)) {
            forward(controller: "exception", action: "notEnoughPointChange", params: [id: userId])
            return;
        }

        def dateFormatString = grailsApplication.config.cn.ilunch.date.format

        try {
            def productOrder = orderService.createOrder(orderDetails, customer, building, dateFormatString)
            render(contentType: "text/json") {
                orderId = productOrder.id
                result = "success"
            }
        } catch (EntityNotFoundException e) {
            forward(controller: "exception", action: "entityNotFound", params: e.exceptionInfoMap)
        }
        catch (DeprecatedOrderException e) {
            forward(controller: "exception", action: "deprecatedOrder", params: [:])
        }
    }
}
