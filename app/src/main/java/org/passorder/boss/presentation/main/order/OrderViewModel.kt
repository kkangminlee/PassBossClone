package org.passorder.boss.presentation.main.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import org.passorder.domain.entity.Order
import org.passorder.domain.entity.SetOrder
import org.passorder.domain.entity.Status
import org.passorder.domain.repository.OrderRepository
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: OrderRepository
) : ViewModel() {
    private val _currentOrder = MutableStateFlow<List<Order>>(listOf())
    val currentOrder = _currentOrder.asStateFlow()

    private val _orderStatus = MutableStateFlow<Status?>(null)
    val orderStatus = _orderStatus.asStateFlow().filterNotNull()

    fun orderList(request: SetOrder) {
        viewModelScope.launch {
            runCatching {
                repository.getOrderList(request)
            }.onSuccess {
                _currentOrder.value = it
            }
        }
    }

    fun putOrderStatus(uuid: String) {
        viewModelScope.launch {
            runCatching {
                repository.putOrderStatus(uuid)
            }.onSuccess {
                _orderStatus.value = it
            }
        }
    }
}