package org.passorder.data.repository

import org.passorder.data.datasource.OrderDataSource
import org.passorder.data.model.request.toRequest
import org.passorder.domain.entity.*
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

    override suspend fun putOrderStatus(uuid: String): Status {
        return dataSource.putOrderStatus(uuid).toStatus()
    }

    override suspend fun totalMoney(request: SetCount): List<Money> {
        return dataSource.totalMoney(request.toRequest()).map {
            it.toMoney()
        }
    }

    override suspend fun orderList(request: SetOrder): List<Order> {
        return dataSource.orderList(request.toRequest()).map {
            it.toOrder()
        }
    }

    override suspend fun orderCount(request: SetCount): List<Count> {
        return dataSource.orderCount(request.toRequest()).map {
            it.toCount()
        }
    }
}