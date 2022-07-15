package org.passorder.data.model.response

import org.passorder.domain.entity.Count

data class ResponseCount(
    val count: Int
    ) {
    fun toCount() : Count {
        return Count(count)
    }
}