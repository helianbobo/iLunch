package cn.ilunch.domain

class DistributionPoint extends Location{

    String name

    DistributionArea area
    Kitchen kitchen
    List buildings

    static hasMany = [buildings:Building]

    static constraints = {
        status(min: 0,max: 1)
    }
}
