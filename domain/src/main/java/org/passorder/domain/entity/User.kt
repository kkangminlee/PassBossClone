package org.passorder.domain.entity

data class User(
    val email: String,
    val fcmToken: String,
    val identifier: String,
    val loginId: String,
    val nickname: String,
    val phone: String,
    val status: Int,
    val storeIdentifier: String,
)