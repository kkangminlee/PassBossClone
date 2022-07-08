package org.passorder.data.service

import org.passorder.data.model.response.ResponseLogin
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {
    @FormUrlEncoded
    @POST("/v2/users/authentication")
    suspend fun postLogin(
        @Field("login_id") loginId : String,
        @Field("password") password : String,
        @Field("fcm_token") fcmToken : String?,
        @Field("app_agent") appAgent : Int?
    ): ResponseLogin
}