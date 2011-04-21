package cn.ilunch.domain
/**
 * Created by IntelliJ IDEA.
 * User: janexie
 * Date: 11-4-16
 * Time: 下午5:59
 * To change this template use File | Settings | File Templates.
 */

class PersonControllerTests extends JSONRenderControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
        fixJsonRender controller
    }
  void testPreference() {
      Customer customer = new Customer()
      customer.cellNumber = "12345678901"
      customer.name = "liuchao"
      customer.pointBalance = 123
      mockDomain(Customer, [customer])

      Building building = new Building()
      building.name = "qq"
      building.latitude = 123.31
      building.longitude = 12.31


      customer.primaryBuilding = building

      DistributionPoint dp = new DistributionPoint()
      dp.name="dp1"

      building.distributionPoint = dp

       DistributionArea area = new DistributionArea()
      area.name = "zhangjiang"
      area.longitude = 23.12
      area.latitude = 123.23
      dp.area = area

      mockDomain(Building,[building])
      mockDomain(DistributionPoint,[dp])
      mockDomain(DistributionArea,[area])
    controller.params.id = 1
      controller.preference()
      assertEquals """{"id":1,"nickname":"liuchao","phoneNumber":"12345678901","points":123,"area":{"areaName":"zhangjiang","areaId":1,"arealLongitude":23.12,"areaLatitude":123.23},"building":{"buildId":1,"buildingLongitude":12.31,"buildingLatitude":123.31}}""", controller.response.contentAsString
//      println controller.response.contentAsString
  }

    void testSaveCard(){
        controller.params.cartInfo = "testInfo"
        controller.saveCart()
        assertEquals 'testInfo', controller.session.attributes.cartInfo
    }
}
