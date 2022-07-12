package org.passorder.data.repository

import org.passorder.data.datasource.OrderDataSource
import org.passorder.domain.PassDataStore
import org.passorder.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val dataSource: OrderDataSource,
) : OrderRepository {
    override suspend fun gateStore(isOpen: Boolean) {
        dataSource.gateStore(isOpen)
    }

    override suspend fun setMinTimeStore(time: Int) {
        dataSource.setMinTimeStore(time)
    }
}