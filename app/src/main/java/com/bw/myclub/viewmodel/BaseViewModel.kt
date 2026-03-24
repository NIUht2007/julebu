package com.bw.myclub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bw.myclub.network.ApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    protected fun <T> launchRequest(
        request: suspend () -> ApiResponse<T>,
        onSuccess: (T) -> Unit,
        onError: (Exception) -> Unit = {}
    ) {
        viewModelScope.launch {
            when (val response = request()) {
                is ApiResponse.Success -> onSuccess(response.data)
                is ApiResponse.Error -> onError(response.exception)
                is ApiResponse.Loading -> {}
            }
        }
    }
}
