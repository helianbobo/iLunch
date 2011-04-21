package cn.ilunch.domain

class Building extends Location{

    String name
    DistributionPoint distributionPoint

    static constraints = {
        status(min: 0,max: 1)
    }
}
