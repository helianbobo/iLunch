package cn.ilunch.domain

class DistributionPointGroupController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [distributionPointGroupInstanceList: DistributionPointGroup.list(params), distributionPointGroupInstanceTotal: DistributionPointGroup.count()]
    }

    def create = {
        def distributionPointGroupInstance = new DistributionPointGroup()
        distributionPointGroupInstance.properties = params
        return [distributionPointGroupInstance: distributionPointGroupInstance]
    }

    def save = {
        def distributionPointGroupInstance = new DistributionPointGroup(params)
        if (distributionPointGroupInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'distributionPointGroup.label', default: 'DistributionPointGroup'), distributionPointGroupInstance.id])}"
            redirect(action: "show", id: distributionPointGroupInstance.id)
        }
        else {
            render(view: "create", model: [distributionPointGroupInstance: distributionPointGroupInstance])
        }
    }

    def show = {
        def distributionPointGroupInstance = DistributionPointGroup.get(params.id)
        if (!distributionPointGroupInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'distributionPointGroup.label', default: 'DistributionPointGroup'), params.id])}"
            redirect(action: "list")
        }
        else {
            [distributionPointGroupInstance: distributionPointGroupInstance]
        }
    }

    def edit = {
        def distributionPointGroupInstance = DistributionPointGroup.get(params.id)
        if (!distributionPointGroupInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'distributionPointGroup.label', default: 'DistributionPointGroup'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [distributionPointGroupInstance: distributionPointGroupInstance]
        }
    }

    def update = {
        def distributionPointGroupInstance = DistributionPointGroup.get(params.id)
        if (distributionPointGroupInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (distributionPointGroupInstance.version > version) {
                    
                    distributionPointGroupInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'distributionPointGroup.label', default: 'DistributionPointGroup')] as Object[], "Another user has updated this DistributionPointGroup while you were editing")
                    render(view: "edit", model: [distributionPointGroupInstance: distributionPointGroupInstance])
                    return
                }
            }
            distributionPointGroupInstance.properties = params
            if (!distributionPointGroupInstance.hasErrors() && distributionPointGroupInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'distributionPointGroup.label', default: 'DistributionPointGroup'), distributionPointGroupInstance.id])}"
                redirect(action: "show", id: distributionPointGroupInstance.id)
            }
            else {
                render(view: "edit", model: [distributionPointGroupInstance: distributionPointGroupInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'distributionPointGroup.label', default: 'DistributionPointGroup'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def distributionPointGroupInstance = DistributionPointGroup.get(params.id)
        if (distributionPointGroupInstance) {
            try {
                distributionPointGroupInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'distributionPointGroup.label', default: 'DistributionPointGroup'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'distributionPointGroup.label', default: 'DistributionPointGroup'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'distributionPointGroup.label', default: 'DistributionPointGroup'), params.id])}"
            redirect(action: "list")
        }
    }
}
