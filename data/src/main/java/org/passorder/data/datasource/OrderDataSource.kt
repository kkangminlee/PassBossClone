package org.passorder.data.datasource

interface OrderDataSource {
    suspend fun gateStore(isOpen: Boolean)
    suspend fun setMinTimeStore(time: Int)
}