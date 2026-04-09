package com.jczy.cyclone.club.mmkv

import com.tencent.mmkv.MMKV

object AuthMMKV {
    private val mmkv = MMKV.mmkvWithID("auth")

    // ========== 主业务 Token ==========
    var token: String?
        get() = mmkv.getString("token", null)
        set(value) { mmkv.putString("token", value) }

    var isLogin: Boolean
        get() = mmkv.getBoolean("isLogin", false)
        set(value) { mmkv.putBoolean("isLogin", value) }

    // ========== 商城 OpenApi 鉴权（奇墨云）==========
    //
    // 参照参考项目（club-android）：
    //   - openApiAccessKey  = 写死 partnerId，不需要动态获取
    //   - openApiSecretKey  = 写死 secretKey，不需要动态获取
    //   - openApiAccessToken = 直接使用主业务 accessToken（即 token 字段）
    //     → 不再需要单独调 sysLoginSmsAuto 进行二次商城登录
    //
    // 参考项目 AuthMMKV.kt 关键代码：
    //   var openApiAccessKey by MMKVProperty("23022113430907CF0113")
    //   var openApiSecretKey by MMKVProperty("6eef7179f83aea3986f7dd9be4c5cd8a")
    //   val openApiAccessToken get() = accessToken   ← 直接返回主业务 token

    /** 商城 accessKey（partnerId），放在请求头 x-api-accessKey，固定值 */
    val openApiAccessKey: String
        get() = "23022113430907CF0113"

    /** 商城 secretKey，用于签名计算，固定值 */
    val openApiSecretKey: String
        get() = "6eef7179f83aea3986f7dd9be4c5cd8a"

    /**
     * 商城访问 token，每次请求作为 body 参数传入。
     * 直接使用主业务 token — 与参考项目保持一致，无需二次商城登录。
     */
    val openApiAccessToken: String?
        get() = token

    fun clear() {
        mmkv.clearAll()
    }
}
