package com.jczy.cyclone.club.motocircle.viewmodel

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jczy.cyclone.club.motocircle.model.Post
import com.jczy.cyclone.club.motocircle.repository.MotoCircleRepository

class PostPagingSource(private val repository: MotoCircleRepository) : PagingSource<Int, Post>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        val page = params.key ?: 1
        return try {
            val response = repository.getPostList(page, params.loadSize)
            if (response.success) {
                val posts: List<Post> = response.data ?: emptyList()
                val nextPage = if (posts.size < params.loadSize) null else page + 1
                LoadResult.Page(
                    data = posts,
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
    
    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let {anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}