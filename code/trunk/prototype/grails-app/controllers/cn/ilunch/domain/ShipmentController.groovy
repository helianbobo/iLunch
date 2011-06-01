package cn.ilunch.domain

import cn.ilunch.security.ILunchUserDetails


class ShipmentController {
    def springSecurityService

    def indexQueryByDP = {
        ILunchUserDetails user = springSecurityService.principal
        if (user.authority.contains("ROLE_ADMIN")) {
            def distributionPoints = DistributionPoint.findAllByStatus(DistributionArea.INUSE)
            render(view: "index", model: [distributionPoints: distributionPoints])
            return
        } else {
            def kitchen = Kitchen.findById(user.kitchenId)
            render(view: "index", model: [distributionPoints: kitchen.distributionPoints])
        }
    }

    def indexQueryByArea = {
        ILunchUserDetails user = springSecurityService.principal
        if (user.authority.contains("ROLE_ADMIN")) {
            def areas = DistributionArea.findAllByStatus(DistributionArea.INUSE)
            render(view: "indexQueryByArea", model: [areas: areas])
            return
        } else {
            def kitchen = Kitchen.findById(user.kitchenId)
            render(view: "indexQueryByArea", model: [distributionPoints: [kitchen.area]])
        }
    }

    def listShipmentByDateAndDP = {
        ILunchUserDetails user = springSecurityService.principal
        def dpId = params.dpId
        def dp = null
        if (dpId && dpId != "-1") {
            dp = DistributionPoint.get(dpId)
            println dp.name
        }

        Date fromDate = params.fromDate
        Date toDate = params.toDate
        fromDate.clearTime()
        toDate.clearTime()
        def criteria = Shipment.createCriteria()
        def result = criteria.list {
            eq('status', Shipment.CREATED)
            and {
                ge("shipmentDate", fromDate)
                le("shipmentDate", toDate)
            }
            if (dp) {
                productOrder {
                    eq('distributionPoint', dp)
                }
            }
        }
        render(view: "list", model: [shipments: result])
    }

    def listShipmentByDateAndArea = {
        ILunchUserDetails user = springSecurityService.principal
        def areaId = params.areaId
        def area = null
        if (areaId && areaId != "-1") {
            area = DistributionArea.get(areaId)
            println area.name
        }

        Date fromDate = params.fromDate
        Date toDate = params.toDate
        fromDate.clearTime()
        toDate.clearTime()
        def criteria = Shipment.createCriteria()
        def result = criteria.list {
            eq('status', Shipment.CREATED)
            and {
                ge("shipmentDate", fromDate)
                le("shipmentDate", toDate)
            }
            if (area) {
                productOrder {
                    distributionPoint {
                        eq('area', area)
                    }
                }
            }
        }
        render(view: "list", model: [shipments: result])
    }
}
