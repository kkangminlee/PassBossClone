package org.passorder.domain.entity

data class PostLogin(
    val loginId: String,
    val password: String,
    val fcmToken: String?,
    val appAgent: Int
)

data class LoginToken (
    val accessToken: String,
    val identifier: String,
    val kind: String,
    val refreshToken: String
)