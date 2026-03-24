package com.bw.myclub.network

sealed class ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error(val exception: Exception) : ApiResponse<Nothing>()
    object Loading : ApiResponse<Nothing>()
}
