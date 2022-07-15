package org.passorder.data.model.response

import com.google.gson.annotations.SerializedName
import org.passorder.domain.entity.Money

data class ResponseMoney(
    @SerializedName("method_of_payment")
    val methodOfPayment: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("used_coupon_benefit")
    val usedCouponBenefit: Int,
    @SerializedName("used_passorder_point")
    val usedPassorderPoint: Int,
    @SerializedName("used_store_coupon_benefit")
    val usedStoreCouponBenefit: Int,
    @SerializedName("used_store_point")
    val usedStorePoint: Int
) {
    fun toMoney() : Money {
        return Money(methodOfPayment, price, usedCouponBenefit, usedPassorderPoint, usedStoreCouponBenefit, usedStorePoint)
    }
}