package org.passorder.data.service

import org.passorder.data.model.response.ResponseNotice
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NoticeService {
    @GET("stores/{store_identifier}/notifications")
    suspend fun getNotification(
        @Path("store_identifier") storeIdentifier: String,
        @Query("notification_kind") notificationKind: Int,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): List<ResponseNotice>
}