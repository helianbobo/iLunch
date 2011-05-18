import cn.ilunch.domain.Building
import cn.ilunch.domain.DistributionPoint
import cn.ilunch.domain.Customer
import cn.ilunch.domain.DistributionArea
import cn.ilunch.domain.Kitchen
import cn.ilunch.domain.Manager
import cn.ilunch.domain.MainDish
import cn.ilunch.domain.SideDish
import cn.ilunch.domain.ProductAreaPriceSchedule
import java.text.SimpleDateFormat
import cn.ilunch.domain.Product
import cn.ilunch.domain.Role
import cn.ilunch.domain.UserRole



fixture {

    'adminRole'(Role){
        authority = 'ROLE_ADMIN'
    }

    'managerRole'(Role){
        authority = 'ADMIN_ADMIN'
    }

    'userRole'(Role){
        authority = 'USER_ADMIN'
    }

    'person_chenkai'(Customer) {
        name = '陈凯'
        cellNumber = '18600186000'
        password = "password"
        enabled = true
        accountExpired = false
        accountLocked = false
        passwordExpired = false

    }

    'person_chenyu'(Manager) {
        name = '晨煜'
        cellNumber = '18600186001'
        password = "password"
        enabled = true
        accountExpired = false
        accountLocked = false
        passwordExpired = false

    }

    'da_zhangjiang'(DistributionArea) {
        name = "张江高科"
        latitude = '31.204212'
        longitude = '121.600199'
    }

    'da_peoplesquare'(DistributionArea) {
        name = "人民广场"
        latitude = '32.204212'
        longitude = '122.600199'
    }

    "kitchen_zhangjiang"(Kitchen) {
        name = "张江厨房"
    }

    "kitchen_peoplesquare"(Kitchen) {
        name = "人民广场厨房"
        manager = person_chenyu
    }

    'dp_worldtradetower'(DistributionPoint) {
        name = "世贸大厦楼下"
        latitude = '31.205093'
        longitude = '121.598160'
        area = da_peoplesquare
        kitchen = kitchen_peoplesquare
    }



    'building_worldtradetower'(Building) {
        name = '世贸大厦'
        latitude = '31.205093'
        longitude = '121.598160'
        distributionPoint = dp_worldtradetower
    }

    'dp_lingyang'(DistributionPoint) {
        name = "凌阳大厦大堂"
        latitude = '31.205093'
        longitude = '121.598160'
        area = da_zhangjiang
        kitchen = kitchen_zhangjiang

    }



    'building_lingyang'(Building) {
        name = '凌阳大厦'
        latitude = '31.205093'
        longitude = '121.598160'
        distributionPoint = dp_lingyang
    }

    'building_qq'(Building) {
        name = 'QQ大厦'
        latitude = '31.205093'
        longitude = '121.598160'
        distributionPoint = dp_lingyang
    }

    'person_liuchao'(Customer) {
        name = '刘超'
        cellNumber = '18621077586'
        primaryBuilding = building_lingyang
        buildings = [building_lingyang]
        password = springSecurityService.encodePassword('jleo')
        enabled = true
        accountExpired = false
        accountLocked = false
        passwordExpired = false
    }



    'md_curryfish'(MainDish) {
        name = '咖喱鱼'
        story = '东南亚咖喱鱼的传说'
        originalImageUrl = "images/pic_15.jpg"
    }

    'md_psourcefish'(MainDish) {
        name = '茄汁鱼'
        story = '东南亚茄汁鱼的传说'
        originalImageUrl = "images/pic_15.jpg"
    }

    'md_curryricewithfish'(MainDish) {
        name = '炸白身鱼'
        story = '炸白身鱼的传说'
        originalImageUrl = "images/pic_15.jpg"
    }

    'md_curryricewithfriedpork'(MainDish) {
        name = '炸猪排'
        story = '炸猪排的传说'
        originalImageUrl = "images/pic_15.jpg"
    }

    'sd_doujiao'(SideDish) {
        name = '豆角'
        story = '东北豆角的传说'
        originalImageUrl = "images/pic_8.jpg"
    }

    'sd_potatomash'(SideDish) {
        name = '土豆泥'
        story = '德式土豆泥的传说'
        originalImageUrl = "images/pic_8.jpg"
    }

    'sd_liangpi'(SideDish) {
        name = '凉皮'
        story = '陕西凉皮的传说'
        originalImageUrl = "images/pic_8.jpg"
    }

    'sd_huangniluo'(SideDish) {
        name = '黄泥螺'
        story = '宁波黄泥螺的传说'
        originalImageUrl = "images/pic_8.jpg"
    }

    'schedule_sd_doujiao_2010-11-05->2011-04-05'(ProductAreaPriceSchedule) {
        product = sd_doujiao
        area = da_zhangjiang
        price = 15.0
        fromDate = new SimpleDateFormat('yyyy-MM-dd').parse('2010-11-05')
        toDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-04-05')
    }

    'schedule_sd_doujiao_2011-04-06->*'(ProductAreaPriceSchedule) {
        product = sd_doujiao
        area = da_zhangjiang
        price = 18.0
        fromDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-04-06')
    }

    'schedule_md_curryfish_2011-04-06'(ProductAreaPriceSchedule) {
        product = md_curryfish
        area = da_zhangjiang
        price = 30.0
        fromDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-04-06')
        toDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-04-06')
    }

    'schedule_md_psourcefish_2011-04-06'(ProductAreaPriceSchedule) {
        product = md_psourcefish
        area = da_zhangjiang
        price = 40.0
        fromDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-04-16')
        toDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-04-16')
    }

    'schedule_md_psourcefish_2010-04-16'(ProductAreaPriceSchedule) {
        product = md_psourcefish
        area = da_zhangjiang
        price = 40.0
        fromDate = new SimpleDateFormat('yyyy-MM-dd').parse('2010-04-16')
        toDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-04-12')
    }
    'schedule_md_psourcefish_2011-04-17'(ProductAreaPriceSchedule) {
        product = md_psourcefish
        area = da_zhangjiang
        price = 45.0
        fromDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-04-17')
        toDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-04-26')
    }
    'schedule_md_psourcefish_2011-04-27'(ProductAreaPriceSchedule) {
        product = md_psourcefish
        area = da_zhangjiang
        price = 40.0
        fromDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-04-27')
    }

    'schedule_md_psourcefish_peoplesquare_2011-05-01'(ProductAreaPriceSchedule) {
        product = md_psourcefish
        area = da_peoplesquare
        price = 30.0
        fromDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-01')
        toDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-01')
    }

    'schedule_md_curryfish_peoplesquare_2011-05-02'(ProductAreaPriceSchedule) {
        product = md_curryfish
        area = da_peoplesquare
        price = 30.0
        fromDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-02')
        toDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-02')
    }

    'schedule_md_curryricewithfish_peoplesquare_2011-05-03'(ProductAreaPriceSchedule) {
        product = md_curryricewithfish
        area = da_peoplesquare
        price = 30.0
        fromDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-03')
        toDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-03')
    }

    'schedule_md_curryricewithfriedpork_peoplesquare_2011-05-04'(ProductAreaPriceSchedule) {
        product = md_curryricewithfriedpork
        area = da_peoplesquare
        price = 30.0
        fromDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-04')
        toDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-04')
    }

    'schedule_md_curryfish_peoplesquare_2011-05-05'(ProductAreaPriceSchedule) {
        product = md_curryfish
        area = da_peoplesquare
        price = 35.0
        fromDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-05')
        toDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-05')
    }





     'schedule_md_curryfish_2011-05-06'(ProductAreaPriceSchedule) {
        product = md_curryfish
        area = da_zhangjiang
        price = 30.0
        fromDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-06')
        toDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-06')
    }

    'schedule_md_psourcefish_2011-05-16'(ProductAreaPriceSchedule) {
        product = md_psourcefish
        area = da_zhangjiang
        price = 40.0
        fromDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-16')
        toDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-16')
    }

    'schedule_md_psourcefish_2010-05-17'(ProductAreaPriceSchedule) {
        product = md_psourcefish
        area = da_zhangjiang
        price = 40.0
        fromDate = new SimpleDateFormat('yyyy-MM-dd').parse('2010-05-17')
        toDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-17')
    }
    'schedule_md_psourcefish_2011-05-18'(ProductAreaPriceSchedule) {
        product = md_psourcefish
        area = da_zhangjiang
        price = 45.0
        fromDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-18')
        toDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-18')
    }
    'schedule_md_psourcefish_2011-05-19'(ProductAreaPriceSchedule) {
        product = md_psourcefish
        area = da_zhangjiang
        price = 40.0
        fromDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-19')
        toDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-19')
    }

    'schedule_md_psourcefish_peoplesquare_2011-05-20'(ProductAreaPriceSchedule) {
        product = md_psourcefish
        area = da_zhangjiang
        price = 30.0
        fromDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-20')
        toDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-20')
    }

    'schedule_md_curryfish_peoplesquare_2011-05-23'(ProductAreaPriceSchedule) {
        product = md_curryfish
        area = da_zhangjiang
        price = 30.0
        fromDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-23')
        toDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-23')
    }

    'schedule_md_curryricewithfish_peoplesquare_2011-05-24'(ProductAreaPriceSchedule) {
        product = md_curryricewithfish
        area = da_zhangjiang
        price = 30.0
        fromDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-24')
        toDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-24')
    }

    'schedule_md_curryricewithfriedpork_peoplesquare_2011-05-25'(ProductAreaPriceSchedule) {
        product = md_curryricewithfriedpork
        area = da_zhangjiang
        price = 30.0
        fromDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-25')
        toDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-25')
    }

    'schedule_md_curryfish_peoplesquare_2011-05-26'(ProductAreaPriceSchedule) {
        product = md_curryfish
        area = da_zhangjiang
        price = 35.0
        fromDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-26')
        toDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-05-26')
    }








    'schedule_md_liangpi_peoplesquare_2010-01-01'(ProductAreaPriceSchedule) {
        product = sd_liangpi
        area = da_peoplesquare
        price = 35.0
        fromDate = new SimpleDateFormat('yyyy-MM-dd').parse('2010-01-01')
    }



    'schedule_sd_doujiao_peoplesquare_2010-01-01'(ProductAreaPriceSchedule) {
        product = sd_doujiao
        area = da_peoplesquare
        price = 35.0
        fromDate = new SimpleDateFormat('yyyy-MM-dd').parse('2010-01-01')
    }

    'schedule_sd_potatomash_peoplesquare_2010-01-01'(ProductAreaPriceSchedule) {
        product = sd_potatomash
        area = da_peoplesquare
        price = 35.0
        fromDate = new SimpleDateFormat('yyyy-MM-dd').parse('2010-01-01')
    }

    'schedule_sd_huangniluo_peoplesquare_2010-01-01'(ProductAreaPriceSchedule) {
        product = sd_huangniluo
        area = da_peoplesquare
        price = 35.0
        fromDate = new SimpleDateFormat('yyyy-MM-dd').parse('2010-01-01')
        toDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-01-01')
    }

    'schedule_sd_huangniluo_peoplesquare_2011-01-02'(ProductAreaPriceSchedule) {
        product = sd_huangniluo
        area = da_peoplesquare
        price = 35.0
        fromDate = new SimpleDateFormat('yyyy-MM-dd').parse('2011-01-02')
    }
}

post {


    def kitchen = Kitchen.findByName("张江厨房")
    if(!kitchen){
        kitchen = new Kitchen(name:"张江厨房")
        kitchen.distributionPoints = [dp_lingyang]
    }
    def jleo = new Manager();
    jleo.name = "jleo"
    jleo.cellNumber = '13764511823'
    jleo.addToRoles(managerRole)
    jleo.password = springSecurityService.encodePassword('jleo')
    jleo.enabled = true
    jleo.kitchen = kitchen
    jleo.save(flush: true)

    kitchen.setManager (jleo)

    UserRole.create jleo, managerRole, true
    UserRole.create jleo, adminRole, true
    UserRole.create jleo, userRole, true
    UserRole.create person_liuchao, userRole, true
}
