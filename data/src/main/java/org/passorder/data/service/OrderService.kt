package org.passorder.data.service

import org.passorder.data.model.response.*
import retrofit2.http.*


interface OrderService {
    @GET("stores/{store_uid}")
    suspend fun getStore(
        @Path("store_uid") storeId: String
    ): ResponseStore

    @FormUrlEncoded
    @PUT("stores/{store_uid}")
    suspend fun putGateStore(
        @Path("store_uid") storeId: String,
        @Field("is_open") isOpen: Boolean
    )

    @FormUrlEncoded
    @PUT("stores/{store_uid}")
    suspend fun setMinTimeStore(
        @Path("store_uid") storeId: String,
        @Field("minimum_pickup_time") minTime: Int
    )

    @GET("orders")
    suspend fun getOrderList(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("filter") filter: Int,
        @Query("start_date") start: String?,
        @Query("end_date") end: String?
    ): List<ResponseOrder>

    @PUT("orders/{order_uid}")
    suspend fun putOrderStatus(
        @Path("order_uid") order_uid: String
    ): ResponseStatus

    @FormUrlEncoded
    @POST("analysis/sales")
    suspend fun getTotalMoney(
        @Field("start_date") start_date: String,
        @Field("end_date") end_date: String,
        @Field("include_dump") include_dump: String?,
        @Field("condition_list_dump") condition_list_dump: String?
    ): List<ResponseMoney>

    @FormUrlEncoded
    @POST("analysis/number_of_orders")
    suspend fun getOrderCount(
        @Field("start_date") start: String,
        @Field("end_date") end: String,
        @Field("include_dump") includeDump: String?,
        @Field("condition_list_dump") conditionDump: String?
    ): List<ResponseCount>
}