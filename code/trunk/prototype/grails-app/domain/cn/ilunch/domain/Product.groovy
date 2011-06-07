package cn.ilunch.domain

class Product {
    static int INUSE = 0
    static int DELETED = 1

    String name
    String story
    int status//in use or deleted
    String originalImageUrl
    String detailImageUrl
    String largeImageUrl
    String mediumImageUrl
    String smallImageUrl
    static hasMany = [productAreaPriceSchedules: ProductAreaPriceSchedule, tags:Tag]

    List productAreaPriceSchedules
    List tags
    static constraints = {
        originalImageUrl(nullable: true)
        detailImageUrl(nullable: true)
        largeImageUrl(nullable: true)
        mediumImageUrl(nullable: true)
        smallImageUrl(nullable: true)
        status(min: 0, max: 1)
        productAreaPriceSchedules(nullable: true)
        name(nullable: true)
    }

    def statusDesc = {
        switch (status) {
            case INUSE:
                return "使用中";
            case DELETED:
                return "已删除";
            default:
                return "未知";
        }
    }



    def getScheduleDates() {
        if (!productAreaPriceSchedules)
            return []

        def dates = []
        productAreaPriceSchedules.each {productAreaPriceSchedule ->
            if(productAreaPriceSchedule)
                dates << [fromDate: productAreaPriceSchedule.fromDate, toDate: productAreaPriceSchedule.toDate]
        }
        dates
    }


}
