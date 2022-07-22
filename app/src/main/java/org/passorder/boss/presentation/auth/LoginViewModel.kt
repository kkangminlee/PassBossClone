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
import retrofit2.HttpException
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
        get() = id.value.isBlank() || pw.value.isBlank()

    fun postLogin(request: PostLogin) {
        viewModelScope.launch {
            runCatching {
                repository.postLogin(request)
            }.onSuccess {
                dataStore.userUUID = it.identifier
                dataStore.accessToken = it.accessToken
                dataStore.refreshToken = it.refreshToken
                _loginInfo.emit(Event.Success(it))
            }.onFailure {
                if (it is HttpException) {
                    when (it.code()) {
                        404 -> _loginInfo.emit(Event.Failure("아이디 또는 비밀번호를 확인해주세요"))
                        else -> _loginInfo.emit(Event.Failure("서버 통신 에러 error code: ${it.code()}"))
                    }
                }
            }
        }
    }

    fun getUser() {
        viewModelScope.launch {
            runCatching {
                repository.getUser(dataStore.userUUID)
            }.onSuccess {
                dataStore.storeUUID = it.storeIdentifier
                _userInfo.value = it
            }.onFailure {
                if (it is HttpException) {
                    _loginInfo.emit(Event.Failure("서버 통신 에러 error code: ${it.code()}"))
                }
            }
        }
    }

    sealed class Event {
        data class Success(val loginToken: LoginToken) : Event()
        data class Failure(val message: String) : Event()
    }
}