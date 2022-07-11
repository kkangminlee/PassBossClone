package org.passorder.data.model.request

import com.google.gson.annotations.SerializedName
import org.passorder.domain.entity.PostLogin

data class RequestLogin(
    @SerializedName("login_id")
    val loginId: String,
    val password: String,
    @SerializedName("fcm_token")
    val fcmToken: String?,
    @SerializedName("app_agent")
    val appAgent: Int?
)

fun PostLogin.toRequestLogin() : RequestLogin {
    return RequestLogin(loginId, password, fcmToken, appAgent)
}