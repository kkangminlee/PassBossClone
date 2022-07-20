package org.passorder.domain.repository

import org.passorder.domain.entity.Notice
import org.passorder.domain.entity.SetNotice

interface NoticeRepository {
    suspend fun getNotice(kind: Int, start:String, end: String): List<Notice>
}