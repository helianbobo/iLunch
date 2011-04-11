package cn.ilunch.domain

class Kitchen {

    String name
    Manager manager

    static hasMany = [
            distributionPoints:DistributionPoint

    ]

    static constraints = {
        manager(nullable: true)
    }
}
