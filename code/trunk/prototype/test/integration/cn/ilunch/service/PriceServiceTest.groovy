package cn.ilunch.service

import cn.ilunch.domain.DistributionArea
import java.text.SimpleDateFormat
import cn.ilunch.domain.SideDish
import cn.ilunch.domain.MainDish

/**
 * Created by IntelliJ IDEA.
 * User: lsha6086
 * Date: 4/13/11
 * Time: 2:54 PM
 * To change this template use File | Settings | File Templates.
 */
class PriceServiceTest extends GroovyTestCase {
    def priceService

    protected void setUp() {
        super.setUp()
    }

    void testQuery() {
        def sideDish = SideDish.findByName('豆角')
        def curryFish = MainDish.findByName('咖喱鱼')
        def psourceFish = MainDish.findByName('茄汁鱼')
        def area = DistributionArea.findByName('张江高科')

        assertEquals(18.0, priceService.queryProductSchedule(sideDish, area)[0].price);
        assertEquals(18.0, priceService.queryProductSchedule(sideDish, area, null)[0].price);
        assertEquals(15.0, priceService.queryProductSchedule(sideDish, area, new SimpleDateFormat('yyyy-MM-dd').parse('2011-04-04'))[0].price);
        assertEquals(18.0, priceService.queryProductSchedule(sideDish, area, new SimpleDateFormat('yyyy-MM-dd').parse('2011-04-06'))[0].price);
        assertEquals(30.0, priceService.queryProductSchedule(curryFish, area, new SimpleDateFormat('yyyy-MM-dd').parse('2011-04-06'))[0].price);
        assertTrue(0 == priceService.queryProductSchedule(curryFish, area, new SimpleDateFormat('yyyy-MM-dd').parse('2011-03-06')).size());
        assertEquals(40, priceService.queryProductSchedule(psourceFish, area)[0].price);
        assertEquals(new SimpleDateFormat('yyyy-MM-dd').parse('2011-04-27'), priceService.queryProductSchedule(psourceFish, area)[0].fromDate);
    }
}
