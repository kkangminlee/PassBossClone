package org.passorder.data.datasource

import org.passorder.data.model.response.ResponseNotice

interface NoticeDataSource {
    suspend fun getNoticeStore(kind: Int, start:String, end: String): List<ResponseNotice>
}