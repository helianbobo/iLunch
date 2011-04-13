import cn.ilunch.test.HttpFunctionalSpec

class MySpec extends HttpFunctionalSpec{

    def "主菜列表"(){

        when: "页面读取完毕,向后台发送ajax数据请求"

        def response = get(url)

        then: "后台返回json格式的数据"
        response == '[{name:"咖喱鱼"}]'

        where:
        url = 'http://localhost:8080/prototype/mainDish/show'

    }


}
