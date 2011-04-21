package cn.ilunch.domain

import cn.ilunch.security.ILunchUserDetails
import java.awt.geom.Area
import grails.plugins.springsecurity.Secured
import org.hibernate.criterion.CriteriaSpecification

class ProductController {
    def priceService
    def dataSource
    def sessionFactory
    def springSecurityService

    def showDetail = {
        def mainDishInstance = MainDish.get(params.productId)
        def areaInstance = DistributionArea.get(params.areaId)

        def date
        def dateFormatString = grailsApplication.config.cn.ilunch.date.format

        if (params.date)
            date = Date.parse(dateFormatString, params.date)

        if (!mainDishInstance) {
            forward(controller: "exception", action: "entityNotFound", params: [id: params.productId, entityName: Product])
            return
        } else {
            def schedule = priceService.queryProductSchedule(mainDishInstance, areaInstance, date)[0]
            if (!schedule) {
                forward(controller: "exception", action: "scheduleNotFound")
                return
            }
            render(contentType: "text/json") {
                name = mainDishInstance.name
                price = schedule.price
                serveDate = [
                        fromDate: schedule.fromDate.format(dateFormatString),
                        toDate: schedule.toDate.format(dateFormatString)
                ]
                imageURL = mainDishInstance.detailImageUrl
                story = mainDishInstance.story
            }
        }
    }

    def listAllMainDishOnSelectionPage = {
        def dateFormatString = grailsApplication.config.cn.ilunch.date.format
        def fromDate = Date.parse(dateFormatString, params.date)
        def max = params.max
        def className = "cn.ilunch.domain.MainDish"
        def areaId = params.areaId as long
        def result = queryProductOnSelectionPage(fromDate, areaId, className, max)
        renderJSONFromResult(result)
    }

    def listAllSideDishOnSelectionPage = {
        def dateFormatString = grailsApplication.config.cn.ilunch.date.format
        def fromDate = Date.parse(dateFormatString, params.date)
        def max = params.max
        def className = "cn.ilunch.domain.SideDish"
        def areaId = params.areaId as long
        def result = queryProductOnSelectionPage(fromDate, areaId, className, max)
        renderJSONFromResult(result)
    }

    private def queryProductOnSelectionPage(Date fromDate, long areaId, String className, max) {
        def sqlQuery = sessionFactory.currentSession.createSQLQuery("""select S2.price as price,S2.product_id as productId,S2.from_date as fromDate,S2.to_date as toDate,p.name as productName, p.story as story, p.small_image_url as smallImageUrl
                    from product_area_price_schedule S2
                    left join product p on p.id = S2.product_id
                    right join (select t.product_id,from_date as fromDate
                        from product_area_price_schedule t
                        where t.area_id=:area
                        and (t.to_date>=:st or t.to_date is null )
                        group by t.product_id,from_date) S1
                        on S1.product_id=S2.product_id and S1.fromDate=S2.from_date
                        where p.class = :className order by S2.fromDate asc""")

        sqlQuery.setTimestamp("st", fromDate)
        sqlQuery.setLong("area", areaId)
        sqlQuery.setString("className", className)

        if (max)
            sqlQuery.setMaxResults(max)

        sqlQuery.list()
    }

    def listAllMainDishOnIndexPage = {
        def dateFormatString = grailsApplication.config.cn.ilunch.date.format
        def fromDate = Date.parse(dateFormatString, params.date) + 2  //后天开始
        def max = params.max
        def areaId = params.areaId as long
        def className = "cn.ilunch.domain.MainDish"

        List result = queryProductOnIndexPage(fromDate, areaId, className, max)
        renderJSONFromResult(result)
    }

    def listAllSideDishOnIndexPage = {
        def dateFormatString = grailsApplication.config.cn.ilunch.date.format
        def fromDate = Date.parse(dateFormatString, params.date) + 2  //后天开始
        def max = params.max
        def areaId = params.areaId as long
        def className = "cn.ilunch.domain.SideDish"

        List result = queryProductOnIndexPage(fromDate, areaId, className, max)
        renderJSONFromResult(result)
    }

