package cn.ilunch.domain

class MainDishController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [mainDishInstanceList: MainDish.list(params), mainDishInstanceTotal: MainDish.count()]
    }

    def create = {
        def mainDishInstance = new MainDish()
        mainDishInstance.properties = params
        return [mainDishInstance: mainDishInstance]
    }

    def save = {
        def mainDishInstance = new MainDish(params)
        if (mainDishInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'mainDish.label', default: 'MainDish'), mainDishInstance.id])}"
            redirect(action: "show", id: mainDishInstance.id)
        }
        else {
            render(view: "create", model: [mainDishInstance: mainDishInstance])
        }
    }

    def show = {
        def mainDishInstance = MainDish.get(params.id)
        if (!mainDishInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'mainDish.label', default: 'MainDish'), params.id])}"
            redirect(action: "list")
        }
        else {
            [mainDishInstance: mainDishInstance]
        }
    }

    def showPublic = {
        def mainDishInstance = MainDish.get(params.id)
        if (!mainDishInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'mainDish.label', default: 'MainDish'), params.id])}"
            redirect(action: "list")
        }
        else {
            [mainDishInstance: mainDishInstance]
        }
    }

    def edit = {
        def mainDishInstance = MainDish.get(params.id)
        if (!mainDishInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'mainDish.label', default: 'MainDish'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [mainDishInstance: mainDishInstance]
        }
    }

    def update = {
        def mainDishInstance = MainDish.get(params.id)
        if (mainDishInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (mainDishInstance.version > version) {

                    mainDishInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'mainDish.label', default: 'MainDish')] as Object[], "Another user has updated this MainDish while you were editing")
                    render(view: "edit", model: [mainDishInstance: mainDishInstance])
                    return
                }
            }
            mainDishInstance.properties = params
            if (!mainDishInstance.hasErrors() && mainDishInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'mainDish.label', default: 'MainDish'), mainDishInstance.id])}"
                redirect(action: "show", id: mainDishInstance.id)
            }
            else {
                render(view: "edit", model: [mainDishInstance: mainDishInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'mainDish.label', default: 'MainDish'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def mainDishInstance = MainDish.get(params.id)
        if (mainDishInstance) {
            try {
                mainDishInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'mainDish.label', default: 'MainDish'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'mainDish.label', default: 'MainDish'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'mainDish.label', default: 'MainDish'), params.id])}"
            redirect(action: "list")
        }
    }
}
