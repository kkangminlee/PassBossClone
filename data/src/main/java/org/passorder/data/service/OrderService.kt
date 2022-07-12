package org.passorder.data.service

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.PUT
import retrofit2.http.Path

interface OrderService {
    @FormUrlEncoded
    @PUT("stores/{store_uid}")
    suspend fun gateStore(
        @Path("store_uid") storeId: String,
        @Field("is_open") isOpen: Boolean
    )

    @FormUrlEncoded
    @PUT("stores/{store_uid}")
    suspend fun setMinTimeStore(
        @Path("store_uid") storeId: String,
        @Field("minimum_pickup_time") minTime: Int
    )
}