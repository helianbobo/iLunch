package cn.ilunch.domain

import java.text.SimpleDateFormat

class ProductControllerTests extends JSONRenderControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
        def configObject = new ConfigObject()
        configObject.cn.ilunch.date.format = "yyyy-MM-dd"
        controller.metaClass.grailsApplication = [config:configObject]
        fixJsonRender controller
    }

    void testShow() {
        def dishName = "chicken"
        def story = "chicken story"
        final detailImageUrl = "http://www.google.com"

        mockDomain(MainDish, [new MainDish(id: 1, name: dishName, story: story, detailImageUrl: detailImageUrl)])
        mockDomain(DistributionArea, [new DistributionArea(id: 1)])

        def priceService = mockFor(cn.ilunch.service.PriceService)

        def today
        priceService.demand.queryProductSchedule(1..1) {mainDish, area, Date date ->
            today = new SimpleDateFormat('yyyy-MM-dd').parse('2011-04-14')
            today.clearTime()
            [new ProductAreaPriceSchedule(price: 5, fromDate: today, toDate: today + 2)]
        }
        controller.priceService = priceService.createMock()

        controller.params.productId = 1
        controller.params.areaId = 1
        controller.showDetail()

        //assertEquals "/foo/bar", fc.response.redirectedUrl
        assertEquals('{"name":"chicken","price":5,"serveDate":{"fromDate":"2011-04-14","toDate":"2011-04-16"},"imageURL":"http://www.google.com","story":"chicken story"}',
                controller.response.contentAsString)
    }

    void testShowNotFound() {
        def dishName = "chicken"
        def story = "chicken story"
        final detailImageUrl = "http://www.google.com"

        mockDomain(MainDish, [new MainDish(id: 1, name: dishName, story: story, detailImageUrl: detailImageUrl)])
        mockDomain(DistributionArea, [new DistributionArea(id: 1)])

        def priceService = mockFor(cn.ilunch.service.PriceService)

        priceService.demand.queryProductSchedule(1..1) {mainDish, area, Date date ->
            []
        }
        controller.priceService = priceService.createMock()

        controller.params.productId = 1
        controller.params.areaId = 1
        controller.showDetail()

        assertEquals('exception', controller.forwardArgs.controller)
        assertEquals('scheduleNotFound', controller.forwardArgs.action)
    }

    void testShowEntityNotFound() {
        mockDomain(MainDish, [])
        mockDomain(DistributionArea, [])

        controller.params.productId = 1
        controller.params.areaId = 1
        controller.showDetail()

        assertEquals('exception', controller.forwardArgs.controller)
        assertEquals('entityNotFound', controller.forwardArgs.action)
        assertEquals(controller.params.productId, controller.forwardArgs.params.id)
    }
}
