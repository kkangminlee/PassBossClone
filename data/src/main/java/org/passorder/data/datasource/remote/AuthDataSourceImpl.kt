package org.passorder.data.datasource.remote

import org.passorder.data.datasource.AuthDataSource
import org.passorder.data.model.request.RequestLogin
import org.passorder.data.model.response.ResponseLogin
import org.passorder.data.service.AuthService
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val service: AuthService
) : AuthDataSource {
    override suspend fun postLogin(request: RequestLogin): ResponseLogin {
        return service.postLogin(
            request.loginId,
            request.password,
            request.fcmToken,
            request.appAgent
        )
    }
}