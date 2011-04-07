package cn.ilunch.domain

class DistributionPointGroup extends Location{

    String name
    Restaurant restaurant

    static hasMany = [distributionPoints:DistributionPoint]

    static constraints = {
    }
}
