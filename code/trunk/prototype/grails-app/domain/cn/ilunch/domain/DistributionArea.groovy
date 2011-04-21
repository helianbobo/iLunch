package cn.ilunch.domain

class DistributionArea extends Location{

    String name
    List distributionPoints

    static hasMany = [
            distributionPoints:DistributionPoint,
            kitchens:Kitchen]

    static constraints = {
        status(min: 0,max: 1)
    }
}
