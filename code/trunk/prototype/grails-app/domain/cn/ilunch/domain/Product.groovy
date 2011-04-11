package cn.ilunch.domain

class Product {

    String name

    String originalImageUrl
    String detailImageUrl
    String largeImageUrl
    String mediumImageUrl
    String smallImageUrl

    static constraints = {
        originalImageUrl(nullable: true)
        detailImageUrl(nullable: true)
        largeImageUrl(nullable: true)
        mediumImageUrl(nullable: true)
        smallImageUrl(nullable: true)
    }
}
