package com.jczy.cyclone.club.club.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jczy.cyclone.club.club.model.Club
import com.jczy.cyclone.club.club.model.ClubActivity
import com.jczy.cyclone.club.club.repository.ClubRepository
import com.jczy.cyclone.club.common.UiState
import com.jczy.cyclone.club.common.launchWithState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ClubViewModel @Inject constructor(
    private val repository: ClubRepository
) : ViewModel() {

    // 俱乐部列表（分页）
    val clubList: Flow<PagingData<Club>> = Pager(
        config = PagingConfig(pageSize = 10, enablePlaceholders = false),
        pagingSourceFactory = { ClubPagingSource(repository) }
    ).flow.cachedIn(viewModelScope)

    // 俱乐部详情状态
    private val _clubDetailState = MutableStateFlow<UiState<Club>>(UiState.Loading)
    val clubDetailState: StateFlow<UiState<Club>> = _clubDetailState.asStateFlow()

    // 操作状态（创建、编辑、评分等）
    private val _actionState = MutableStateFlow<UiState<Boolean>>(UiState.Success(false))
    val actionState: StateFlow<UiState<Boolean>> = _actionState.asStateFlow()

    // 活动列表（分页）
    fun getClubActivityList(clubId: String): Flow<PagingData<ClubActivity>> = Pager(
        config = PagingConfig(pageSize = 10, enablePlaceholders = false),
        pagingSourceFactory = { ClubActivityPagingSource(repository, clubId) }
    ).flow.cachedIn(viewModelScope)

    // 获取俱乐部详情
    fun fetchClubDetail(id: String) {
        launchWithState(_clubDetailState) {
            repository.getClubDetail(id)
        }
    }

    // 创建俱乐部
    fun createClub(name: String, description: String, logo: String) {
        launchWithState(_actionState) {
            repository.createClub(name, description, logo)
        }
    }

    // 编辑俱乐部
    fun editClub(id: String, name: String, description: String, logo: String) {
        launchWithState(_actionState) {
            repository.editClub(id, name, description, logo)
        }
    }

    // 创建俱乐部活动
    fun createClubActivity(
        clubId: String,
        title: String,
        description: String,
        startTime: Long,
        endTime: Long,
        location: String
    ) {
        launchWithState(_actionState) {
            repository.createClubActivity(clubId, title, description, startTime, endTime, location)
        }
    }

    // 报名俱乐部活动
    fun joinClubActivity(id: String) {
        launchWithState(_actionState) {
            repository.joinClubActivity(id)
        }
    }

    // 俱乐部评分
    fun scoreClub(id: String, score: Float) {
        launchWithState(_actionState) {
            repository.scoreClub(id, score)
        }
    }

    // 重置操作状态
    fun resetActionState() {
        _actionState.value = UiState.Success(false)
    }

    // 活动详情状态
    private val _activityDetailState = MutableStateFlow<UiState<ClubActivity>>(UiState.Loading)
    val activityDetailState: StateFlow<UiState<ClubActivity>> = _activityDetailState.asStateFlow()

    // 获取俱乐部活动详情
    fun getClubActivityDetail(id: String) {
        launchWithState(_activityDetailState) {
            repository.getClubActivityDetail(id)
        }
    }
}
