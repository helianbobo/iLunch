package cn.ilunch.domain

class ExceptionController {
    def grailsApplication

    def entityNotFound = {
        render(contentType: "text/json") {
            error(message: "Entity ${params.entityName} with id: ${params.id} not found", errorCode: grailsApplication.config."cn.ilunch.exception.code.EntityNotFound")
        }
    }
    def scheduleNotFound = {
        render(contentType: "text/json") {
            error(message: "Price schedule not found of product ${params.product} in area ${params.area} on date ${params.date}", errorCode: grailsApplication.config."cn.ilunch.exception.code.ScheduleNotFound")
        }
    }
}