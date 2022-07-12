package org.passorder.data.service

import org.passorder.data.model.response.ResponseLogin
import org.passorder.data.model.response.ResponseUser
import retrofit2.http.*

interface AuthService {
    @FormUrlEncoded
    @POST("users/authentication")
    suspend fun postLogin(
        @Field("login_id") loginId : String,
        @Field("password") password : String,
        @Field("fcm_token") fcmToken : String?,
        @Field("app_agent") appAgent : Int?
    ): ResponseLogin

    @GET("users/{uuid}")
    suspend fun getUser(
        @Path("uuid") uuid: String
    ): ResponseUser
}