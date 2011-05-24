import grails.plugins.springsecurity.SecurityConfigType

// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [html: ['text/html', 'application/xhtml+xml'],
        xml: ['text/xml', 'application/xml'],
        text: 'text/plain',
        js: 'text/javascript',
        rss: 'application/rss+xml',
        atom: 'application/atom+xml',
        css: 'text/css',
        csv: 'text/csv',
        all: '*/*',
        json: ['application/json', 'text/json'],
        form: 'application/x-www-form-urlencoded',
        multipartForm: 'multipart/form-data'
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// whether to install the java.util.logging bridge for sl4j. Disable for AppEngine!
grails.logging.jul.usebridge = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []

grails.gorm.failOnError = true

grails.org.hibernate
// set per-environment serverURL stem for creating absolute links
environments {
    production {
        grails.serverURL = "http://www.changeme.com"
    }
    development {
        grails.serverURL = "http://localhost:8080/${appName}"
    }
    test {
        grails.serverURL = "http://localhost:8080/${appName}"
    }

}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    appenders {
        console name: 'stdout', layout: pattern(conversionPattern: '%c{2} %m%n')
    }

    error 'org.codehaus.groovy.grails.web.servlet',  //  controllers
            'org.codehaus.groovy.grails.web.pages', //  GSP
            'org.codehaus.groovy.grails.web.sitemesh', //  layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping', // URL mapping
            'org.codehaus.groovy.grails.commons', // core / classloading
            'org.codehaus.groovy.grails.plugins', // plugins
            'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
            'org.springframework',
            'org.hibernate',
            'net.sf.ehcache.hibernate'

    warn 'org.mortbay.log'
}

grails.json.legacy.builder = false

cn.ilunch.exception.code.EntityNotFound = "01"
cn.ilunch.exception.code.ScheduleNotFound = "02"
cn.ilunch.exception.code.CartNotFound = "03"
cn.ilunch.exception.code.NotEnoughPointChange = "04"
cn.ilunch.exception.code.DeprecatedOrder = "05"
cn.ilunch.exception.code.CellphoneNumberInvalid = "06"
cn.ilunch.exception.code.CellphoneNumberRegistered = "07"

cn.ilunch.date.format = 'yyyy-MM-dd'
cn.ilunch.product.image.location = "${userHome}/ilunch/images/products"
// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'cn.ilunch.domain.Person'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'cn.ilunch.domain.UserRole'
grails.plugins.springsecurity.authority.className = 'cn.ilunch.domain.Role'

grails.plugins.springsecurity.userLookup.usernamePropertyName = 'cellNumber'

grails.plugins.springsecurity.securityConfigType = SecurityConfigType.InterceptUrlMap

grails.plugins.springsecurity.interceptUrlMap = [
        '/secure/**': ['ROLE_ADMIN'],
        '/admin.gsp': ['ROLE_ADMIN'],
        '/myorder.gsp': ['IS_AUTHENTICATED_FULLY'],
        '/person/loggedInUserPreference': ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/productOrder/**': ['IS_AUTHENTICATED_FULLY'],
        '/product/**': ['ROLE_MANAGER', 'ROLE_ADMIN'],
        '/order/**': ['ROLE_USER', 'ROLE_ADMIN','ROLE_MANAGER'],
        '/console': ['ROLE_MANAGER', 'ROLE_ADMIN'],
        '/js/**': ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/css/**': ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/images/**': ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/distributionArea/list': ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/product/list*': ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/product/showDetail': ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/person/cart': ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/person/saveCart': ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/person/preference': ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/shipment/**': ['ROLE_ADMIN','ROLE_MANAGER'],
        '/faq.gsp': ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/aboutus.gsp': ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/agreement.gsp': ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/login/**': ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/logout/**': ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/': ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/dataAPI/**': ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/**': ['ROLE_ADMIN']
]

grails.config.locations = [ "classpath:config.properties"]