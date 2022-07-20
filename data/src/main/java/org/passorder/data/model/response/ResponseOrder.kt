package org.passorder.data.model.response

import com.google.gson.annotations.SerializedName
import org.passorder.domain.entity.*

data class ResponseOrder(
    val requests: String,
    @SerializedName("user_nickname")
    val nickName: String?,
    val price: Int,
    val phone: String,
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("table_number")
    val tableNumber: Int,
    @SerializedName("method_of_payment")
    val methodOfPayment: Int,
    val number: Int,
    val status: Int,
    val identifier: String,
    val menus: List<ResponseMenu>,
    @SerializedName("used_coupons")
    val usedCoupons: List<ResponseCoupon>,
) {
    fun toOrder(): Order {
        val payStatus = Pay.stringOf(methodOfPayment)
        return Order(requests, nickName, price, phone, createdDate, tableNumber, methodOfPayment, number, status, identifier, menus.map { it.toMenu() }, usedCoupons.map { it.toCoupon() }, payStatus)
    }

    data class ResponseMenu(
        val count: Int,
        val name: String,
        val price: Int,
        val options: List<ResponseOption>
    ) {
        fun toMenu(): Menu {
            return Menu(count, name, price, options.map { it.toOption() })
        }

        data class ResponseOption(
            val name: String,
            val price: Int
        ) {
            fun toOption(): Option {
                return Option(name, price)
            }
        }
    }

    data class ResponseCoupon(
        @SerializedName("adjustments_target")
        val adjustmentsTarget: Int,
        @SerializedName("benefit")
        val benefit: Int,
        @SerializedName("coupon_name")
        val couponName: String,
        @SerializedName("owned_by")
        val ownedBy: Int,
    ) {
        fun toCoupon() = Coupon(adjustmentsTarget, benefit, couponName, ownedBy)
    }

    enum class Pay(
        val payNo: Int,
        val payStatue: String
    ) {
        UNKNOWN(-99, "known"),
        CARD(0, "신용카드"),
        SAMSUNG_PAY(1, "삼성페이"),
        KAKAO_PAY(2, "카카오페이"),
        PASS_MONEY(3, "패스머니"),
        CASH(4, "캐시"),
        ONLY_POINT(5, "포인트"),
        ONLY_COUPON(6, "쿠폰");

        companion object {
            fun stringOf(english: Int): String {
                return values().find { it.payNo == english }?.payStatue
                    ?: throw IllegalArgumentException()
            }
        }
    }
}