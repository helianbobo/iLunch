package cn.ilunch.domain

class Person {
    String cellNumber
    String name
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

    static hasMany = [roles: Role]
	static belongsTo = Role

	static constraints = {
		cellNumber blank: false, unique: true
		password blank: false
		name nullable:true
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}
}
