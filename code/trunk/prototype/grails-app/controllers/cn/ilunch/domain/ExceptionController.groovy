package cn.ilunch.domain

class ExceptionController {
    def grailsApplication

    def entityNotFound = {
        render(contentType: "text/json") {
            error(message: "Entity ${params.entityName} with id: ${params.id} not found", errorCode: grailsApplication.config.cn.ilunch.exception.code.EntityNotFound.toString())
        }
    }
    def scheduleNotFound = {
        render(contentType: "text/json") {
            error(message: "Price schedule not found of product ${params.product} in area ${params.area} on date ${params.date}", errorCode: grailsApplication.config.cn.ilunch.exception.code.ScheduleNotFound.toString())
        }
    }
def cartNotFound = {
        render(contentType: "text/json") {
            error(message: "cart not found", errorCode: grailsApplication.config.cn.ilunch.exception.code.CartNotFound.toString())
        }
    }

    def notEnoughPointChange = {
        render(contentType: "text/json") {
            error(message: "not enough point change", errorCode: grailsApplication.config.cn.ilunch.exception.code.NotEnoughPointChange.toString())
        }
    }

    def deprecatedOrder = {
        render(contentType: "text/json") {
            error(message: "The dish you ordered must be served at least 2 days later", errorCode: grailsApplication.config.cn.ilunch.exception.code.DeprecatedOrder.toString())
        }
    }
}