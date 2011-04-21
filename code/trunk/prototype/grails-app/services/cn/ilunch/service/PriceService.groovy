package cn.ilunch.service

import cn.ilunch.domain.ProductAreaPriceSchedule
import org.hibernate.criterion.Order

class PriceService {

    static transactional = true

    //return -1 if not found

    def queryProductSchedule(def product, def area, def date = null) {
        if (date)
            date.clearTime()

        def c = ProductAreaPriceSchedule.createCriteria()
        def result = c.list {
            and {
                eq('area', area)
                eq('product', product)
                if (date) {
                    or {
                        and {
                            le('fromDate', date)
                            ge('toDate', date)
                        }
                        and {
                            isNull('toDate')
                            le('fromDate', date)
                        }
                    }
                } else {
                    def today = new Date()
                    today.clearTime()
                    or {
                        ge('toDate' ,today)
                        isNull('toDate')
                    }
                    order('fromDate','asc')
                }
            }
        }
        return result
    }
}
