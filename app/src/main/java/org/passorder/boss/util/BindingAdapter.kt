package org.passorder.boss.util

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapter {
    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter("text_price")
    fun setText(view: TextView, price: Int) {
        view.text = "금액 : ${price}원"
    }

    @JvmStatic
    @BindingAdapter("date_format")
    fun dataFormat(view: TextView, date: String) {
        val pattern = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH).parse(date)
        val koreaDate = SimpleDateFormat("yyyy년 MM월 dd일 E요일 a hh시 mm분", Locale.KOREA).format(pattern)
        view.text = koreaDate
    }
}