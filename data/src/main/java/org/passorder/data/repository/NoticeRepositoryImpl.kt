package org.passorder.data.repository

import org.passorder.data.datasource.NoticeDataSource
import org.passorder.domain.entity.Notice
import org.passorder.domain.repository.NoticeRepository
import javax.inject.Inject

class NoticeRepositoryImpl @Inject constructor(
    private val dataSource: NoticeDataSource
) : NoticeRepository {
    override suspend fun getNotice(kind: Int, start:String, end: String): List<Notice> {
        return dataSource.getNoticeStore(kind, start, end).map {
            it.toNotice()
        }
    }
}