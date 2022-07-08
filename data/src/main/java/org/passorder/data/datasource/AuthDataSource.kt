package org.passorder.data.datasource

import org.passorder.data.model.request.RequestLogin
import org.passorder.data.model.response.ResponseLogin

interface AuthDataSource {
    suspend fun postLogin(request: RequestLogin): ResponseLogin
}