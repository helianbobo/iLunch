class SpockFunctionalTestGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.3.6 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def author = "Liu Chao"
    def authorEmail = "liuchao36@gmail.com"
    def title = "Spock Functional Test Plugin"
    def description = '''\\
Spock Functional Test Plugin for internal use.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/spock-functional-test"


}
