package org.passorder.data.interceptor

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.passorder.data.model.response.ResponseLogin
import org.passorder.domain.PassDataStore
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStore: PassDataStore
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        // 로그인 일때는 Header 넣지 않기위한 로직
        val authRequest = if (isLogin(originalRequest)) originalRequest else
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer ${dataStore.accessToken}")
                .build()
        val response = chain.proceed(authRequest)
        when (response.code()) {
            // 토큰이 만료되면 리프레시 토큰으로 Put 서버 통신 요청후 accessToken 교체
            401 -> {
                val refreshTokenRequest = originalRequest.newBuilder().put(null)
                    .url("${Companion.BASE_URL}v2/users/authentication")
                    .addHeader("Authorization", "Bearer ${dataStore.refreshToken}")
                    .build()
                val refreshTokenResponse = chain.proceed(refreshTokenRequest)

                if (refreshTokenResponse.isSuccessful) {
                    val responseToken: ResponseLogin =
                        Gson().fromJson(
                            refreshTokenResponse.body()?.string(),
                            ResponseLogin::class.java
                        )
                    // 새로 받아온 accessToken DataStore 에 저장
                    with(dataStore) {
                        accessToken = responseToken.accessToken
                    }
                    val newRequest = originalRequest.newBuilder()
                        .addHeader("Authorization", dataStore.accessToken)
                        .build()
                    return chain.proceed(newRequest)
                }
            }
        }
        return response
    }

    // url에 token, auth가 포함되어 있을 시 true 반환
    private fun isLogin(originalRequest: Request) =
        when {
            originalRequest.url().encodedPath().contains("token") -> true
            originalRequest.url().encodedPath().contains("auth") -> true
            else -> false
        }

    companion object {
        private const val BASE_URL = "https://staging.passorder.me:9999/v2/"
    }
}