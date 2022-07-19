package org.passorder.boss.presentation.main.history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.passorder.domain.entity.Order
import org.passorder.domain.entity.SetCount
import org.passorder.domain.entity.SetOrder
import org.passorder.domain.repository.OrderRepository
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: OrderRepository
) : ViewModel() {
    private val _currentOrder = MutableStateFlow<List<Order>>(listOf())
    val currentOrder = _currentOrder.asStateFlow()

    private val _money = MutableStateFlow(0)
    val money = _money.asStateFlow()

    private val _orderCount = MutableStateFlow(0)
    val orderCount = _orderCount.asStateFlow()

    fun orderList(request: SetOrder) {
        viewModelScope.launch {
            runCatching {
                repository.orderList(request)
            }.onSuccess {
                _currentOrder.value = it
            }.onFailure {
                Log.e("ERROR", it.toString())
            }
        }
    }

    fun getOrderCount(request: SetCount) {
        viewModelScope.launch {
            runCatching {
                repository.orderCount(request)
            }.onSuccess {
                var count = 0
                it.map { response ->
                    count += response.count
                }
                _orderCount.value = count
            }
        }
    }

    fun getTotalMoney(request: SetCount) {
        viewModelScope.launch {
            runCatching {
                repository.totalMoney(request)
            }.onSuccess {
                var money = 0
                it.map { response ->
                    money += response.usedPassorderPoint
                }
                _money.value = money
            }
        }
    }
}