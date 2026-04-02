package com.jczy.cyclone.club.mall.viewmodel

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jczy.cyclone.club.mall.model.Goods
import com.jczy.cyclone.club.mall.repository.MallRepository

class GoodsPagingSource(private val repository: MallRepository, private val categoryId: String?) : PagingSource<Int, Goods>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Goods> {
        val page = params.key ?: 1
        return try {
            val response = repository.getGoodsList(page, params.loadSize, categoryId)
            if (response.success) {
                val goods: List<Goods> = response.data ?: emptyList()
                val nextPage = if (goods.size < params.loadSize) null else page + 1
                LoadResult.Page(
                    data = goods,
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
    
    override fun getRefreshKey(state: PagingState<Int, Goods>): Int? {
        return state.anchorPosition?.let {anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}