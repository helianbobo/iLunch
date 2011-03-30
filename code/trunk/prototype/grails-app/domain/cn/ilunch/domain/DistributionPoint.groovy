package cn.ilunch.domain

class DistributionPoint extends Location{

    String name

    DistributionPointGroup group

    static hasMany = [buildings:Building]

    static constraints = {
    }
}
