package com.jczy.cyclone.club.mall.viewmodel

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jczy.cyclone.club.mall.model.Goods
import com.jczy.cyclone.club.mall.repository.MallRepository

class GoodsPagingSource(private val repository: MallRepository, private val categoryId: String?) : PagingSource<Int, Goods>() {
    companion object {
        private const val TAG = "GoodsPagingSource"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Goods> {
        val page = params.key ?: 1
        return try {
            Log.d(TAG, "加载商品列表: page=$page, pageSize=${params.loadSize}, categoryId=$categoryId")
            val response = repository.getGoodsList(page, params.loadSize, categoryId)
            Log.d(TAG, "商品列表响应: success=${response.success}, code=${response.code}, msg=${response.msg}, dataSize=${response.data?.size ?: 0}")
            if (response.success) {
                val goods: List<Goods> = response.data ?: emptyList()
                val nextPage = if (goods.size < params.loadSize) null else page + 1
                Log.d(TAG, "商品列表成功: ${goods.size} 条商品, nextPage=$nextPage")
                LoadResult.Page(
                    data = goods,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = nextPage
                )
            } else {
                Log.e(TAG, "商品列表业务失败: code=${response.code}, msg=${response.msg}")
                LoadResult.Error(Exception(response.msg ?: "加载失败"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "商品列表异常: ${e.message}", e)
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