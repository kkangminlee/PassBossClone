package org.passorder.boss.presentation.auth

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.passorder.boss.R
import org.passorder.boss.databinding.ActivityLoginBinding
import org.passorder.boss.presentation.main.MainActivity
import org.passorder.boss.util.EncryptCenter
import org.passorder.domain.entity.PostLogin
import org.passorder.ui.base.BindingActivity
import org.passorder.ui.context.toast

@AndroidEntryPoint
class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        initEvent()
        observe()
    }

    private fun initEvent() {
        // 로그인 클릭 이벤트
        binding.tvLogin.setOnClickListener {
            // 네트워크 연결 확인
            when (getNetWorkStatusCheck()) {
                true -> {
                    if (viewModel.isInputBlank) {
                        toast("아이디나 비밀번호를 빈칸없이 입력해주세요.")
                    } else {
                        startLogin()
                    }
                }
                false -> {
                    toast("네트워크 연결을 확인해주세요")
                }
            }
        }
    }

    private fun observe() {
        // 뷰모델에서로그인이 서버통신 성공하면 유저 정보 조회 실패하면 아이디 패스워드 확인 로그인 메세지
        viewModel.loginInfo.flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is LoginViewModel.Event.Success -> {
                        viewModel.getUser()
                    }
                    is LoginViewModel.Event.Failure -> {
                        toast(it.message)
                    }
                }
            }.launchIn(lifecycleScope)

        // 뷰모델에서 유저정보에 대한 서버통신 값이 오면 메인화면으로 이동
        viewModel.userInfo.flowWithLifecycle(lifecycle)
            .onEach {
                Intent(this, MainActivity::class.java).apply {
                    startActivity(this)
                }
                finish()
            }.launchIn(lifecycleScope)
    }

    // 패스워드 암호화 후 뷰모델에 로그인 서버통신 요청
    private fun startLogin() {
        val encryptPw = EncryptCenter.encrypt(binding.etPw.text.toString())
        val request = PostLogin(
            binding.etId.text.toString(),
            encryptPw,
            null,
            1
        )
        viewModel.postLogin(request)
    }

    // 네트워크 연결 체크 함수
    private fun getNetWorkStatusCheck(): Boolean {
        val connectManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkStatus: NetworkInfo? = connectManager.activeNetworkInfo
        val connectCheck: Boolean = networkStatus?.isConnectedOrConnecting == true
        return connectCheck
    }

}