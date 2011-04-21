package cn.ilunch.domain

/**
 * Created by IntelliJ IDEA.
 * User: lsha6086
 * Date: 4/21/11
 * Time: 3:33 PM
 * To change this template use File | Settings | File Templates.
 */
class ProductTest extends GroovyTestCase{
    public void testType(){
        assertEquals("主菜",new MainDish().type())
        assertEquals("配菜",new SideDish().type())
    }

    public void testGetDateRange(){
        def sideDish = new SideDish()

        def fromDate = Date.parse("yyyy-MM-dd","2011-01-05")
        def toDate = Date.parse("yyyy-MM-dd","2011-03-05")
        def schedule = new ProductAreaPriceSchedule(fromDate: fromDate, toDate:toDate)
        def schedule2 = new ProductAreaPriceSchedule(fromDate: fromDate+3, toDate:toDate+5)

        sideDish.productAreaPriceSchedules = [schedule, schedule2]
        assertEquals(2, sideDish.scheduleDates.size())

        assertEquals(fromDate, sideDish.scheduleDates[0].fromDate)
        assertEquals(toDate, sideDish.scheduleDates[0].toDate)

        assertEquals(fromDate+3, sideDish.scheduleDates[1].fromDate)
        assertEquals(toDate+5, sideDish.scheduleDates[1].toDate)
    }
}
