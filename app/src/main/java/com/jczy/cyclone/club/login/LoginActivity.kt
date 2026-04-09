package com.jczy.cyclone.club.login

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jczy.cyclone.club.MainActivity
import com.jczy.cyclone.club.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * 登录页面
 *
 * 参考项目：com.jczy.cyclone.ui.login.LoginActivity + PhoneLoginFragment
 * 流程：输入手机号 → 获取验证码 → 输入验证码 → 登录 → 跳转 MainActivity
 */
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var etPhone: EditText
    private lateinit var etSmsCode: EditText
    private lateinit var btnGetSmsCode: Button
    private lateinit var btnLogin: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etPhone = findViewById(R.id.et_phone)
        etSmsCode = findViewById(R.id.et_sms_code)
        btnGetSmsCode = findViewById(R.id.btn_get_sms_code)
        btnLogin = findViewById(R.id.btn_login)
        progressBar = findViewById(R.id.progress_bar)

        btnGetSmsCode.setOnClickListener {
            val phone = etPhone.text.toString().trim()
            viewModel.sendSmsCode(phone)
        }

        btnLogin.setOnClickListener {
            val phone = etPhone.text.toString().trim()
            val smsCode = etSmsCode.text.toString().trim()
            viewModel.loginBySms(phone, smsCode)
        }

        observeState()
        observeCountdown()
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is LoginViewModel.LoginState.Idle -> {
                            showLoading(false)
                        }
                        is LoginViewModel.LoginState.LoadingSms -> {
                            showLoading(true)
                            btnGetSmsCode.isEnabled = false
                        }
                        is LoginViewModel.LoginState.LoadingLogin -> {
                            showLoading(true)
                            btnLogin.isEnabled = false
                        }
                        is LoginViewModel.LoginState.SmsSuccess -> {
                            showLoading(false)
                            Toast.makeText(this@LoginActivity, "验证码已发送", Toast.LENGTH_SHORT).show()
                        }
                        is LoginViewModel.LoginState.LoginSuccess -> {
                            showLoading(false)
                            Toast.makeText(this@LoginActivity, "登录成功", Toast.LENGTH_SHORT).show()
                            goToMain()
                        }
                        is LoginViewModel.LoginState.Error -> {
                            showLoading(false)
                            btnGetSmsCode.isEnabled = true
                            btnLogin.isEnabled = true
                            Toast.makeText(this@LoginActivity, state.message, Toast.LENGTH_LONG).show()
                            viewModel.resetState()
                        }
                    }
                }
            }
        }
    }

    private fun observeCountdown() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.countdown.collect { seconds ->
                    if (seconds > 0) {
                        btnGetSmsCode.text = "${seconds}s 后重发"
                        btnGetSmsCode.isEnabled = false
                    } else {
                        btnGetSmsCode.text = "获取验证码"
                        btnGetSmsCode.isEnabled = true
                    }
                }
            }
        }
    }

    private fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
