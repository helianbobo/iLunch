package cn.ilunch.domain

import grails.test.*

class ProductTests extends GroovyTestCase {
    protected void setUp() {
        super.setUp()
        controller = new ProductController()
    }

    def controller

    protected void tearDown() {
        super.tearDown()
    }

    void testListAllMainDishOnIndexPage() {
        controller.params.date = '2011-04-11'
        controller.params.areaId = DistributionArea.findByName('张江高科').id
        controller.listAllMainDishOnIndexPage()
        assertEquals """{"mainDishes":[{"name":"茄汁鱼","price":40,"serveDate":{"fromDate":"2011-04-16","toDate":"2011-04-16"},"imageURL":null,"story":"东南亚茄汁鱼的传说"}]}""", controller.response.contentAsString
    }

    void testListAllMainDishOnIndexPageDayAfterTomorrow() {
        controller.params.date = '2011-04-15'
        controller.params.areaId = DistributionArea.findByName('张江高科').id
        controller.listAllMainDishOnIndexPage()

        assertEquals """{"mainDishes":[{"name":"茄汁鱼","price":45,"serveDate":{"fromDate":"2011-04-17","toDate":"2011-04-26"},"imageURL":null,"story":"东南亚茄汁鱼的传说"}]}""", controller.response.contentAsString
    }

    void testListAllMainDishOnIndexPageDayAfterTomorrowWithMaxRowNumber1() {
        controller.params.date = '2011-04-15'
        controller.params.areaId = DistributionArea.findByName('张江高科').id
        controller.params.max = 1
        controller.listAllMainDishOnIndexPage()

        assertEquals """{"mainDishes":[{"name":"茄汁鱼","price":45,"serveDate":{"fromDate":"2011-04-17","toDate":"2011-04-26"},"imageURL":null,"story":"东南亚茄汁鱼的传说"}]}""", controller.response.contentAsString
    }

    void testListAllMainDishOnSelectionPage() {
        controller.params.date = '2011-04-30'
        controller.params.areaId = DistributionArea.findByName('人民广场').id
        controller.params.max = 100
        controller.listAllMainDishOnSelectionPage()
//        println controller.response.contentAsString
        assertEquals """{"mainDishes":[{"name":"茄汁鱼","price":30,"serveDate":{"fromDate":"2011-05-01","toDate":"2011-05-01"},"imageURL":null,"story":"东南亚茄汁鱼的传说"},{"name":"咖喱鱼","price":30,"serveDate":{"fromDate":"2011-05-02","toDate":"2011-05-02"},"imageURL":null,"story":"东南亚咖喱鱼的传说"},{"name":"炸白身鱼","price":30,"serveDate":{"fromDate":"2011-05-03","toDate":"2011-05-03"},"imageURL":null,"story":"炸白身鱼的传说"},{"name":"炸猪排","price":30,"serveDate":{"fromDate":"2011-05-04","toDate":"2011-05-04"},"imageURL":null,"story":"炸猪排的传说"},{"name":"咖喱鱼","price":35,"serveDate":{"fromDate":"2011-05-05","toDate":"2011-05-05"},"imageURL":null,"story":"东南亚咖喱鱼的传说"}]}""", controller.response.contentAsString
    }

    void testListAllSideDishOnSelectionPage() {
        controller.params.date = '2010-01-01'
        controller.params.areaId = DistributionArea.findByName('人民广场').id
        controller.params.max = 100
        controller.listAllSideDishOnSelectionPage()
//        println controller.response.contentAsString
        assertEquals """{"mainDishes":[{"name":"凉皮","price":35,"serveDate":{"fromDate":"2010-01-01","toDate":null},"imageURL":null,"story":"陕西凉皮的传说"},{"name":"豆角","price":35,"serveDate":{"fromDate":"2010-01-01","toDate":null},"imageURL":null,"story":"东北豆角的传说"},{"name":"土豆泥","price":35,"serveDate":{"fromDate":"2010-01-01","toDate":null},"imageURL":null,"story":"德式土豆泥的传说"},{"name":"黄泥螺","price":35,"serveDate":{"fromDate":"2010-01-01","toDate":"2011-01-01"},"imageURL":null,"story":"宁波黄泥螺的传说"},{"name":"黄泥螺","price":35,"serveDate":{"fromDate":"2011-01-02","toDate":null},"imageURL":null,"story":"宁波黄泥螺的传说"}]}""", controller.response.contentAsString
    }

    void testListAllSideDishOnIndexPage() {
        controller.params.date = '2010-01-01'
        controller.params.areaId = DistributionArea.findByName('人民广场').id
        controller.listAllSideDishOnIndexPage()
        assertEquals """{"mainDishes":[{"name":"豆角","price":35,"serveDate":{"fromDate":"2010-01-01","toDate":null},"imageURL":null,"story":"东北豆角的传说"},{"name":"凉皮","price":35,"serveDate":{"fromDate":"2010-01-01","toDate":null},"imageURL":null,"story":"陕西凉皮的传说"},{"name":"黄泥螺","price":35,"serveDate":{"fromDate":"2010-01-01","toDate":"2011-01-01"},"imageURL":null,"story":"宁波黄泥螺的传说"},{"name":"土豆泥","price":35,"serveDate":{"fromDate":"2010-01-01","toDate":null},"imageURL":null,"story":"德式土豆泥的传说"}]}""", controller.response.contentAsString
    }
}