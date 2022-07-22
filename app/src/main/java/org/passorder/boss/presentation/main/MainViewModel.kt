package org.passorder.boss.presentation.main

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.passorder.domain.entity.Store
import org.passorder.domain.repository.OrderRepository
import org.passorder.ui.base.BaseViewModel
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: OrderRepository
) : BaseViewModel() {
    private val _isOpenState = MutableSharedFlow<Boolean>()
    val isOpenState = _isOpenState.asSharedFlow()

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
                if (it is HttpException) {
                    _errorMsg.emit("서버 통신 에러 error code: ${it.code()}")
                }
            }
        }
    }

    fun gateStore(isOpen: Boolean) {
        viewModelScope.launch {
            runCatching {
                repository.gateStore(isOpen)
            }.onSuccess {
                _isOpenState.emit(isOpen)
            }.onFailure {
                if (it is HttpException) {
                    _errorMsg.emit("서버 통신 에러 error code: ${it.code()}")
                }
            }
        }
    }

    fun setMinTime(time: Int) {
        viewModelScope.launch {
            runCatching {
                repository.setMinTimeStore(time)
            }.onFailure {
                if (it is HttpException) {
                    _errorMsg.emit("서버 통신 에러 error code: ${it.code()}")
                }
            }
        }
    }
}