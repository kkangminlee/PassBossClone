package org.passorder.data.model.request

import org.passorder.domain.entity.SetCount

data class RequestCount(
    val start: String,
    val end: String,
    val includeDump: String?,
    val conditionDump: String?
)

fun SetCount.toRequest() : RequestCount {
    return RequestCount(start, end, includeDump, conditionDump)
}