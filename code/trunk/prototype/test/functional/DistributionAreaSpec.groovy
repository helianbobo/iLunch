//
//import it.tika.test.HttpFunctionalSpec
//
//class DistributionAreaSpec extends HttpFunctionalSpec {
//
//    String getAppName(){
//        String result = 'prototype'
//        if(config){
//            result = config.app.name
//        }
//        return result
//    }
//
//    def "配送地区列表"() {
//
//        when: "页面读取完毕,向后台发送ajax数据请求"
//
//        get(path) { response, json ->
//
//            log.info(json)
//
//            json.areas.each {area->
//                assert area.id != ""
//                assert area.name != ""
//            }
//
//        }
//
//        then: "后台返回json格式的数据"
//
//        where:
//        path = "/${appName}/distributionArea/list"
//
//    }
//
//
//}
