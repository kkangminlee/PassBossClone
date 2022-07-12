package org.passorder.domain.repository

import org.passorder.domain.entity.LoginToken
import org.passorder.domain.entity.PostLogin
import org.passorder.domain.entity.User

interface AuthRepository {
    suspend fun postLogin(request: PostLogin): LoginToken
    suspend fun getUser(uuid: String): User
}