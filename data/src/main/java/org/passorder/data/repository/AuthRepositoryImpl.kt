package org.passorder.data.repository

import org.passorder.data.datasource.AuthDataSource
import org.passorder.data.model.request.toRequestLogin
import org.passorder.domain.entity.LoginToken
import org.passorder.domain.entity.PostLogin
import org.passorder.domain.entity.User
import org.passorder.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataSource: AuthDataSource
): AuthRepository {
    override suspend fun postLogin(request: PostLogin): LoginToken {
        return dataSource.postLogin(request.toRequestLogin()).toLogin()
    }

    override suspend fun getUser(uuid: String): User {
        return dataSource.getUser(uuid).toUser()
    }
}