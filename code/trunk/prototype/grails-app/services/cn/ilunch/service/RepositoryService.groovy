package cn.ilunch.service

import cn.ilunch.exception.EntityNotFoundException
import cn.ilunch.domain.ProductAreaPriceSchedule

class RepositoryService {

    static transactional = true
    def priceService

    def reduce(product, area, date, quantity) {
        def schedule = priceService.queryProductSchedule(product, area, date)[0]
        if (!schedule)
            throw new EntityNotFoundException([entity: ProductAreaPriceSchedule, product: product, area: area, date: date])

        if(schedule.remain - quantity < 0)
            throw new NotEnoughProductException()

        schedule.remain -= quantity

        schedule.save()
    }
}
