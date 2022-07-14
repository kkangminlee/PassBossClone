package org.passorder.boss.util

import org.passorder.boss.R

enum class TakeOut(
    val takeNo: Int,
    val takeStatue: String,
) {
    UNKNOWN(-99, "known"),
    TAKEOUT(-1, "테이크아웃"),
    DINE(0, "매장")
}

enum class OrderStatus(
    val status: Int,
    val orderText: String,
    val background: Int
) {
    WAITING(1, "접수대기", R.drawable.shape_bottom_blue),
    COOKING(2, "조리중", R.drawable.shape_bottom_yellow),
    COOKED(3, "조리완료", R.drawable.shape_bottom_orange),
    RECEIVE(4, "수령완료", R.drawable.shape_bottom_indigo)
}