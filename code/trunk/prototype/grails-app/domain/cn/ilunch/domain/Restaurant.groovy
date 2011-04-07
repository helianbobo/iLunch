package cn.ilunch.domain

class Restaurant {

    String name
    Manager manager

    static hasMany = [distributionPointGroups:DistributionPointGroup]

    static constraints = {
    }
}
