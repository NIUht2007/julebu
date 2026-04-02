package com.jczy.cyclone.club.club.viewmodel

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jczy.cyclone.club.club.model.Club
import com.jczy.cyclone.club.club.repository.ClubRepository

class ClubPagingSource(private val repository: ClubRepository) : PagingSource<Int, Club>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Club> {
        val page = params.key ?: 1
        return try {
            val response = repository.getClubList(page, params.loadSize)
            if (response.success) {
                val clubs: List<Club> = response.data ?: emptyList()
                val nextPage = if (clubs.size < params.loadSize) null else page + 1
                LoadResult.Page(
                    data = clubs,
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
    
    override fun getRefreshKey(state: PagingState<Int, Club>): Int? {
        return state.anchorPosition?.let {anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}