    private List queryProductOnIndexPage(Date fromDate, long areaId, String className, max) {
        def sqlQuery = sessionFactory.currentSession.createSQLQuery("""select S2.price as price,S2.product_id as productId,S2.from_date as fromDate,S2.to_date as toDate,p.name as productName, p.story as story, p.small_image_url as smallImageUrl
                    from product_area_price_schedule S2
                    left join product p on p.id = S2.product_id
                    right join (select t.product_id ,min(t.from_date) as fromDate
                        from product_area_price_schedule t
                        where t.area_id=:area
                        and (t.to_date>=:st or t.to_date is null )
                        group by t.product_id) S1
                    on S1.product_id=S2.product_id and S1.fromDate=S2.from_date
                    where p.class=:className order by S2.fromDate asc""")

        sqlQuery.setTimestamp("st", fromDate)
        sqlQuery.setLong("area", areaId)
        sqlQuery.setString("className", className)

        if (max)
            sqlQuery.setMaxResults(max)


        sqlQuery.list()
    }

    private def renderJSONFromResult(result) {
        def dateFormatString = grailsApplication.config.cn.ilunch.date.format
        render(contentType: "text/json") {
            mainDishes = array {
                for (schedule in result) {
                    dish([
                            name: schedule[4],
                            price: schedule[0],
                            serveDate: [
                                    fromDate: schedule[2].format(dateFormatString),
                                    toDate: schedule[3]?.format(dateFormatString)
                            ],
                            imageURL: schedule[6],
                            story: schedule[5]]
                    )
                }
            }
        }
    }

    def index = {
        redirect(action: "list")
    }

    def addProduct = {
//        promptProductName {
//            on("submit").to "checkProductName"
//            on("back").to "returnToIndex"
//        }
//
//        checkProductName {
//            action {
//                if (!params.productName || Product.findByName(params.productName)) {
//                    flow.message = "product already exist"
//
//                    flow.backUrl = "promptProductName"
//                    throw new ProductAlreadyExistException("");
//                }
//                flow.productName = params.productName
//                flow.productStory = params.productStory
//            }
//            on("success").to "uploadImage"
//            on(ProductAlreadyExistException).to "showErrorMessage"
//        }
//        showErrorMessage {
//            action {
//                return [errorMsg: flow.message]
//            }
//            on("success").to "handleError"
//            on("back").to "$flow.backUrl"
//        }
//
//        handleError {
//
//        }
//        uploadImage {
//            on("uploadImage").to "saveImage"
//            on("back").to "promptProductName"
//        }
//
//        saveImage {
//            action {
//                def f = request.getFile('image')
//                if (!f.empty) {
//                    String location = grailsApplication.config.cn.ilunch.product.image.location
//                    def newFile = new File(location + "/${flow.productName}" + ".jpg")
//                    f.transferTo(newFile)
//                }
//                else {
//                    flash.message = 'file cannot be empty'
//                }
//            }
//            on("success").to "promptProductPrice"
//            on(Exception).to "showErrorMessage"
//        }
//
//        promptProductPrice {
//            on("submit").to "checkProductPrice"
//            on("back").to "uploadImage"
//        }
//
//        checkProductPrice {
//            action {
//                if (!params.productPrice || (params.productPrice as float) <= 0.0f) {
//                    flow.message = "产品价格不能小于0"
//
//                    flow.backUrl = "promptProductPrice"
//                    throw new IllegalProductPriceException();
//                }
//                flow.productPrice = params.productPrice
//            }
//            on("success").to "promptDateRange"
//            on(IllegalProductPriceException).to "showErrorMessage"
//        }
//
//        promptDateRange {
//            on("submit").to "checkProductDate"
//            on("back").to "promptProductPrice"
//        }
//
//        checkProductDate {
//            action {
//                def fromDate = Date.parse(grailsApplication.config.cn.ilunch.date.format, params.fromDate)
//                def toDate = Date.parse(grailsApplication.config.cn.ilunch.date.format, params.toDate)
//                if (fromDate > toDate) {
//                    flow.message = "起始日期不能大于结束日期"
//                    throw new IllegalDateRangeException()
//                }
//                flow.fromDate = fromDate
//                flow.toDate = toDate
//            }
//            on("success").to "printNewProductInfo"
//            on("IllegalDateRangeException").to "showErrorMessage"
//        }
//
//        confirm {
//            on("confirm").to "saveAll"
//            on("back").to "promptDateRange"
//        }
//
//        printNewProductInfo{
//            action {
//                return [name: flow.productName,story: flow.productStory, price: flow.productPrice, from: flow.fromDate, to: flow.toDate]
//            }
//            on("success").to "confirm"
//            on(Exception).to "showErrorMessage"
//        }
//
//        saveAll{
//            action{
//                new Product(name:flow.productName, story:flow.productStory).save()
//            }
//        }
//        finish {
//
//        }

    }

