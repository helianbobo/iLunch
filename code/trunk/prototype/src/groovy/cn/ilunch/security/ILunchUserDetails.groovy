package cn.ilunch.security

import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUser
import org.springframework.security.core.GrantedAuthority
import cn.ilunch.domain.Person
import cn.ilunch.domain.Customer

/**
 * Created by IntelliJ IDEA.
 * User: janexie
 * Date: 11-4-16
 * Time: 下午4:10
 * To change this template use File | Settings | File Templates.
 */
class ILunchUserDetails extends GrailsUser {
    long id
    String name
    String cellNumber
    int points
    String areaName
    Long areaId
    double longitude
    double latitude
    long buildId
    double buildingLongitude
    double buildingLatitude
    String buildingName

    ILunchUserDetails(String cellNumber, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<GrantedAuthority> authorities, Person person) {
        super(cellNumber, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities,person.id)
        this.cellNumber = cellNumber

        if(person instanceof Customer)
            this.buildId = person.primaryBuilding?.id
        this.areaId = person.area?.id
    }
}
