package org.passorder.domain.entity

data class Status(
    val msg: String,
    val status: Int,
)

data class NewStatus(
    val status: Int,
    val position: Int
)