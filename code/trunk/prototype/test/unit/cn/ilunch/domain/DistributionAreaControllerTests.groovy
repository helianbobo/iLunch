package cn.ilunch.domain
/**
 * Created by IntelliJ IDEA.
 * User: janexie
 * Date: 11-4-16
 * Time: 下午6:51
 * To change this template use File | Settings | File Templates.
 */

class DistributionAreaControllerTests extends JSONRenderControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
        fixJsonRender controller
    }

    void testList() {
        Building building = new Building()
        building.name = "qq"
        building.latitude = 123.31
        building.longitude = 12.31

        Building building2 = new Building()
        building2.name = "ilunch"
        building2.latitude = 123.32
        building2.longitude = 12.32

        DistributionPoint dp = new DistributionPoint()
        dp.name = "dp1"

        building.distributionPoint = dp
        building2.distributionPoint = dp

        DistributionArea area = new DistributionArea()
        area.name = "zhangjiang"
        area.longitude = 23.12
        area.latitude = 123.23
        area.status = Location.INUSE

        DistributionArea area2 = new DistributionArea()
        area2.name = "zhangjiang"
        area2.longitude = 23.12
        area2.latitude = 123.23
        area2.status = Location.DELETED

        mockDomain(Building, [building, building2])
        mockDomain(DistributionPoint, [dp])
        mockDomain(DistributionArea, [area,area2])

        area.addToDistributionPoints dp
        dp.addToBuildings building
        dp.addToBuildings building2

        controller.list()

        assertEquals """{"areas":[{"id":1,"name":"zhangjiang","longitude":23.12,"latitude":123.23,"buildings":[{"id":1,"name":"qq","longitude":12.31,"latitude":123.31},{"id":2,"name":"ilunch","longitude":12.32,"latitude":123.32}]}]}""", controller .response.contentAsString
    }

}
