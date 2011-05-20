package cn.ilunch.domain

import cn.ilunch.security.ILunchUserDetails

import grails.plugins.springsecurity.Secured
import org.hibernate.criterion.CriteriaSpecification
import org.codehaus.groovy.grails.commons.GrailsApplication

class ProductController {
    def priceService
    def dataSource
    def sessionFactory
    def springSecurityService

    def showDetail = {
        def mainDishInstance = Product.get(params.productId as Long)
        def areaInstance = DistributionArea.get(params.areaId as Long)

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
                startDate = schedule.fromDate.format(dateFormatString)
                endDate = schedule.toDate?.format(dateFormatString)
                flavour = array {
                    for (tagInstance in mainDishInstance.tags) {
                        tag([value: tagInstance.value])
                    }
                }
                remain = schedule.remain
                quantity = schedule.quantity
                imageURL = mainDishInstance.originalImageUrl
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
        def sqlQuery = sessionFactory.currentSession.createSQLQuery("""select S2.price as price,S2.product_id as productId,S2.from_date as fromDate,S2.to_date as toDate,p.name as productName, p.story as story, p.original_image_url as original_image_url,p.id as id,p.class as class,t.value
                    from product_area_price_schedule S2
                    left join product p on p.id = S2.product_id
                    left join product_tags pt on p.id=pt.product_id
                    left join tag t on t.id = pt.tag_id
                    right join (select t.product_id,from_date as fromDate
                        from product_area_price_schedule t
                        where t.area_id=:area
                        and t.status = 0
                        and (t.to_date>=:st or t.to_date is null )
                        group by t.product_id,from_date) S1
                        on S1.product_id=S2.product_id and S1.fromDate=S2.from_date
                        where p.class = :className order by S2.from_date asc""")

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
        def sqlQuery = sessionFactory.currentSession.createSQLQuery("""select S2.price as price,S2.product_id as id,S2.from_date as fromDate,S2.to_date as toDate,p.name as productName, p.story as story, p.original_image_url as original_image_url, p.id as id,p.class,t.value
                    from product_area_price_schedule S2
                    left join product p on p.id = S2.product_id
                    left join product_tags pt on p.id=pt.product_id
                    left join tag t on t.id = pt.tag_id
                    right join (select t.product_id ,min(t.from_date) as fromDate
                        from product_area_price_schedule t
                        where t.area_id=:area
                        and t.status = 0
                        and (t.to_date>=:st or t.to_date is null )
                        group by t.product_id) S1
                    on S1.product_id=S2.product_id and S1.fromDate=S2.from_date
                    where p.class=:className order by S2.from_date asc""")

        sqlQuery.setTimestamp("st", fromDate)
        sqlQuery.setLong("area", areaId)
        sqlQuery.setString("className", className)

        if (max)
            sqlQuery.setMaxResults(max)

        sqlQuery.list()
    }

