package cn.ilunch.domain

class DistributionAreaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [distributionAreaInstanceList: DistributionArea.list(params), distributionAreaInstanceTotal: DistributionArea.count()]
    }

    def create = {
        def distributionAreaInstance = new DistributionArea()
        distributionAreaInstance.properties = params
        return [distributionAreaInstance: distributionAreaInstance]
    }

    def save = {
        def distributionAreaInstance = new DistributionArea(params)
        if (distributionAreaInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'distributionArea.label', default: 'distributionArea'), distributionAreaInstance.id])}"
            redirect(action: "show", id: distributionAreaInstance.id)
        }
        else {
            render(view: "create", model: [distributionAreaInstance: distributionAreaInstance])
        }
    }

    def show = {
        def distributionAreaInstance = DistributionArea.get(params.id)
        if (!distributionAreaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'distributionArea.label', default: 'distributionArea'), params.id])}"
            redirect(action: "list")
        }
        else {
            [distributionAreaInstance: distributionAreaInstance]
        }
    }

    def edit = {
        def distributionAreaInstance = DistributionArea.get(params.id)
        if (!distributionAreaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'distributionArea.label', default: 'distributionArea'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [distributionAreaInstance: distributionAreaInstance]
        }
    }

    def update = {
        def distributionAreaInstance = DistributionArea.get(params.id)
        if (distributionAreaInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (distributionAreaInstance.version > version) {
                    
                    distributionAreaInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'distributionArea.label', default: 'distributionArea')] as Object[], "Another user has updated this distributionArea while you were editing")
                    render(view: "edit", model: [distributionAreaInstance: distributionAreaInstance])
                    return
                }
            }
            distributionAreaInstance.properties = params
            if (!distributionAreaInstance.hasErrors() && distributionAreaInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'distributionArea.label', default: 'distributionArea'), distributionAreaInstance.id])}"
                redirect(action: "show", id: distributionAreaInstance.id)
            }
            else {
                render(view: "edit", model: [distributionAreaInstance: distributionAreaInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'distributionArea.label', default: 'distributionArea'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def distributionAreaInstance = DistributionArea.get(params.id)
        if (distributionAreaInstance) {
            try {
                distributionAreaInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'distributionArea.label', default: 'distributionArea'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'distributionArea.label', default: 'distributionArea'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'distributionArea.label', default: 'distributionArea'), params.id])}"
            redirect(action: "list")
        }
    }
}
