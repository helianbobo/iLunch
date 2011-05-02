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
        final originalImageUrl = "http://www.google.com"

        final tags = [new Tag(value: "good"), new Tag(value: "bad")]
        mockDomain(Tag, tags)
        mockDomain(Product, [new Product(id: 1,tags:tags, name: dishName, story: story, originalImageUrl: originalImageUrl)])
        mockDomain(DistributionArea, [new DistributionArea(id: 1)])

        def priceService = mockFor(cn.ilunch.service.PriceService)

        def today
        priceService.demand.queryProductSchedule(1..1) {mainDish, area, Date date ->
            today =Date.parse('yyyy-MM-dd','2011-04-14')
            [new ProductAreaPriceSchedule(remain:5, quantity:50, price:5, fromDate: today, toDate: today + 2)]
        }
        controller.priceService = priceService.createMock()

        controller.params.productId = 1
        controller.params.areaId = 1
        controller.showDetail()
        //assertEquals "/foo/bar", fc.response.redirectedUrl
        assertEquals('{"name":"chicken","price":5,"startDate":"2011-04-14","endDate":"2011-04-16","flavour":[{"value":"good"},{"value":"bad"}],"remain":5,"quantity":50,"imageURL":"http://www.google.com","story":"chicken story"}',
                controller.response.contentAsString)
    }

    void testShowNotFound() {
        def dishName = "chicken"
        def story = "chicken story"
        final detailImageUrl = "http://www.google.com"

        mockDomain(Product, [new Product(id: 1, name: dishName, story: story, detailImageUrl: detailImageUrl)])
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
        mockDomain(Product, [])
        mockDomain(DistributionArea, [])

        controller.params.productId = 1
        controller.params.areaId = 1
        controller.showDetail()

        assertEquals('exception', controller.forwardArgs.controller)
        assertEquals('entityNotFound', controller.forwardArgs.action)
        assertEquals(controller.params.productId, controller.forwardArgs.params.id)
    }
}
