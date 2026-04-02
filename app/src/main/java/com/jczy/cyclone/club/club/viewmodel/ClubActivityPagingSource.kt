package com.jczy.cyclone.club.club.viewmodel

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jczy.cyclone.club.club.model.ClubActivity
import com.jczy.cyclone.club.club.repository.ClubRepository

class ClubActivityPagingSource(private val repository: ClubRepository, private val clubId: String) : PagingSource<Int, ClubActivity>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ClubActivity> {
        val page = params.key ?: 1
        return try {
            val response = repository.getClubActivityList(clubId, page, params.loadSize)
            if (response.success) {
                val activities: List<ClubActivity> = response.data ?: emptyList()
                val nextPage = if (activities.size < params.loadSize) null else page + 1
                LoadResult.Page(
                    data = activities,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = nextPage
                )
            } else {
                LoadResult.Error(Exception(response.message ?: "加载失败"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
    
    override fun getRefreshKey(state: PagingState<Int, ClubActivity>): Int? {
        return state.anchorPosition?.let {anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}