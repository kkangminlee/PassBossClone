package org.passorder.domain.repository

interface OrderRepository {
    suspend fun gateStore(isOpen: Boolean)
    suspend fun setMinTimeStore(time: Int)
}