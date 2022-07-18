package org.passorder.data.model.response

import com.google.gson.annotations.SerializedName
import org.passorder.domain.entity.Store

data class ResponseStore(
    val name: String,
    @SerializedName("minimum_pickup_time")
    val minimumPickupTime: Int
) {
    fun toStore() = Store(name, minimumPickupTime)
}