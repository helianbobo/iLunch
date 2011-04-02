package cn.ilunch.domain

class Customer extends Person {

  Building building

  static hasMany = [orders:Order]


  static constraints = {
  }
}
