package cn.ilunch.service

import cn.ilunch.domain.ProductAreaPriceSchedule
import grails.test.GrailsUnitTestCase
import cn.ilunch.domain.Product
import cn.ilunch.domain.DistributionArea

/**
 * Created by IntelliJ IDEA.
 * User: lsha6086
 * Date: 4/22/11
 * Time: 3:40 PM
 * To change this template use File | Settings | File Templates.
 */
class RepositoryServiceTests extends GrailsUnitTestCase {
    RepositoryService repositoryService
    PriceService priceService

    protected void setUp() {
        super.setUp()
        repositoryService = new RepositoryService()
    }

    void testReduce() {
        mockDomain(ProductAreaPriceSchedule, [])
        ProductAreaPriceSchedule schedule = new ProductAreaPriceSchedule(remain: 50)

        def priceService = mockFor(cn.ilunch.service.PriceService)
        Product product = new Product()
        DistributionArea area = new DistributionArea()
        priceService.demand.queryProductSchedule(1) {mainDish, area1, date ->
            assertEquals(product, mainDish)
            assertEquals(area, area1)
            return [schedule]
        }
        repositoryService.priceService = priceService.createMock()

        repositoryService.reduce(product, area, new Date(), 10)
        assertEquals(40, schedule.remain)
    }

    void testInvalidReduce() {
        mockDomain(ProductAreaPriceSchedule, [])
        ProductAreaPriceSchedule schedule = new ProductAreaPriceSchedule(remain: 8)

        def priceService = mockFor(cn.ilunch.service.PriceService)
        Product product = new Product()
        DistributionArea area = new DistributionArea()
        priceService.demand.queryProductSchedule(1) {mainDish, area1, date ->
            assertEquals(product, mainDish)
            assertEquals(area, area1)
            return [schedule]
        }
        repositoryService.priceService = priceService.createMock()
        try {
            repositoryService.reduce(product, area, new Date(), 10)
        } catch (NotEnoughProductException e) {
            assertEquals(8, schedule.remain)
            return
        }
        fail()
    }

    void testTryReduce() {
        mockDomain(ProductAreaPriceSchedule, [])
        ProductAreaPriceSchedule schedule = new ProductAreaPriceSchedule(remain: 50)

        def priceService = mockFor(cn.ilunch.service.PriceService)
        Product product = new Product()
        DistributionArea area = new DistributionArea()
        priceService.demand.queryProductSchedule(1) {mainDish, area1, date ->
            assertEquals(product, mainDish)
            assertEquals(area, area1)
            return [schedule]
        }
        repositoryService.priceService = priceService.createMock()

        repositoryService.tryReduce(product, area, new Date(), 10)
        assertEquals(50, schedule.remain)
    }

    void testInvalidTryReduce() {
        mockDomain(ProductAreaPriceSchedule, [])
        ProductAreaPriceSchedule schedule = new ProductAreaPriceSchedule(remain: 8)

        def priceService = mockFor(cn.ilunch.service.PriceService)
        Product product = new Product()
        DistributionArea area = new DistributionArea()
        priceService.demand.queryProductSchedule(1) {mainDish, area1, date ->
            assertEquals(product, mainDish)
            assertEquals(area, area1)
            return [schedule]
        }
        repositoryService.priceService = priceService.createMock()
        try {
            repositoryService.tryReduce(product, area, new Date(), 10)
        } catch (NotEnoughProductException e) {
            assertEquals(8, schedule.remain)
            return
        }
        fail()
    }
}
