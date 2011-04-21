package cn.ilunch.domain

class ExceptionControllerTests extends JSONRenderControllerUnitTestCase {
    ExceptionController exceptionController

    protected void setUp() {
        super.setUp()
        exceptionController = new ExceptionController()
        exceptionController.metaClass.grailsApplication = [config: ['cn.ilunch.exception.code.EntityNotFound': "3", 'cn.ilunch.exception.code.ScheduleNotFound': "4"]]
        fixJsonRender exceptionController
    }


    void testEntityNotFound() {
        exceptionController.params.entityName = "Entity"
        exceptionController.params.id = "5"
        exceptionController.entityNotFound()
        assertEquals '{"error":{"message":"Entity Entity with id: 5 not found","errorCode":"3"}}', exceptionController.response.contentAsString

    }

    void testScheduleNotFound() {
        exceptionController.params.product = "Entity"
        exceptionController.params.area = "5"
        exceptionController.params.date = '2011-04-12'
        exceptionController.scheduleNotFound()
        assertEquals '{"error":{"message":"Price schedule not found of product Entity in area 5 on date 2011-04-12","errorCode":"4"}}', exceptionController.response.contentAsString
    }
}
