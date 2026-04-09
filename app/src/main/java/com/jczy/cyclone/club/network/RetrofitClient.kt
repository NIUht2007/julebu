package com.jczy.cyclone.club.network

import com.google.gson.Gson
import com.jczy.cyclone.club.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    /** 主业务 API（宗申车联网后端） */
    private const val BASE_URL = "https://iovapicyclone.zonsenmotor.com/"

    /**
     * 商城 API（奇墨云 OpenApi 网关）
     * 所有请求统一 POST 到 gateway.do，由 OpenApiHeaderInterceptor 注入公共参数+签名。
     */
    private const val MALL_BASE_URL = "http://test-zsjc-openapi.qimoyun.com/"

    private const val TIMEOUT_SECONDS = 30L

    private val gson: Gson by lazy { Gson() }

    private val loggingInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    /** 主业务 OkHttpClient */
    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(HttpHeaderInterceptor())
            .build()
    }

    /**
     * 商城专用 OkHttpClient
     * 添加了 OpenApiHeaderInterceptor，自动注入：
     *   - service（从 @OpenApiService 注解读取）
     *   - token / partnerId / requestNo / version
     *   - Header: x-api-sign / x-api-signType / x-api-accessKey
     */
    private val mallHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            // ⚠️ 商城签名拦截器必须在日志拦截器之后，保证日志能看到完整参数
            .addInterceptor(OpenApiHeaderInterceptor())
            .build()
    }

    /**
     * 主业务 Retrofit 实例
     * 使用 Gson（与参考项目一致），避免 Moshi 对默认值 / 泛型的兼容问题。
     */
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    /**
     * 商城独立 Retrofit 实例（指向奇墨云网关）
     */
    val mallRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(MALL_BASE_URL)
            .client(mallHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    /** 创建主业务接口 Service */
    inline fun <reified T> createService(): T = retrofit.create(T::class.java)

    /** 创建商城接口 Service（使用带签名拦截器的 mallRetrofit） */
    inline fun <reified T> createMallService(): T = mallRetrofit.create(T::class.java)
}
