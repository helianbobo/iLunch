import cn.ilunch.domain.Role
import org.springframework.security.core.userdetails.User
import cn.ilunch.domain.UserRole
import cn.ilunch.domain.Person
import cn.ilunch.domain.Building
import cn.ilunch.domain.Customer
import cn.ilunch.domain.Manager
import cn.ilunch.domain.Kitchen

class BootStrap {

    def fixtureLoader

    def springSecurityService

    def init = { servletContext ->
        environments {
            test {
                loadTestData()
            }
            development {
                loadData()
            }
            leo {
                loadTestData()
            }
        }

    }
    def destroy = {
    }

    void loadData() {
        fixtureLoader.load('initData')
    }

    void loadTestData() {
        println "loading test data--------------------------"
        fixtureLoader.load('initData')
    }
}
