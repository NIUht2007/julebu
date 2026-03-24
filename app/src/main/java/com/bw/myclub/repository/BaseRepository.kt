package com.bw.myclub.repository

import com.bw.myclub.network.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseRepository {
    protected suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResponse<T> {
        return withContext(Dispatchers.IO) {
            try {
                val result = apiCall()
                ApiResponse.Success(result)
            } catch (e: Exception) {
                ApiResponse.Error(e)
            }
        }
    }
}
