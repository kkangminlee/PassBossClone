package org.passorder.boss.presentation.main.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.passorder.data.datasource.remote.OrderPagingSource
import org.passorder.data.model.request.RequestOrder
import org.passorder.data.service.OrderService
import org.passorder.domain.entity.SetCount
import org.passorder.domain.repository.OrderRepository
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: OrderRepository,
    private val service: OrderService
) : ViewModel() {
    private val _money = MutableStateFlow(0)
    val money = _money.asStateFlow()

    private val _orderCount = MutableStateFlow(0)
    val orderCount = _orderCount.asStateFlow()

    // 지난 주문 내역 리스트 서버 통신 페이징으로 가져오기
    fun orderList(request: RequestOrder) = Pager(
        config = PagingConfig(10),
        pagingSourceFactory = { OrderPagingSource(service, request) }
    ).flow.map {  pagingData ->
        pagingData.map {
            it.toOrder()
        }
    }.cachedIn(viewModelScope)

    // 총 판매량 서버 통신으로 가져오기
    fun getOrderCount(request: SetCount) {
        viewModelScope.launch {
            runCatching {
                repository.getOrderCount(request)
            }.onSuccess {
                var count = 0
                it.map { response ->
                    count += response.count
                }
                _orderCount.value = count
            }
        }
    }

    // 총 매출 값 서버 통신으로 가져오기
    fun getTotalMoney(request: SetCount) {
        viewModelScope.launch {
            runCatching {
                repository.getTotalMoney(request)
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