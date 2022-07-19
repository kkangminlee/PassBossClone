package org.passorder.data.datasource

import org.passorder.data.model.request.RequestCount
import org.passorder.data.model.request.RequestOrder
import org.passorder.data.model.response.*

interface OrderDataSource {
    suspend fun getStore(): ResponseStore
    suspend fun putGateStore(isOpen: Boolean)
    suspend fun setMinTimeStore(time: Int)
    suspend fun putOrderStatus(uuid: String): ResponseStatus
    suspend fun getOrderList(request: RequestOrder): List<ResponseOrder>
    suspend fun orderCount(request: RequestCount): List<ResponseCount>
    suspend fun totalMoney(request: RequestCount): List<ResponseMoney>
}