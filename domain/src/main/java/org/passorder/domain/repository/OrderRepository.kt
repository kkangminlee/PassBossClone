package org.passorder.domain.repository

import org.passorder.domain.entity.*

interface OrderRepository {
    suspend fun getStore(): Store
    suspend fun gateStore(isOpen: Boolean)
    suspend fun setMinTimeStore(time: Int)
    suspend fun putOrderStatus(uuid: String): Status
    suspend fun orderList(request: SetOrder): List<Order>
    suspend fun orderCount(request: SetCount): List<Count>
    suspend fun totalMoney(request: SetCount): List<Money>
}