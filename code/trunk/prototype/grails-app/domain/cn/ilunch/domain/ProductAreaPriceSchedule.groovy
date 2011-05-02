package cn.ilunch.domain

class ProductAreaPriceSchedule {
    static int INUSE = 0
    static int DELETED = 1

    int status
    Product product
    Date fromDate
    Date toDate
    DistributionArea area
    BigDecimal price

    int quantity
    int remain

    static constraints = {
        toDate(nullable:true)
        price(min:new BigDecimal("0"))
        quantity(min:1)
        remain(min:0)
        status(min: 0, max: 1)
    }

    String dateInfo(){
        "从"+fromDate.format("yyyy-MM-dd")+"到"+fromDate.format("yyyy-MM-dd")
    }

    String toString(){
        "${area.name}的${product.name}在${fromDate.format('yyyy-MM-dd')}到${toDate==null?"未来":toDate.format('yyyy-MM-dd')}的排菜安排"
    }
}
