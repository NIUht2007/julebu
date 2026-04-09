package com.jczy.cyclone.club.network

/**
 * 主业务 API 通用响应体
 *
 * 对齐参考项目 BaseModel（com.jczy.lib_net.model.BaseModel）：
 *   data class BaseModel<T>(val code: Int, var msg: String, val data: T?, val errorCode: String? = null)
 *
 * 后端实际返回格式（经 Logcat 实测确认）：
 *   成功：{ "code": 0, "errorCode": null, "msg": null, "data": ... }
 *   失败：{ "code": 非0, "msg": "错误信息", "data": null }
 *
 * code == 0 视为成功。
 */
data class ApiResponse<T>(
    val code: Int = -1,
    val msg: String? = null,
    val data: T? = null,
    val errorCode: String? = null
) {
    /** 业务是否成功（code == 0） */
    val success: Boolean get() = code == 0

    /** 兼容旧代码调用的 message 属性 */
    val message: String? get() = msg

    companion object {
        fun <T> success(data: T): ApiResponse<T> =
            ApiResponse(code = 0, msg = "success", data = data)

        fun <T> error(message: String, code: Int = -1): ApiResponse<T> =
            ApiResponse(code = code, msg = message)
    }
}

inline fun <T> ApiResponse<T>.onSuccess(action: (T) -> Unit): ApiResponse<T> {
    if (success) data?.let(action)
    return this
}

inline fun <T> ApiResponse<T>.onError(action: (String) -> Unit): ApiResponse<T> {
    if (!success) action(msg ?: "未知错误")
    return this
}

fun <T> ApiResponse<T>.isSuccess(): Boolean = success && data != null
