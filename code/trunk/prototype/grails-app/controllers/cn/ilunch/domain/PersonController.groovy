package cn.ilunch.domain

import grails.converters.JSON

class PersonController {

    def springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def loggedInUserPreference = {
        if(springSecurityService.isLoggedIn()){
            def id = springSecurityService.currentUser.id
            forward(action: 'preference', params:[id:id])
        }else{
//            response.status = HttpServletResponse.SC_UNAUTHORIZED
			render([error: "not logged on"] as JSON)
        }

    }

    def preference = {
        def customerId = params.id
        def customer = Customer.get(customerId)

        if (!customer) {
            forward(controller: "exception", action: "entityNotFound", params: [id: customerId, entityName: Customer])
            return
        }
        def customerArea = customer.primaryBuilding.distributionPoint.area
        def customerBuilding = customer.primaryBuilding

        render(contentType: "text/json") {
            id=customer.id
            nickname = customer.name
            phoneNumber = customer.cellNumber
            points = customer.pointBalance
            distributionArea = [
                    name: customerArea.name,
                    id: customerArea.id,
                    arealLongitude: customerArea.longitude,
                    areaLatitude: customerArea.latitude
            ]
            building = [
                    id: customerBuilding.id,
                    name:customerBuilding.name,
                    longitude: customerBuilding.longitude,
                    latitude: customerBuilding.latitude
            ]
        }
    }

    def saveCart = {
        session.putValue("cartInfo", params.cartInfo)
		render "{'status':'OK'}"
    }

 def cart = {
     if(!session.getValue("cartInfo")){
        forward(controller: "exception", action: "cartNotFound")
                return
     }
            render([text:session.getValue("cartInfo"), contentType: 'text/plain'])
        
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [personInstanceList: Person.list(params), personInstanceTotal: Person.count()]
    }

    def create = {
        def personInstance = new Person()
        personInstance.properties = params
        return [personInstance: personInstance]
    }

    def save = {
        def personInstance = new Person(params)
        if (personInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'person.label', default: 'Person'), personInstance.id])}"
            redirect(action: "show", id: personInstance.id)
        }
        else {
            render(view: "create", model: [personInstance: personInstance])
        }
    }

    def show = {
        def personInstance = Person.get(params.id)
        if (!personInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
            redirect(action: "list")
        }
        else {
            [personInstance: personInstance]
        }
    }

    def edit = {
        def personInstance = Person.get(params.id)
        if (!personInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [personInstance: personInstance]
        }
    }

    def update = {
        def personInstance = Person.get(params.id)
        if (personInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (personInstance.version > version) {

                    personInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'person.label', default: 'Person')] as Object[], "Another user has updated this Person while you were editing")
                    render(view: "edit", model: [personInstance: personInstance])
                    return
                }
            }
            personInstance.properties = params
            if (!personInstance.hasErrors() && personInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'person.label', default: 'Person'), personInstance.id])}"
                redirect(action: "show", id: personInstance.id)
            }
            else {
                render(view: "edit", model: [personInstance: personInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def personInstance = Person.get(params.id)
        if (personInstance) {
            try {
                personInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
            redirect(action: "list")
        }
    }
}
