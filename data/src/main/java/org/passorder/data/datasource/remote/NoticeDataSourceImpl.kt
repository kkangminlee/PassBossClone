package org.passorder.data.datasource.remote

import org.passorder.data.datasource.NoticeDataSource
import org.passorder.data.model.response.ResponseNotice
import org.passorder.data.service.NoticeService
import org.passorder.domain.PassDataStore
import javax.inject.Inject

class NoticeDataSourceImpl @Inject constructor(
    private val service: NoticeService,
    private val dataStore: PassDataStore
) : NoticeDataSource {
    override suspend fun getNoticeStore(kind: Int, start:String, end: String): List<ResponseNotice> {
        return service.getNotification(dataStore.storeUUID, kind, start, end)
    }
}