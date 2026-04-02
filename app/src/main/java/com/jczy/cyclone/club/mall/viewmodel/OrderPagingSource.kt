package com.jczy.cyclone.club.mall.viewmodel

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jczy.cyclone.club.mall.model.Order
import com.jczy.cyclone.club.mall.repository.MallRepository

class OrderPagingSource(private val repository: MallRepository, private val status: Int?) : PagingSource<Int, Order>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Order> {
        val page = params.key ?: 1
        return try {
            val response = repository.getOrderList(status, page, params.loadSize)
            if (response.success) {
                val orders: List<Order> = response.data ?: emptyList()
                val nextPage = if (orders.size < params.loadSize) null else page + 1
                LoadResult.Page(
                    data = orders,
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
    
    override fun getRefreshKey(state: PagingState<Int, Order>): Int? {
        return state.anchorPosition?.let {anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}