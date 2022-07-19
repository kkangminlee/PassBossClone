package org.passorder.data.repository

import org.passorder.data.datasource.OrderDataSource
import org.passorder.data.model.request.toRequest
import org.passorder.domain.entity.*
import org.passorder.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val dataSource: OrderDataSource,
) : OrderRepository {
    override suspend fun getStore(): Store {
        return dataSource.getStore().toStore()
    }

    override suspend fun gateStore(isOpen: Boolean) {
        dataSource.putGateStore(isOpen)
    }

    override suspend fun setMinTimeStore(time: Int) {
        dataSource.setMinTimeStore(time)
    }

    override suspend fun putOrderStatus(uuid: String): Status {
        return dataSource.putOrderStatus(uuid).toStatus()
    }

    override suspend fun getTotalMoney(request: SetCount): List<Money> {
        return dataSource.getTotalMoney(request.toRequest()).map {
            it.toMoney()
        }
    }

    override suspend fun getOrderList(request: SetOrder): List<Order> {
        return dataSource.getOrderList(request.toRequest()).map {
            it.toOrder()
        }
    }

    override suspend fun getOrderCount(request: SetCount): List<Count> {
        return dataSource.getOrderCount(request.toRequest()).map {
            it.toCount()
        }
    }
}