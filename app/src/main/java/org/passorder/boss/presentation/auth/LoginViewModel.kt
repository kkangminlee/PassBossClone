package org.passorder.boss.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.passorder.domain.entity.LoginToken
import org.passorder.domain.entity.PostLogin
import org.passorder.domain.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private val _loginInfo = MutableSharedFlow<Event>()
    val loginInfo: SharedFlow<Event> = _loginInfo.asSharedFlow()

    val id = MutableStateFlow("")
    val pw = MutableStateFlow("")

    val isInputBlank: Boolean
        get() = id.value.isNullOrBlank() || pw.value.isNullOrBlank()

    fun postLogin(request: PostLogin) {
        viewModelScope.launch {
            runCatching {
                repository.postLogin(request)
            }.onSuccess {
                _loginInfo.emit(Event.Success(it))
            }.onFailure {
                _loginInfo.emit(Event.Failure("아이디나 패스워드가 일치하지 않습니다."))
            }
        }
    }

    sealed class Event {
        data class Success(val loginToken: LoginToken) : Event()
        data class Failure(val message: String) : Event()
    }
}