    @Secured(['ROLE_MANAGER'])
    def list = {
        ILunchUserDetails user = springSecurityService.principal
        def areaId = user.areaId
        def area = DistributionArea.get(areaId)
        def today = new Date()
        today.clearTime()
        def c = Product.createCriteria()
        def result = c.list {
            eq('status', Product.INUSE)
            productAreaPriceSchedules {
                and {
                    eq('area', area)
                    or {
                        isNull('toDate')
                        and {
                            le('fromDate', today)
                            ge('toDate', today)
                        }
                    }
                }

            }
            resultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
        }

        return [productInstanceList: result, productInstanceTotal: result.size()]
    }

    @Secured(['ROLE_MANAGER'])
    def create = {
        def productInstance = new Product()
        productInstance.properties = params
        return [productInstance: productInstance, principal: springSecurityService.principal]
    }

    @Secured(['ROLE_MANAGER'])
    def save = {
        def areaId = params.areaId
        def area = DistributionArea.get(areaId)
        if (!area) {
            flash.message = "无法找到该地区"
            redirect(action: "create")
            return
        }
        def createSchedule = params.createSchedule
        def price = params.price
        if (!price || createSchedule && (price as float) < 0.0f) {
            flash.message = "价格必须大于零"
            redirect(action: "create")
            return
        }

        def name = params.name
        if (!name) {
            flash.message = "菜名不能为空"
            redirect(action: "create")
            return
        }

        def f = request.getFile('image')
        if (!f.empty) {
            String location = grailsApplication.config.cn.ilunch.product.image.location
            def newFile = new File(location + "/${params.name}" + ".jpg")
            f.transferTo(newFile)
        }
        def productInstance = new Product(name: name, story: params.story, originalImageUrl: Product.convertServerImageURL(params.name))

        try {
            Product.withTransaction {
                productInstance.save()
                if (params.createSchedule) {
                    def dateFormatString = grailsApplication.config.cn.ilunch.date.format
                    def fromDate = params.fromDate
                    def toDate = params.toDate
                    def schedule = new ProductAreaPriceSchedule(fromDate:fromDate, toDate:toDate,product: productInstance, area: area, price: params.price)
                    productInstance.addToProductAreaPriceSchedules schedule
                    schedule.save()
                }
            }
        }
        catch (e) {
            flash.message = "保存失败，请联系管理员"
            redirect(action: "show", id: productInstance.id)
        }
        render(view: "create", model: [productInstance: productInstance])
    }

    def show = {
        def productInstance = Product.get(params.id)
        if (!productInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'location.label', default: 'Location'), params.id])}"
            redirect(action: "list")
        }
        else {
            [productInstance: productInstance]
        }
    }

    def edit = {
        def productInstance = Product.get(params.id)
        if (!productInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'location.label', default: 'Location'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [locationInstance: productInstance]
        }
    }

    def update = {
        def productInstance = Product.get(params.id)
        if (productInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (productInstance.version > version) {

                    productInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'location.label', default: 'Location')] as Object[], "Another user has updated this Location while you were editing")
                    render(view: "edit", model: [locationInstance: productInstance])
                    return
                }
            }
            productInstance.properties = params
            if (!productInstance.hasErrors() && productInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'location.label', default: 'Location'), productInstance.id])}"
                redirect(action: "show", id: productInstance.id)
            }
            else {
                render(view: "edit", model: [locationInstance: productInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'location.label', default: 'Location'), params.id])}"
            redirect(action: "list")
        }
    }
}