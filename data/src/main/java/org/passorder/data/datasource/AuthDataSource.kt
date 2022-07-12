package org.passorder.data.datasource

import org.passorder.data.model.request.RequestLogin
import org.passorder.data.model.response.ResponseLogin
import org.passorder.data.model.response.ResponseUser

interface AuthDataSource {
    suspend fun postLogin(request: RequestLogin): ResponseLogin
    suspend fun getUser(uuid: String) : ResponseUser
}