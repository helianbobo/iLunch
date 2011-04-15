package it.tika.test

import spock.lang.Specification
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import static groovyx.net.http.ContentType.JSON
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.apache.log4j.Logger


class HttpFunctionalSpec extends Specification {

    def log = Logger.getLogger(this.getClass().getName())
    def config = ConfigurationHolder.config

    protected String getBaseUrl() {

        String result = 'http://localhost:8080'
        if(config)
            result = config.grails.serverURL

        return result

    }

    protected void get(String path, Closure successClosure) {

        def http = new HTTPBuilder(baseUrl)

        http.request(Method.valueOf("GET"), JSON) {req->
            uri.path = path
            headers.'User-Agent' = 'Mozilla/5.0'
            response.success = successClosure
            response.failure = { resp ->
                def message = "Unexpected error: ${resp.statusLine.statusCode} : ${resp.statusLine.reasonPhrase}"
                log.error message
                throw new Exception(message);
            }
        }

    }

}
