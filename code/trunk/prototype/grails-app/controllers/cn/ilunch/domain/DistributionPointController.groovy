package cn.ilunch.domain

class DistributionPointController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [distributionPointInstanceList: DistributionPoint.list(params), distributionPointInstanceTotal: DistributionPoint.count()]
    }

    def create = {
        def distributionPointInstance = new DistributionPoint()
        distributionPointInstance.properties = params
        return [distributionPointInstance: distributionPointInstance]
    }

    def save = {
        def distributionPointInstance = new DistributionPoint(params)
        if (distributionPointInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'distributionPoint.label', default: 'DistributionPoint'), distributionPointInstance.id])}"
            redirect(action: "show", id: distributionPointInstance.id)
        }
        else {
            render(view: "create", model: [distributionPointInstance: distributionPointInstance])
        }
    }

    def show = {
        def distributionPointInstance = DistributionPoint.get(params.id)
        if (!distributionPointInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'distributionPoint.label', default: 'DistributionPoint'), params.id])}"
            redirect(action: "list")
        }
        else {
            [distributionPointInstance: distributionPointInstance]
        }
    }

    def edit = {
        def distributionPointInstance = DistributionPoint.get(params.id)
        if (!distributionPointInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'distributionPoint.label', default: 'DistributionPoint'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [distributionPointInstance: distributionPointInstance]
        }
    }

    def update = {
        def distributionPointInstance = DistributionPoint.get(params.id)
        if (distributionPointInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (distributionPointInstance.version > version) {
                    
                    distributionPointInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'distributionPoint.label', default: 'DistributionPoint')] as Object[], "Another user has updated this DistributionPoint while you were editing")
                    render(view: "edit", model: [distributionPointInstance: distributionPointInstance])
                    return
                }
            }
            distributionPointInstance.properties = params
            if (!distributionPointInstance.hasErrors() && distributionPointInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'distributionPoint.label', default: 'DistributionPoint'), distributionPointInstance.id])}"
                redirect(action: "show", id: distributionPointInstance.id)
            }
            else {
                render(view: "edit", model: [distributionPointInstance: distributionPointInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'distributionPoint.label', default: 'DistributionPoint'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def distributionPointInstance = DistributionPoint.get(params.id)
        if (distributionPointInstance) {
            try {
                distributionPointInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'distributionPoint.label', default: 'DistributionPoint'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'distributionPoint.label', default: 'DistributionPoint'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'distributionPoint.label', default: 'DistributionPoint'), params.id])}"
            redirect(action: "list")
        }
    }
}
