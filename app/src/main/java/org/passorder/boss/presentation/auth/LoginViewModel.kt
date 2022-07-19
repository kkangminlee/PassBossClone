package org.passorder.boss.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.passorder.domain.PassDataStore
import org.passorder.domain.entity.LoginToken
import org.passorder.domain.entity.PostLogin
import org.passorder.domain.entity.User
import org.passorder.domain.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val dataStore: PassDataStore
) : ViewModel() {
    private val _loginInfo = MutableSharedFlow<Event>()
    val loginInfo = _loginInfo.asSharedFlow()

    private val _userInfo = MutableStateFlow<User?>(null)
    val userInfo = _userInfo.asStateFlow().filterNotNull()

    val id = MutableStateFlow("")
    val pw = MutableStateFlow("")

    val isInputBlank: Boolean
        get() = id.value.isNullOrBlank() || pw.value.isNullOrBlank()

    fun postLogin(request: PostLogin) {
        viewModelScope.launch {
            runCatching {
                repository.postLogin(request)
            }.onSuccess {
                dataStore.userUUID = it.identifier
                dataStore.accessToken = it.accessToken
                _loginInfo.emit(Event.Success(it))
            }.onFailure {
                _loginInfo.emit(Event.Failure("아이디나 패스워드가 일치하지 않습니다."))
            }
        }
    }

    fun getUser() {
        viewModelScope.launch {
            runCatching {
                repository.getUser(dataStore.userUUID)
            }.fold({
                dataStore.storeUUID = it.storeIdentifier
                _userInfo.value = it
            }, {
            })
        }
    }

    sealed class Event {
        data class Success(val loginToken: LoginToken) : Event()
        data class Failure(val message: String) : Event()
    }
}