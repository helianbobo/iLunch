import cn.ilunch.domain.Building
import cn.ilunch.domain.DistributionPoint
import cn.ilunch.domain.Customer
import cn.ilunch.domain.DistributionArea
import cn.ilunch.domain.Kitchen
import cn.ilunch.domain.Manager
import cn.ilunch.domain.MainDish
import cn.ilunch.domain.SideDish

fixture {



    'person_chenkai'(Manager){
        name = '陈凯'
        cellNumber = '1860018600'
    }

    'da_zhangjiang'(DistributionArea){
        name = "张江高科"
        latitude = '31.204212'
        longitude = '121.600199'
    }

    "kitchen_zhangjiang"(Kitchen){
        name = "张江厨房"
        manager = person_chenkai

    }



    'dp_lingyang'(DistributionPoint){
        name = "凌阳大厦大堂"
        latitude = '31.205093'
        longitude = '121.598160'
        area = da_zhangjiang
        kitchen = kitchen_zhangjiang

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

    'md_curryfish'(MainDish){
        name = '咖喱鱼'
        flavor = '东南亚'
    }

    'sd_doujiao'(SideDish){
        name = '豆角'
        flavor = '东北'
    }

}