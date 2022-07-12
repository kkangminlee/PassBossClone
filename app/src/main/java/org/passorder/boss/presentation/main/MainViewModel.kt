package org.passorder.boss.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.passorder.domain.PassDataStore
import org.passorder.domain.repository.OrderRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: OrderRepository,
): ViewModel() {
    private val _isOpenState = MutableStateFlow(false)
    val isOpenState = _isOpenState.asStateFlow()

    val minTime = MutableStateFlow("")

    fun gateStore(isOpen: Boolean) {
        viewModelScope.launch {
            runCatching {
                repository.gateStore(isOpen)
            }.onSuccess {
                _isOpenState.value = isOpen
            }.onFailure {

            }
        }
    }

    fun setMinTime(time: Int) {
        viewModelScope.launch {
            runCatching {
                repository.setMinTimeStore(5)
            }.onSuccess {
                minTime.value = "${time}분"
            }.onFailure {

            }
        }
    }

    sealed class Event {
        data class Success(val msg: String) : Event()
        data class Failure(val msg: String) : Event()
    }
}