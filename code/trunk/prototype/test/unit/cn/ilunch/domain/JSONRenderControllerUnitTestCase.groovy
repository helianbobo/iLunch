package cn.ilunch.domain

import groovy.xml.StreamingMarkupBuilder
import grails.util.JSonBuilder
import grails.test.ControllerUnitTestCase
import grails.web.JSONBuilder
import grails.converters.JSON

/**
 * Created by IntelliJ IDEA.
 * User: lsha6086
 * Date: 4/14/11
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
class JSONRenderControllerUnitTestCase extends ControllerUnitTestCase{
    ObjectGraphBuilder builder

    protected void setUp() {
        super.setUp()
        builder  = new ObjectGraphBuilder()
        builder.classNameResolver = "cn.ilunch.domain"
    }

    def fixJsonRender(clazz) {
        clazz.metaClass.render = {Map map, Closure c ->
            renderArgs.putAll(map)
            switch (map["contentType"]) {
                case null:
                    break

                case "application/xml":
                case "text/xml":
                    def b = new StreamingMarkupBuilder()
                    if (map["encoding"]) b.encoding = map["encoding"]

                    def writable = b.bind(c)
                    delegate.response.outputStream << writable
                    break
                case "text/json":
                    JSON json = new JSONBuilder().build(c)
                    delegate.response.writer.write(json.toString())
                    delegate.response.writer.flush()
                default:
                    println "Nothing"
                    break
            }
        }
    }
}
