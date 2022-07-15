package org.passorder.domain.entity

data class Money(
    val methodOfPayment: Int,
    val price: Int,
    val usedCouponBenefit: Int,
    val usedPassorderPoint: Int,
    val usedStoreCouponBenefit: Int,
    val usedStorePoint: Int
)