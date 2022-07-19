package org.passorder.domain.entity

data class SetOrder(
    val page: Int,
    val limit: Int,
    val filter: Int,
    val start: String?,
    val end: String?
)

data class Order(
    val requests: String,
    val nickName: String?,
    val price: Int,
    val phone: String,
    val createdDate: String,
    val tableNumber: Int,
    var methodOfPayment: Int,
    val number: Number,
    val status: Int,
    val identifier: String,
    val menus: List<Menu>,
    val usedCoupons: List<Coupon>,
    val payStatus: String
)

data class Menu(
    val count: Int,
    val name: String,
    val price: Int,
    val options: List<Option>
)

data class Option(
    val name: String,
    val price: Int
)

data class Coupon(
    val adjustmentsTarget: Int,
    val benefit: Int,
    val couponName: String,
    val ownedBy: Int,
)