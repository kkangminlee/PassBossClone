package org.passorder.boss.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import org.passorder.domain.entity.Store
import org.passorder.domain.repository.OrderRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: OrderRepository
): ViewModel() {
    private val _isOpenState = MutableStateFlow(false)
    val isOpenState = _isOpenState.asStateFlow()

    private val _storeInfo = MutableStateFlow<Store?>(null)
    val storeInfo = _storeInfo.asStateFlow().filterNotNull()

    init {
        getStore()
    }

    private fun getStore() {
        viewModelScope.launch {
            runCatching {
                repository.getStore()
            }.onSuccess {
                _storeInfo.value = it
            }.onFailure {

            }
        }
    }

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
                repository.setMinTimeStore(time)
            }.onSuccess {

            }.onFailure {

            }
        }
    }
}