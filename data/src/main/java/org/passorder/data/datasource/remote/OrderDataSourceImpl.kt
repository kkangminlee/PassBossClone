package org.passorder.data.datasource.remote

import org.passorder.data.datasource.OrderDataSource
import org.passorder.data.model.request.RequestCount
import org.passorder.data.model.request.RequestOrder
import org.passorder.data.model.response.ResponseCount
import org.passorder.data.model.response.ResponseMoney
import org.passorder.data.model.response.ResponseOrder
import org.passorder.data.model.response.ResponseStatus
import org.passorder.data.service.OrderService
import org.passorder.domain.PassDataStore
import javax.inject.Inject

class OrderDataSourceImpl @Inject constructor(
    private val service: OrderService,
    private val dataStore: PassDataStore
): OrderDataSource {
    override suspend fun gateStore(isOpen: Boolean) {
        service.gateStore(dataStore.storeUUID, isOpen)
    }

    override suspend fun setMinTimeStore(time: Int) {
        service.setMinTimeStore(dataStore.storeUUID, time)
    }

    override suspend fun putOrderStatus(uuid: String):ResponseStatus {
        return service.putOrderStatus(uuid)
    }

    override suspend fun orderList(request: RequestOrder): List<ResponseOrder> {
        return service.orderList(request.page, request.limit, request.filter, request.start, request.end).map {
            it.translate()
        }
    }

    override suspend fun totalMoney(request: RequestCount): List<ResponseMoney> {
        return service.getTotalMoney(request.start, request.end, request.includeDump, request.conditionDump)
    }

    override suspend fun orderCount(request: RequestCount): List<ResponseCount> {
        return service.getOrderCount(request.start,request.end,request.includeDump,request.conditionDump)
    }
}