package com.jczy.cyclone.club.motocircle.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jczy.cyclone.club.common.UiState
import com.jczy.cyclone.club.common.launchWithState
import com.jczy.cyclone.club.motocircle.model.Article
import com.jczy.cyclone.club.motocircle.model.Guide
import com.jczy.cyclone.club.motocircle.model.Post
import com.jczy.cyclone.club.motocircle.repository.MotoCircleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MotoCircleViewModel @Inject constructor(
    private val repository: MotoCircleRepository
) : ViewModel() {

    // 动态列表（分页）
    val postList: Flow<PagingData<Post>> = Pager(
        config = PagingConfig(pageSize = 10, enablePlaceholders = false),
        pagingSourceFactory = { PostPagingSource(repository) }
    ).flow.cachedIn(viewModelScope)

    // 文章列表（分页）
    val articleList: Flow<PagingData<Article>> = Pager(
        config = PagingConfig(pageSize = 10, enablePlaceholders = false),
        pagingSourceFactory = { ArticlePagingSource(repository) }
    ).flow.cachedIn(viewModelScope)

    // 攻略列表（分页）
    val guideList: Flow<PagingData<Guide>> = Pager(
        config = PagingConfig(pageSize = 10, enablePlaceholders = false),
        pagingSourceFactory = { GuidePagingSource(repository) }
    ).flow.cachedIn(viewModelScope)

    // 动态详情状态
    private val _postDetailState = MutableStateFlow<UiState<Post>>(UiState.Loading)
    val postDetailState: StateFlow<UiState<Post>> = _postDetailState.asStateFlow()

    // 操作状态
    private val _actionState = MutableStateFlow<UiState<Boolean>>(UiState.Success(false))
    val actionState: StateFlow<UiState<Boolean>> = _actionState.asStateFlow()

    fun fetchPostDetail(id: String) {
        launchWithState(_postDetailState) {
            repository.getPostDetail(id)
        }
    }

    fun likePost(id: String) {
        launchWithState(_actionState) {
            repository.likePost(id)
        }
    }

    fun commentPost(id: String, content: String) {
        launchWithState(_actionState) {
            repository.commentPost(id, content)
        }
    }

    fun sharePost(id: String) {
        launchWithState(_actionState) {
            repository.sharePost(id)
        }
    }

    fun resetActionState() {
        _actionState.value = UiState.Success(false)
    }
}
