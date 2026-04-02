package com.jczy.cyclone.club.motocircle.viewmodel

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jczy.cyclone.club.motocircle.model.Article
import com.jczy.cyclone.club.motocircle.repository.MotoCircleRepository

class ArticlePagingSource(private val repository: MotoCircleRepository) : PagingSource<Int, Article>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        return try {
            val response = repository.getArticleList(page, params.loadSize)
            if (response.success) {
                val articles: List<Article> = response.data ?: emptyList()
                val nextPage = if (articles.size < params.loadSize) null else page + 1
                LoadResult.Page(
                    data = articles,
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
    
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}