package com.jczy.cyclone.club.motocircle.viewmodel

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jczy.cyclone.club.motocircle.model.Guide
import com.jczy.cyclone.club.motocircle.repository.MotoCircleRepository

class GuidePagingSource(private val repository: MotoCircleRepository) : PagingSource<Int, Guide>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Guide> {
        val page = params.key ?: 1
        return try {
            val response = repository.getGuideList(page, params.loadSize)
            if (response.success) {
                val guides: List<Guide> = response.data ?: emptyList()
                val nextPage = if (guides.size < params.loadSize) null else page + 1
                LoadResult.Page(
                    data = guides,
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
    
    override fun getRefreshKey(state: PagingState<Int, Guide>): Int? {
        return state.anchorPosition?.let {anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}