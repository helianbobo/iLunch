package cn.ilunch.domain

class Order {

  static hasMany = [orderItems:OrderItem, appliedPriceRule:PriceRule]

  static constraints = {
  }
}
