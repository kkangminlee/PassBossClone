package org.passorder.data.model.response

import org.passorder.domain.entity.Status

data class ResponseStatus(
    val msg: String,
    val status: Int
) {
    fun toStatus() : Status {
        return Status(msg, status)
    }
}