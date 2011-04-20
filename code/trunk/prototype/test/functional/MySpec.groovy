import it.tika.test.HttpFunctionalSpec

class MySpec extends HttpFunctionalSpec {

    String getAppName(){
        String result = 'prototype'
        if(config){
            result = config.app.name
        }
        return result
    }

    def "主菜列表"() {

        when: "页面读取完毕,向后台发送ajax数据请求"

        get(path) { response, json ->

            log.info(json)

            json.data.each {mainDish->
                assert mainDish.name != ""
            }

        }

        then: "后台返回json格式的数据"

        where:
        path = "/${appName}/api/maindish"

    }


}
