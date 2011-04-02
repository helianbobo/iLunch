import cn.ilunch.domain.Building
import cn.ilunch.domain.DistributionPoint
import cn.ilunch.domain.DistributionPointGroup
import cn.ilunch.domain.Customer

fixture {

    'dpg_zhangjiang'(DistributionPointGroup){
        name = "张江高科"
        latitude = '31.204212'
        longitude = '121.600199'
    }


    'dp_lingyang'(DistributionPoint){
        name = "凌阳大厦大堂"
        latitude = '31.205093'
        longitude = '121.598160'
        group = dpg_zhangjiang

    }

    'building_lingyang'(Building){
        name = '凌阳大厦'
        latitude = '31.205093'
        longitude = '121.598160'
        distributionPoint = dp_lingyang
    }

    'person_liuchao'(Customer){
        name = '刘超'
        cellNumber = '18621077586'
        primaryBuilding = building_lingyang
        buildings = [building_lingyang]
    }

}