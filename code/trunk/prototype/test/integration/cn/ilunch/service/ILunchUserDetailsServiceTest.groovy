package cn.ilunch.service

/**
 * Created by IntelliJ IDEA.
 * User: janexie
 * Date: 11-4-16
 * Time: 下午4:48
 * To change this template use File | Settings | File Templates.
 */
class ILunchUserDetailsServiceTest extends GroovyTestCase{
    def iLunchUserDetailsService = new ILunchUserDetailsService()

    void testLoadUser() {
        def user = iLunchUserDetailsService.loadUserByUsername("13764511823")
        assertEquals "13764511823",user.cellNumber
    }
}
