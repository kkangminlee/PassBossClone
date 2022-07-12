package org.passorder.data.model.response

import com.google.gson.annotations.SerializedName
import org.passorder.domain.entity.User

data class ResponseUser(
    val email: String,
    @SerializedName("fcm_token")
    val fcmToken: String,
    val identifier: String,
    @SerializedName("login_id")
    val loginId: String,
    val nickname: String,
    val phone: String,
    val status: Int,
    @SerializedName("store_identifier")
    val storeIdentifier: String,
) {
    fun toUser(): User {
        return User(email, fcmToken, identifier, loginId, nickname, phone, status, storeIdentifier)
    }
}