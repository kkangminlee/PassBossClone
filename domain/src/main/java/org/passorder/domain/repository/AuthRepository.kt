package org.passorder.domain.repository

import org.passorder.domain.entity.LoginToken
import org.passorder.domain.entity.PostLogin

interface AuthRepository {
    suspend fun postLogin(request: PostLogin): LoginToken
}