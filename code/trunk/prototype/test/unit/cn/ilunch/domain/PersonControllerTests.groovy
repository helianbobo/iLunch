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
        dp.name = "dp1"

        building.distributionPoint = dp

        DistributionArea area = new DistributionArea()
        area.name = "zhangjiang"
        area.longitude = 23.12
        area.latitude = 123.23
        dp.area = area

        mockDomain(Building, [building])
        mockDomain(DistributionPoint, [dp])
        mockDomain(DistributionArea, [area])
        controller.params.id = 1
        controller.preference()
        assertEquals """{"id":1,"nickname":"liuchao","phoneNumber":"12345678901","points":123,"distributionArea":{"name":"zhangjiang","id":1,"arealLongitude":23.12,"areaLatitude":123.23},"building":{"id":1,"name":"qq","longitude":12.31,"latitude":123.31}}""", controller.response.contentAsString
//      println controller.response.contentAsString
    }

    void testSaveCard() {
        controller.params.cartInfo = "testInfo"
        controller.saveCart()
        assertEquals 'testInfo', controller.session.attributes.cartInfo
    }

    void testRegisterWithUsedNumber() {

        mockDomain(Role, [new Role(authority: 'ROLE_USER')])
        mockDomain(UserRole, [])
        mockDomain(Customer, [new Customer(cellNumber: "12345678901")])
        def number1 = Customer.findAll().size()
        controller.params.cellNumber = "12345678901"
        controller.params.password = "test123"
        controller.springSecurityService = [encodePassword: {it -> return it}]
        controller.register()

        assertEquals('exception', controller.forwardArgs.controller)
        assertEquals('cellphoneNumberRegistered', controller.forwardArgs.action)
        assertEquals("12345678901", controller.forwardArgs.params.number)
    }

    void testRegister() {

        mockDomain(Role, [new Role(authority: 'ROLE_USER')])
        mockDomain(UserRole, [])
        mockDomain(Customer, [])
        def number1 = Customer.findAll().size()
        controller.params.cellNumber = "12345678901"
        controller.params.password = "test123"
        controller.springSecurityService = [encodePassword: {it -> return it}]
        controller.register()

        def number2 = Customer.findAll().size()
        assertTrue(number2-number1==1)
    }

    void testRegisterWithEmptyNumber() {

        mockDomain(Role, [new Role(authority: 'ROLE_USER')])
        mockDomain(UserRole, [])
        mockDomain(Customer, [])
        def number1 = Customer.findAll().size()
        controller.params.password = "test123"
        controller.springSecurityService = [encodePassword: {it -> return it}]
        controller.register()

        assertEquals('exception', controller.forwardArgs.controller)
        assertEquals('cellphoneNumberInvalid', controller.forwardArgs.action)
        assertEquals(null, controller.forwardArgs.params.number)
    }

    void testRegisterWithInvaildNumber() {

        mockDomain(Role, [new Role(authority: 'ROLE_USER')])
        mockDomain(UserRole, [])
        mockDomain(Customer, [])

        controller.params.cellNumber = "a2345678901"
        controller.params.password = "test123"
        controller.springSecurityService = [encodePassword: {it -> return it}]
        controller.register()

        assertEquals('exception', controller.forwardArgs.controller)
        assertEquals('cellphoneNumberInvalid', controller.forwardArgs.action)
        assertEquals('a2345678901', controller.forwardArgs.params.number)
    }
}
