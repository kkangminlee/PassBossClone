package org.passorder.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import org.passorder.domain.PassDataStore
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStore: PassDataStore
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer ${dataStore.accessToken}")
            .build()

        return chain.proceed(authRequest)
    }
}