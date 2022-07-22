package org.passorder.boss.presentation.main.order

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import org.passorder.domain.entity.NewStatus
import org.passorder.domain.entity.Order
import org.passorder.domain.entity.SetOrder
import org.passorder.domain.repository.OrderRepository
import org.passorder.ui.base.BaseViewModel
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: OrderRepository
) : BaseViewModel() {
    private val _currentOrder = MutableStateFlow<List<Order>>(listOf())
    val currentOrder = _currentOrder.asStateFlow()

    private val _orderStatus = MutableStateFlow<NewStatus?>(null)
    val orderStatus = _orderStatus.asStateFlow().filterNotNull()

    fun orderList(request: SetOrder) {
        viewModelScope.launch {
            runCatching {
                repository.getOrderList(request)
            }.onSuccess {
                _currentOrder.value = it
            }.onFailure {
                if (it is HttpException) {
                    _errorMsg.emit("서버 통신 에러 error code: ${it.code()}")
                }
            }
        }
    }

    // 주문 상태 변경하는 서버 통신 로직
    fun putOrderStatus(uuid: String, pos: Int) {
        viewModelScope.launch {
            runCatching {
                repository.putOrderStatus(uuid)
            }.onSuccess {
                val newStatus = NewStatus(it.status, pos)
                _orderStatus.value = newStatus
            }.onFailure {
                if (it is HttpException) {
                    _errorMsg.emit("서버 통신 에러 error code: ${it.code()}")
                }
            }
        }
    }
}