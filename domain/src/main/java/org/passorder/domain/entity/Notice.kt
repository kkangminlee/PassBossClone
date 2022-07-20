package org.passorder.domain.entity

data class SetNotice(
    val notificationKind: Int,
    val startDate: String,
    val endDate: String
)

data class Notice(
    val createdDate: String,
    val foodIdentifier: String?,
    val identifier: String,
    val is_sent: Boolean,
    val message: String,
    val notificationKind: Int,
    val storeIdentifier: String,
    val title: String,
    val userIdentifier: String
)
