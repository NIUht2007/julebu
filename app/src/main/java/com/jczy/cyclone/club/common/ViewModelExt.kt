package com.jczy.cyclone.club.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jczy.cyclone.club.network.ApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel 扩展函数，用于简化 StateFlow 状态管理
 * 注意：Hilt 要求 @HiltViewModel 必须直接继承 ViewModel，所以不能使用 BaseViewModel 抽象类
 */

fun <T> ViewModel.launchWithState(
    stateFlow: MutableStateFlow<UiState<T>>,
    block: suspend () -> ApiResponse<T>
) {
    viewModelScope.launch {
        stateFlow.value = UiState.Loading
        try {
            val response = block()
            if (response.success) {
                response.data?.let {
                    stateFlow.value = UiState.Success(it)
                } ?: run {
                    stateFlow.value = UiState.Error("数据为空")
                }
            } else {
                stateFlow.value = UiState.Error(
                    response.message ?: "请求失败",
                    response.code
                )
            }
        } catch (e: Exception) {
            stateFlow.value = UiState.Error(
                e.message ?: "网络错误",
                -1
            )
        }
    }
}

fun <T> ViewModel.launchWithResult(
    stateFlow: MutableStateFlow<UiState<T>>,
    block: suspend () -> T
) {
    viewModelScope.launch {
        stateFlow.value = UiState.Loading
        try {
            val result = block()
            stateFlow.value = UiState.Success(result)
        } catch (e: Exception) {
            stateFlow.value = UiState.Error(
                e.message ?: "操作失败",
                -1
            )
        }
    }
}
