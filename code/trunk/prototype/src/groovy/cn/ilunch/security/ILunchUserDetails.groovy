package cn.ilunch.security

import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUser
import org.springframework.security.core.GrantedAuthority
import cn.ilunch.domain.Person
import cn.ilunch.domain.Customer
import cn.ilunch.domain.Manager

/**
 * Created by IntelliJ IDEA.
 * User: janexie
 * Date: 11-4-16
 * Time: 下午4:10
 * To change this template use File | Settings | File Templates.
 */
class ILunchUserDetails extends GrailsUser {
    Long id
    String name
    String cellNumber
    int points
    String areaName
    Long areaId
    double longitude
    double latitude
    Long buildId
    double buildingLongitude
    double buildingLatitude
    String buildingName
    String authority
    Long kitchenId

    ILunchUserDetails(String cellNumber, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<GrantedAuthority> authorities, Person person) {
        super(cellNumber, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities, person.id)
        this.cellNumber = cellNumber
        this.id = person.id
        if (person instanceof Manager) {
            this.kitchenId = person.kitchen.id
        }
        this.authority = authorities*.authority.join(",")
        if (person instanceof Customer) {
            this.buildId = person.primaryBuilding?.id
        }
        this.areaId = person.area?.id
    }
}
