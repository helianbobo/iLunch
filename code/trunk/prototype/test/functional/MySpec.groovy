import it.tika.test.HttpFunctionalSpec

class MySpec extends HttpFunctionalSpec {

    def "主菜列表"() {

        when: "页面读取完毕,向后台发送ajax数据请求"

        get(path) { response, json ->
            json.each {mainDish->
                assert mainDish.name != ""
            }

        }

        then: "后台返回json格式的数据"

        where:
        path = '/person/show/2'
        expectJson = '[{name:"咖喱鱼"}]'

    }


}
