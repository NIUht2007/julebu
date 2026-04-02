package com.jczy.cyclone.club.im

import android.app.Application
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.SDKOptions
import com.netease.nimlib.sdk.RequestCallback
import com.netease.nimlib.sdk.auth.LoginInfo
import com.netease.nimlib.sdk.auth.AuthService
import com.jczy.cyclone.club.mmkv.AuthMMKV

object IMManager {
    private const val IM_APP_KEY = "your_im_app_key" // 替换为实际的网易云信 AppKey

    // 初始化 IM SDK
    fun init(application: Application) {
        val options = SDKOptions().apply {
            appKey = IM_APP_KEY
        }
        NIMClient.init(application, null, options)
    }

    // 登录
    fun login(account: String, token: String, callback: (Boolean, String?) -> Unit) {
        val loginInfo = LoginInfo(account, token)
        NIMClient.getService(AuthService::class.java)
            .login(loginInfo)
            .setCallback(object : RequestCallback<LoginInfo> {
                override fun onSuccess(param: LoginInfo) {
                    callback(true, null)
                }

                override fun onFailed(code: Int) {
                    callback(false, "登录失败: $code")
                }

                override fun onException(exception: Throwable) {
                    callback(false, "登录异常: ${exception.message}")
                }
            })
    }

    // 登出
    fun logout() {
        NIMClient.getService(AuthService::class.java).logout()
    }

    // 是否已登录（从本地缓存判断）
    fun isLoggedIn(): Boolean {
        return AuthMMKV.isLogin
    }

    // 获取当前登录账号（从本地缓存读取）
    fun getCurrentAccount(): String? {
        return null // TODO: 在 login 成功后保存 account 到 MMKV
    }
}
