package org.passorder.data.model.response

import com.google.gson.annotations.SerializedName
import org.passorder.domain.entity.Notice

data class ResponseNotice (
    @SerializedName("created_date")
    val createdDate : String,
    @SerializedName("food_identifier")
    val foodIdentifier: String?,
    @SerializedName("identifier")
    val identifier: String,
    @SerializedName("is_sent")
    val isSent: Boolean,
    val message: String,
    @SerializedName("notification_kind")
    val notificationKind: Int,
    @SerializedName("store_identifier")
    val storeIdentifier: String,
    val title: String,
    @SerializedName("user_identifier")
    val userIdentifier: String
) {
    fun toNotice() = Notice(createdDate, foodIdentifier, identifier, isSent, message, notificationKind, storeIdentifier, title, userIdentifier)
}