package com.jczy.cyclone.club.network

import android.os.Build
import com.jczy.cyclone.club.BuildConfig
import com.jczy.cyclone.club.mmkv.AuthMMKV
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.Locale

/**
 * 公共请求头拦截器
 *
 * 参照参考项目 HttpHeaderInterceptor（com.jczy.cyclone.net.HttpHeaderInterceptor）
 * 后端依赖这些头部字段来处理请求：
 *   - platform: ANDROID
 *   - source: CLUB_APP
 *   - Authorization: 登录后携带 token
 *   - version: APP 版本号
 *   - phoneModel: 手机型号
 *   - Content-Language: 国际化语言
 */
class HttpHeaderInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .addHeader("Content-Language", getAcceptLanguage())
            .addHeader("platform", "ANDROID")
            .addHeader("source", "CLUB_APP")
            .addHeader("Authorization", AuthMMKV.token ?: "")
            .addHeader("meid", "")
            .addHeader("version", BuildConfig.VERSION_NAME)
            .addHeader("phoneModel", "${Build.BRAND}_${Build.MODEL}")
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    private fun getAcceptLanguage(): String {
        val locale = Locale.getDefault()
        return when (locale.language) {
            Locale.CHINESE.language -> {
                if (locale.country == Locale.CHINA.country) {
                    "${locale.language}-${locale.country}"
                } else {
                    "${locale.language}-${Locale.TAIWAN.country}"
                }
            }
            Locale.ENGLISH.language -> {
                Locale.ENGLISH.toString()
            }
            else -> {
                "${Locale.SIMPLIFIED_CHINESE.language}-${Locale.SIMPLIFIED_CHINESE.country}"
            }
        }
    }
}
