package org.passorder.data.datasource

import org.passorder.data.model.request.RequestCount
import org.passorder.data.model.request.RequestOrder
import org.passorder.data.model.response.ResponseCount
import org.passorder.data.model.response.ResponseMoney
import org.passorder.data.model.response.ResponseOrder
import org.passorder.data.model.response.ResponseStatus

interface OrderDataSource {
    suspend fun gateStore(isOpen: Boolean)
    suspend fun setMinTimeStore(time: Int)
    suspend fun putOrderStatus(uuid: String): ResponseStatus
    suspend fun orderList(request: RequestOrder): List<ResponseOrder>
    suspend fun orderCount(request: RequestCount): List<ResponseCount>
    suspend fun totalMoney(request: RequestCount): List<ResponseMoney>
}