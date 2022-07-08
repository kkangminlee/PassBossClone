package org.passorder.data.model.response

import com.google.gson.annotations.SerializedName
import org.passorder.domain.entity.LoginToken

data class ResponseLogin(
    @SerializedName("access_token")
    val accessToken: String,
    val identifier: String,
    val kind: String,
    @SerializedName("refresh_token")
    val refreshToken: String
) {
    fun toLogin() : LoginToken {
        return LoginToken(accessToken, identifier, kind, refreshToken)
    }
}