    private def renderJSONFromResult(result) {
        def dateFormatString = grailsApplication.config.cn.ilunch.date.format
        def tagMap = [:]
        def priceMap = [:]
        result.each {it ->
            def id = it[7]
            def tag = it[9]
            def p = new Expando()
            p.price = it[0]
            p.fromDate = it[2].format(dateFormatString)
            p.toDate = it[3]?.format(dateFormatString)
            def key = "id" + id
            if (tagMap.containsKey(key)) {
                tagMap[key] << tag
            } else {
                if (tag)
                    tagMap.put(key, [tag] as Set)
            }
            if (priceMap.containsKey(key)) {
                def found = priceMap[key].find {
                    it.fromDate == p.fromDate
                }
                if (!found)
                    priceMap[key] << p
            } else {
                priceMap.put(key, [p] as Set)
            }
        }


        Set addedSchedule = []

        render(contentType: "text/json") {
            products = array {
                for (schedule in result) {
                    if (addedSchedule.add(schedule[7])) {
                        dish([
                                id: schedule[7],
                                name: schedule[4],
//                            price: schedule[0],
//                            fromDate: schedule[2].format(dateFormatString),
//                            toDate: schedule[3]?.format(dateFormatString),
                                prices: array {
                                    for (pc in priceMap["id" + schedule[7]])
                                        price(
                                                [price: pc.price, startDate: pc.fromDate, endDate: pc.toDate]
                                        )
                                },
                                imageURL: schedule[6],
                                flavors: array {
                                    for (t in tagMap["id" + schedule[7]])
                                        tag(
                                                [value: t]
                                        )
                                },
                                story: schedule[5]]

                        )
                    }

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
            or {
                isEmpty('productAreaPriceSchedules')
                productAreaPriceSchedules {
                    and {
//                        eq('status', ProductAreaPriceSchedule.INUSE)
                        if (areaId)
                            eq('area', area)
//                        or {
//                            isNull('toDate')
//                            and {
//                                le('fromDate', today)
//                                ge('toDate', today)
//                            }
//                        }
                    }
                }
            }
            resultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
        }
//        query.setResultTransformer(Transformers.DISTINCT_ROOT_ENTITY);
//        def result = query.list()


        return [productInstanceList: result, productInstanceTotal: result.size(), areaId: areaId]
    }

    @Secured(['ROLE_MANAGER'])
    def createMainDish = {
        def productInstance = new Product()
        productInstance.properties = params
        return [productInstance: productInstance, principal: springSecurityService.principal]
    }

    @Secured(['ROLE_MANAGER'])
    def createSideDish = {
        def productInstance = new Product()
        productInstance.properties = params
        return [productInstance: productInstance, principal: springSecurityService.principal]
    }

    @Secured(['ROLE_MANAGER'])
    def save = {
        def isMainDish = Boolean.valueOf(params.isMainDish)
        def createAction = isMainDish ? "createMainDish" : "createSideDish"
        def areaId = params.areaId
        def area = DistributionArea.get(areaId)
        if (!area) {
            flash.message = "无法找到该地区"
            redirect(action: createAction)
            return
        }
        def createSchedule = params.createSchedule
        def price = params.price
        if (createSchedule && (!price || (price as float) < 0.0f)) {
            flash.message = "价格必须大于零"
            redirect(action: createAction)
            return
        }

        def quantity = params.quantity
        if (createSchedule && (!quantity || (quantity as int) < 0)) {
            flash.message = "库存必须大于零"
            redirect(action: createAction)
            return
        }

        def name = params.name
        if (!name) {
            flash.message = "菜名不能为空"
            redirect(action: createAction)
            return
        }
        if (Product.findByName(name)) {
            flash.message = "菜名不能重复"
            redirect(action: createAction)
            return
        }

        def f = request.getFile('image')
        if (!f.empty) {
            String location = grailsApplication.config.cn.ilunch.product.image.location
            def newFile = new File(location + "/${params.name}" + ".jpg")
            f.transferTo(newFile)
        }

        def productInstance





        if (isMainDish) {
            productInstance = new MainDish(name: name, story: params.story, originalImageUrl: "images/${name}.jpg")
        } else {
            productInstance = new SideDish(name: name, story: params.story, originalImageUrl: "images/${name}.jpg")
        }


        try {
            Product.withTransaction {
                productInstance.save()
                if (params.createSchedule) {
                    def fromDate = params.fromDate
                    def toDate
                    if (isMainDish) {
                        toDate = fromDate
                    } else {
                        toDate = params.toDate
                    }


                    def schedule = new ProductAreaPriceSchedule(quantity: quantity, fromDate: fromDate, toDate: toDate, product: productInstance, area: area, price: params.price)
                    productInstance.addToProductAreaPriceSchedules schedule
                    schedule.save()
                }
            }
        }
        catch (e) {
            e.printStackTrace()
            flash.message = "保存失败，请联系管理员"
            redirect(action: createAction)
            return
        }
        redirect(action: 'list')
    }

    def linkTag = {
        def productId = params.productId as Long
        def tagId = params.tagId as Long


        def product = Product.get(productId)
        def tag = Tag.get(tagId)

        if (!tag) {
            flash.message = "没有找到该标签"
            redirect(action: editTags, params: [id: productId])
            return
        }

        if (product.tags.contains(tag)) {
            flash.message = "标签已经存在"
            redirect(action: editTags, params: [id: productId])
            return
        }

        product.addToTags(tag)

        if (!product.hasErrors() && product.save()) {
            flash.message = "标签关联成功"
            redirect(action: editTags, params: [id: productId])
        } else {
            flash.message = "标签关联失败"
            redirect(action: editTags, params: [id: productId])
        }
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
            flash.message = "没有找到该产品"
            redirect(action: "list")
        }
        else {
            return [productInstance: productInstance]
        }
    }

    def deleteTag = {
        def tagId = params.id as Long
        def productId = params.productId as Long
        def product = Product.get(productId)
        if (!product) {
            flash.message = "没有找到该产品"
            redirect(action: "list")
            return
        }
        def tag = Tag.get(tagId)
        tag.removeFromProducts(product)
        product.removeFromTags tag
        try {
            tag.delete(flush: true)
            flash.message = "标签已经被删除"
            redirect(action: editTags, params: [id: product.id])
            return
        }
        catch (org.springframework.dao.DataIntegrityViolationException e) {
            flash.message = "标签删除失败"
            redirect(action: editTags, params: [id: product.id])
            return
        }
        redirect(action: editTags, params: [id: product.id])
    }

    def addTag = {
        def value = params.value
        def productId = params.productId

        if (!value) {
            flash.message = "标签不能为空"
            redirect(action: editTags, params: [id: product.id])
            return
        }
        def product = Product.get(productId)
        if (!product) {
            flash.message = "没有找到该产品"
            redirect(action: "list")
            return
        } else {
            def newTag = Tag.findAll().find { tag ->
                tag.value == value
            }
            if (!newTag) {
                newTag = new Tag(value: value)
            }
            if (newTag.products?.contains(product)) {
                flash.message = "标签已关联"
                redirect(action: editTags, params: [id: product.id])
                return
            }
            newTag.addToProducts product
            if (!newTag.hasErrors() && newTag.save(flush: true)) {
                flash.message = "标签已保存"
                redirect(action: editTags, params: [id: product.id])
                return
            }
            else {
                flash.message = "保存失败"
                redirect(action: editTags, params: [productInstance: product, tags: product.tags])
                return
            }
        }
    }

    def editTags = {
        def productInstance = Product.get(params.id)
        if (!productInstance) {
            flash.message = "没有找到该产品"
            redirect(action: "list")
        } else {
            def tags = productInstance.tags
            return [productInstance: productInstance, tags: tags]
        }
    }

    def editSchedule = {
        ILunchUserDetails user = springSecurityService.principal
        def area = DistributionArea.get(user.areaId)
        def productInstance = Product.get(params.id)
        def today = new Date()
        today.clearTime()
        if (!productInstance) {
            flash.message = "没有找到该产品"
            redirect(action: "list")
        }
        else {
            def schedules = productInstance.productAreaPriceSchedules.findAll {s ->
                s.status == 0 && (s.toDate == null || s.toDate >= today) && s.area == area
            }
            def groupedSchedules = schedules.groupBy {schedule ->
                schedule.area
            }
            return [productInstance: productInstance, schedules: groupedSchedules, user: user]
        }
    }

    def addNewSchedule = {
        def areaId = params.areaId
        def productId = params.productId
        def area = DistributionArea.get(areaId)
        def product = Product.get(productId)
        def oldProductId = params.oldProductId

        if (!area) {
            flash.message = "无法找到该地区"
            redirect(action: editSchedule, params: [id: oldProductId])
            return
        }
        if (!product) {
            flash.message = "无法找到该产品"
            redirect(action: editSchedule, params: [id: oldProductId])
            return
        }

        def price = params.price
        if (!price || (price as float) < 0.0f) {
            flash.message = "价格不能为空且必须大于零"
            redirect(action: editSchedule, params: [id: oldProductId])
            return
        }

        def quantity = params.quantity as int
        if (!quantity || (quantity as int) < 0) {
            flash.message = "库存不能为空且必须大于零"
            redirect(action: editSchedule, params: [id: oldProductId])
            return
        }

        if (params.fromDate > params.toDate) {
            flash.message = "起始日期不能大于结束日期"
            redirect(action: editSchedule, params: [id: oldProductId])
            return
        }
        if (quantity <= 0) {
            flash.message = "总量不能小于1"
            redirect(action: editSchedule, params: [id: oldProductId])
            return
        }

        def schedule = new ProductAreaPriceSchedule(quantity: quantity, fromDate: params.fromDate, toDate: params.toDate, product: product, area: area, price: price)
        product.addToProductAreaPriceSchedules schedule

        if (!schedule.hasErrors() && schedule.save(flush: true)) {
            flash.message = schedule.toString() + "已保存"
            redirect(action: editSchedule, params: [id: schedule.product.id])
            return
        }
        else {
            redirect(action: editSchedule, params: [id: schedule.product.id])
            return
        }

        redirect(action: editSchedule, params: [id: oldProductId])
        return
    }

    def deleteSchedule = {
        def scheduleInstance = ProductAreaPriceSchedule.get(params.id)


        if (scheduleInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (scheduleInstance.version > version) {
                    scheduleInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'distributionPoint.label', default: '排菜安排')] as Object[], "Another user has updated this DistributionPoint while you were editing")
                    redirect(action: "editSchedule", params: [id: scheduleInstance.product.id])
                    return
                }
            }
            scheduleInstance.status = ProductAreaPriceSchedule.DELETED
            if (!scheduleInstance.hasErrors() && scheduleInstance.save(flush: true)) {
                flash.message = scheduleInstance.toString() + "已被删除"
                redirect(action: editSchedule, params: [id: scheduleInstance.product.id])
            }
            else {
                redirect(action: editSchedule, params: [id: scheduleInstance.product.id])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'distributionPoint.label', default: '排菜安排'), params.id])}"
            redirect(action: "list")
        }
    }

    def updateSchedule = {
        def scheduleInstance = ProductAreaPriceSchedule.get(params.id)
        def remain = params.remain as int
        def quantity = params.quantity as int
        def fromDate = params.fromDate
        def toDate = params.toDate

        if (fromDate > toDate) {
            flash.message = "起始日期不能大于结束日期"
            redirect(action: editSchedule, params: [id: scheduleInstance.product.id])
            return
        }
        if (quantity <= 0) {
            flash.message = "总量不能小于1"
            redirect(action: editSchedule, params: [id: scheduleInstance.product.id])
            return
        }

        if (remain > quantity) {
            flash.message = "剩余数量不能大于总量"
            redirect(action: editSchedule, params: [id: scheduleInstance.product.id])
            return
        }
        if (scheduleInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (scheduleInstance.version > version) {
                    scheduleInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'distributionPoint.label', default: '排菜安排')] as Object[], "Another user has updated this DistributionPoint while you were editing")
                    redirect(action: "editSchedule", params: [id: scheduleInstance.product.id])
                    return
                }
            }
            scheduleInstance.properties = params
            if (!scheduleInstance.hasErrors() && scheduleInstance.save(flush: true)) {
                flash.message = scheduleInstance.toString() + "已经保存"
                redirect(action: editSchedule, params: [id: scheduleInstance.product.id])
            }
            else {
                redirect(action: editSchedule, params: [id: scheduleInstance.product.id])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'distributionPoint.label', default: '排菜安排'), params.id])}"
            redirect(action: "list")
        }
    }

    def update = {
        def productInstance = Product.get(params.id)

        def f = request.getFile('image')
        if (!f.empty) {
            String location = grailsApplication.config.cn.ilunch.product.image.location
            def newFile = new File(location + "/${params.name}" + ".jpg")
            f.transferTo(newFile)
        }

        if (productInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (productInstance.version > version) {

                    productInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'location.label', default: 'Location')] as Object[], "Another user has updated this Location while you were editing")
                    render(view: "edit", model: [productInstance: productInstance])
                    return
                }
            }
            productInstance.properties = params
            if (!productInstance.hasErrors() && productInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'product.label', default: 'product'), productInstance.id])}"
                redirect(action: "list")
            }
            else {
                render(view: "edit", model: [productInstance: productInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'product.label', default: 'product'), params.id])}"
            redirect(action: "list")
        }
    }
}