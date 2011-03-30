package cn.ilunch.domain

class DistributionPointGroup extends Location{

    String name

    static hasMany = [distributionPoints:DistributionPoint]

    static constraints = {
    }
}
