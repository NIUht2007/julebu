package com.jczy.cyclone.club.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jczy.cyclone.club.mmkv.AuthMMKV
import com.jczy.cyclone.club.network.ClubApi
import com.jczy.cyclone.club.network.RetrofitClient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import javax.inject.Inject

/**
 * 登录 ViewModel
 *
 * 参考项目 com.jczy.cyclone.ui.login.LoginViewModel
 * 实现：手机号+短信验证码登录
 */
@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    companion object {
        private const val TAG = "LoginViewModel"
    }

    private val api: ClubApi = RetrofitClient.createService()
    private val gson = Gson()

    // ---- UI 状态 ----

    sealed class LoginState {
        object Idle : LoginState()
        object LoadingSms : LoginState()       // 正在发送验证码
        object LoadingLogin : LoginState()     // 正在登录
        data class SmsSuccess(val phone: String) : LoginState()  // 验证码发送成功
        object LoginSuccess : LoginState()
        data class Error(val message: String) : LoginState()
    }

    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state: StateFlow<LoginState> = _state

    // 倒计时
    private val _countdown = MutableStateFlow(0)
    val countdown: StateFlow<Int> = _countdown

    private var countdownJob: Job? = null

    // ---- 验证码 ----

    /**
     * 发送登录短信验证码
     */
    fun sendSmsCode(phone: String) {
        if (phone.length != 11) {
            _state.value = LoginState.Error("请输入正确的11位手机号")
            return
        }
        viewModelScope.launch {
            _state.value = LoginState.LoadingSms
            try {
                Log.d(TAG, "发送验证码: phone=$phone")
                val resp = api.getLoginSmsCode(phone)
                Log.d(TAG, "发送验证码响应: code=${resp.code}, msg=${resp.msg}, data=${resp.data}")
                if (resp.success) {
                    _state.value = LoginState.SmsSuccess(phone)
                    startCountdown()
                } else {
                    _state.value = LoginState.Error(resp.msg ?: "发送失败(code=${resp.code})")
                }
            } catch (e: Exception) {
                Log.e(TAG, "发送验证码异常", e)
                _state.value = LoginState.Error("网络异常：${e.message}")
            }
        }
    }

    private fun startCountdown(seconds: Int = 60) {
        countdownJob?.cancel()
        countdownJob = viewModelScope.launch {
            for (i in seconds downTo 1) {
                _countdown.value = i
                delay(1_000)
            }
            _countdown.value = 0
        }
    }

    // ---- 登录 ----

    /**
     * 短信验证码登录
     * POST club-auth/login
     * Body: {"userName":"phone","password":"smsCode","grantType":"sms","operatorType":2}
     */
    fun loginBySms(phone: String, smsCode: String) {
        if (phone.length != 11) {
            _state.value = LoginState.Error("请输入正确的手机号")
            return
        }
        if (smsCode.isEmpty()) {
            _state.value = LoginState.Error("请输入验证码")
            return
        }
        viewModelScope.launch {
            _state.value = LoginState.LoadingLogin
            try {
                val body = mapOf(
                    "userName" to phone,
                    "password" to smsCode,
                    "grantType" to "sms",
                    "operatorType" to 2,
                    "code" to ""
                )
                val jsonBody = gson.toJson(body)
                Log.d(TAG, "登录请求: $jsonBody")
                val requestBody = RequestBody.create(
                    "application/json".toMediaType(),
                    jsonBody
                )
                val resp = api.login(requestBody)
                Log.d(TAG, "登录响应: code=${resp.code}, msg=${resp.msg}, data=${resp.data}")
                if (resp.success && resp.data != null) {
                    // 参考项目：AuthMMKV.accessToken = (it.data as Map<*, *>)["access_token"] as String
                    val accessToken = (resp.data as? Map<*, *>)?.get("access_token") as? String
                    if (!accessToken.isNullOrBlank()) {
                        AuthMMKV.token = accessToken
                        AuthMMKV.isLogin = true
                        Log.d(TAG, "登录成功! token=${accessToken.take(20)}...")
                        _state.value = LoginState.LoginSuccess
                    } else {
                        Log.w(TAG, "token 为空! data=${resp.data}")
                        _state.value = LoginState.Error("登录失败：未获取到 token")
                    }
                } else {
                    _state.value = LoginState.Error(resp.msg ?: "登录失败(code=${resp.code})")
                }
            } catch (e: Exception) {
                Log.e(TAG, "登录异常", e)
                _state.value = LoginState.Error("网络异常：${e.message}")
            }
        }
    }

    fun resetState() {
        _state.value = LoginState.Idle
    }

    override fun onCleared() {
        super.onCleared()
        countdownJob?.cancel()
    }
}
