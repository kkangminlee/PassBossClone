package org.passorder.domain.entity

data class SetCount(
    val start: String,
    val end: String,
    val includeDump: String?,
    val conditionDump: String?
)

data class Count(
    val count: Int
)