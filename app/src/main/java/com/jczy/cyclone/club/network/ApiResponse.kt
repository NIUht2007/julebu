package com.jczy.cyclone.club.network

data class ApiResponse<T>(
    val success: Boolean = false,
    val code: Int = 200,
    val message: String? = null,
    val data: T? = null
) {
    companion object {
        fun <T> success(data: T): ApiResponse<T> =
            ApiResponse(success = true, code = 200, data = data)

        fun <T> error(message: String, code: Int = -1): ApiResponse<T> =
            ApiResponse(success = false, code = code, message = message)
    }
}

inline fun <T> ApiResponse<T>.onSuccess(action: (T) -> Unit): ApiResponse<T> {
    if (success) data?.let(action)
    return this
}

inline fun <T> ApiResponse<T>.onError(action: (String) -> Unit): ApiResponse<T> {
    if (!success) action(message ?: "未知错误")
    return this
}

fun <T> ApiResponse<T>.isSuccess(): Boolean = success && data != null
