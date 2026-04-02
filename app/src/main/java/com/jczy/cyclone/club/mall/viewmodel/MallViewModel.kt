package com.jczy.cyclone.club.mall.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jczy.cyclone.club.common.UiState
import com.jczy.cyclone.club.common.launchWithState
import com.jczy.cyclone.club.mall.model.Address
import com.jczy.cyclone.club.mall.model.Coupon
import com.jczy.cyclone.club.mall.model.Goods
import com.jczy.cyclone.club.mall.model.Order
import com.jczy.cyclone.club.mall.repository.MallRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MallViewModel @Inject constructor(
    private val repository: MallRepository
) : ViewModel() {

    // 商品列表（分页）
    fun getGoodsList(categoryId: String? = null): Flow<PagingData<Goods>> =
        Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { GoodsPagingSource(repository, categoryId) }
        ).flow.cachedIn(viewModelScope)

    // 商品搜索（分页）
    fun searchGoods(keyword: String): Flow<PagingData<Goods>> =
        Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { GoodsSearchPagingSource(repository, keyword) }
        ).flow.cachedIn(viewModelScope)

    // 订单列表（分页）
    fun getOrderList(status: Int? = null): Flow<PagingData<Order>> =
        Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { OrderPagingSource(repository, status) }
        ).flow.cachedIn(viewModelScope)

    // 商品详情状态
    private val _goodsDetailState = MutableStateFlow<UiState<Goods>>(UiState.Loading)
    val goodsDetailState: StateFlow<UiState<Goods>> = _goodsDetailState.asStateFlow()

    // 操作状态
    private val _actionState = MutableStateFlow<UiState<Boolean>>(UiState.Success(false))
    val actionState: StateFlow<UiState<Boolean>> = _actionState.asStateFlow()

    // 地址列表状态
    private val _addressListState = MutableStateFlow<UiState<List<Address>>>(UiState.Loading)
    val addressListState: StateFlow<UiState<List<Address>>> = _addressListState.asStateFlow()

    // 优惠券列表状态
    private val _couponListState = MutableStateFlow<UiState<List<Coupon>>>(UiState.Loading)
    val couponListState: StateFlow<UiState<List<Coupon>>> = _couponListState.asStateFlow()

    // 创建订单结果状态
    private val _createOrderState = MutableStateFlow<UiState<String>>(UiState.Loading)
    val createOrderState: StateFlow<UiState<String>> = _createOrderState.asStateFlow()

    fun fetchGoodsDetail(id: String) {
        launchWithState(_goodsDetailState) {
            repository.getGoodsDetail(id)
        }
    }

    fun addToCart(goodsId: String, quantity: Int) {
        launchWithState(_actionState) {
            repository.addToCart(goodsId, quantity)
        }
    }

    fun createOrder(addressId: String, goodsList: String, couponId: String? = null, payType: Int) {
        launchWithState(_createOrderState) {
            repository.createOrder(addressId, goodsList, couponId, payType)
        }
    }

    fun fetchAddressList() {
        launchWithState(_addressListState) {
            repository.getAddressList()
        }
    }

    fun fetchCouponList() {
        launchWithState(_couponListState) {
            repository.getCouponList()
        }
    }

    fun collectGoods(goodsId: String) {
        launchWithState(_actionState) {
            repository.collectGoods(goodsId)
        }
    }

    fun cancelCollect(goodsId: String) {
        launchWithState(_actionState) {
            repository.cancelCollect(goodsId)
        }
    }

    fun resetActionState() {
        _actionState.value = UiState.Success(false)
    }
}
