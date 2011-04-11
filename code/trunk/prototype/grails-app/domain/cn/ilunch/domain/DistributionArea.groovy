package cn.ilunch.domain

class DistributionArea extends Location{

    String name

    static hasMany = [
            distributionPoints:DistributionPoint,
            kitchens:Kitchen]

    static constraints = {
    }
}
