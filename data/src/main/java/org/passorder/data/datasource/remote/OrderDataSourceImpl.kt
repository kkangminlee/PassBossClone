package org.passorder.data.datasource.remote

import org.passorder.data.datasource.OrderDataSource
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
}