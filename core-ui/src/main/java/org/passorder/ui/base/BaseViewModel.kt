package org.passorder.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseViewModel() :ViewModel() {
    protected val _errorMsg = MutableSharedFlow<String>()
    val errorMsg = _errorMsg.asSharedFlow()

    protected val _successMsg = MutableSharedFlow<String>()
    val successMsg = _successMsg.asSharedFlow()
}