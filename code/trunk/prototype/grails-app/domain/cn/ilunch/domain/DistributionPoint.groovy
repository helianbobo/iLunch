package cn.ilunch.domain

class DistributionPoint extends Location{

    String name

    DistributionArea area
    Kitchen kitchen

    static hasMany = [buildings:Building]

    static constraints = {
    }
}
