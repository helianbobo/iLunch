package cn.ilunch.domain

class BuildingController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [buildingInstanceList: Building.list(params), buildingInstanceTotal: Building.count()]
    }

    def create = {
        def buildingInstance = new Building()
        buildingInstance.properties = params
        return [buildingInstance: buildingInstance]
    }

    def save = {
        def buildingInstance = new Building(params)
        if (buildingInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'building.label', default: 'Building'), buildingInstance.id])}"
            redirect(action: "show", id: buildingInstance.id)
        }
        else {
            render(view: "create", model: [buildingInstance: buildingInstance])
        }
    }

    def show = {
        def buildingInstance = Building.get(params.id)
        if (!buildingInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'building.label', default: 'Building'), params.id])}"
            redirect(action: "list")
        }
        else {
            [buildingInstance: buildingInstance]
        }
    }

    def edit = {
        def buildingInstance = Building.get(params.id)
        if (!buildingInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'building.label', default: 'Building'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [buildingInstance: buildingInstance]
        }
    }

    def update = {
        def buildingInstance = Building.get(params.id)
        if (buildingInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (buildingInstance.version > version) {
                    
                    buildingInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'building.label', default: 'Building')] as Object[], "Another user has updated this Building while you were editing")
                    render(view: "edit", model: [buildingInstance: buildingInstance])
                    return
                }
            }
            buildingInstance.properties = params
            if (!buildingInstance.hasErrors() && buildingInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'building.label', default: 'Building'), buildingInstance.id])}"
                redirect(action: "show", id: buildingInstance.id)
            }
            else {
                render(view: "edit", model: [buildingInstance: buildingInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'building.label', default: 'Building'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def buildingInstance = Building.get(params.id)
        if (buildingInstance) {
            try {
                buildingInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'building.label', default: 'Building'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'building.label', default: 'Building'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'building.label', default: 'Building'), params.id])}"
            redirect(action: "list")
        }
    }
}
