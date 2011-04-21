package cn.ilunch.service

import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUserDetailsService
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.security.core.authority.GrantedAuthorityImpl
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import cn.ilunch.domain.Person
import cn.ilunch.security.ILunchUserDetails

class ILunchUserDetailsService implements GrailsUserDetailsService {

    static final List NO_ROLES = [new GrantedAuthorityImpl(SpringSecurityUtils.NO_ROLE)]

    UserDetails loadUserByUsername(String phoneNumber, boolean loadRoles)
    throws UsernameNotFoundException {
        return loadUserByUsername(phoneNumber)
    }

    UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        Person.withTransaction { status ->
            Person person = Person.findByCellNumber(phoneNumber)
            if (!person) throw new UsernameNotFoundException('User not found', phoneNumber)

            def authorities = person.getAuthorities().collect {new GrantedAuthorityImpl(it.authority)}

            def ilunchUser = new ILunchUserDetails(person.cellNumber, person.password, person.enabled,
                    !person.accountExpired, !person.passwordExpired,
                    !person.accountLocked, authorities ?: NO_ROLES, person)
            return ilunchUser
        }
    }
}