package org.passorder.data.model.request

import org.passorder.domain.entity.SetOrder

data class RequestOrder(
    val page: Int,
    val limit: Int,
    val filter: Int,
    val start: String?,
    val end: String?
)

fun SetOrder.toRequest(): RequestOrder {
    return RequestOrder(page, limit, filter, start, end)
}