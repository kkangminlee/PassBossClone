package org.passorder.boss.presentation.auth

import android.content.Intent
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
        binding.tvLogin.setOnClickListener {
            if (viewModel.isInputBlank) {
                toast("아이디나 비밀번호를 빈칸없이 입력해주세요.")
            } else {
                startLogin()
            }
        }
    }

    private fun observe() {
        viewModel.loginInfo.flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is LoginViewModel.Event.Success -> {
                        finish()
                        Intent(this, MainActivity::class.java).apply {
                            startActivity(this)
                        }
                    }
                    is LoginViewModel.Event.Failure -> {
                        toast(it.message)
                    }
                }
            }.launchIn(lifecycleScope)
    }

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
}