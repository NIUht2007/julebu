package com.jczy.cyclone.club.common

import com.jczy.cyclone.club.network.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseRepository {

    protected fun <T> apiCall(call: suspend () -> ApiResponse<T>): Flow<ApiResult<T>> = flow {
        emit(ApiResult.Loading)
        try {
            val response = call()
            if (response.success) {
                response.data?.let {
                    emit(ApiResult.Success(it))
                } ?: emit(ApiResult.Error("数据为空", response.code))
            } else {
                emit(ApiResult.Error(response.message ?: "请求失败", response.code))
            }
        } catch (e: Exception) {
            emit(ApiResult.Error(e.message ?: "网络错误", -1))
        }
    }.flowOn(Dispatchers.IO)

    protected suspend fun <T> apiCallSync(call: suspend () -> ApiResponse<T>): ApiResult<T> {
        return try {
            val response = call()
            if (response.success) {
                response.data?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Error("数据为空", response.code)
            } else {
                ApiResult.Error(response.message ?: "请求失败", response.code)
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "网络错误", -1)
        }
    }
}

sealed class ApiResult<out T> {
    data object Loading : ApiResult<Nothing>()
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String, val code: Int = -1) : ApiResult<Nothing>()
}
