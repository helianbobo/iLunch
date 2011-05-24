package cn.ilunch.service

import cn.ilunch.domain.Shipment

class ShipmentService {

    static transactional = true

    def ship(def shipments) {
        shipments.each {shipment ->
            shipment.status = Shipment.SHIPPED
            shipment.save()
        }
    }
}
