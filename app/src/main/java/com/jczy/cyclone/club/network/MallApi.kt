package com.jczy.cyclone.club.network

import com.jczy.cyclone.club.network.ApiResponse
import com.jczy.cyclone.club.mall.model.Goods
import com.jczy.cyclone.club.mall.model.Order
import com.jczy.cyclone.club.mall.model.Address
import com.jczy.cyclone.club.mall.model.Coupon
import retrofit2.http.GET
import retrofit2.http.Query

interface MallApi {
    // 获取商品列表
    @GET("api/v1/goods/list")
    suspend fun getGoodsList(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("categoryId") categoryId: String? = null
    ): ApiResponse<List<Goods>>
    
    // 获取商品详情
    @GET("api/v1/goods/detail")
    suspend fun getGoodsDetail(@Query("id") id: String): ApiResponse<Goods>
    
    // 搜索商品
    @GET("api/v1/goods/search")
    suspend fun searchGoods(
        @Query("keyword") keyword: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): ApiResponse<List<Goods>>
    
    // 获取购物车列表
    @GET("api/v1/cart/list")
    suspend fun getCartList(): ApiResponse<List<Any>>
    
    // 添加到购物车
    @GET("api/v1/cart/add")
    suspend fun addToCart(
        @Query("goodsId") goodsId: String,
        @Query("quantity") quantity: Int
    ): ApiResponse<Boolean>
    
    // 从购物车删除
    @GET("api/v1/cart/delete")
    suspend fun deleteFromCart(@Query("id") id: String): ApiResponse<Boolean>
    
    // 更新购物车数量
    @GET("api/v1/cart/update")
    suspend fun updateCartQuantity(
        @Query("id") id: String,
        @Query("quantity") quantity: Int
    ): ApiResponse<Boolean>
    
    // 创建订单
    @GET("api/v1/order/create")
    suspend fun createOrder(
        @Query("addressId") addressId: String,
        @Query("goodsList") goodsList: String,
        @Query("couponId") couponId: String? = null,
        @Query("payType") payType: Int
    ): ApiResponse<String> // 返回订单 ID
    
    // 获取订单列表
    @GET("api/v1/order/list")
    suspend fun getOrderList(
        @Query("status") status: Int? = null,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): ApiResponse<List<Order>>
    
    // 获取订单详情
    @GET("api/v1/order/detail")
    suspend fun getOrderDetail(@Query("id") id: String): ApiResponse<Order>
    
    // 取消订单
    @GET("api/v1/order/cancel")
    suspend fun cancelOrder(@Query("id") id: String): ApiResponse<Boolean>
    
    // 确认收货
    @GET("api/v1/order/confirm")
    suspend fun confirmOrder(@Query("id") id: String): ApiResponse<Boolean>
    
    // 申请售后
    @GET("api/v1/order/afterSales")
    suspend fun applyAfterSales(
        @Query("orderId") orderId: String,
        @Query("reason") reason: String
    ): ApiResponse<Boolean>
    
    // 获取收货地址列表
    @GET("api/v1/address/list")
    suspend fun getAddressList(): ApiResponse<List<Address>>
    
    // 添加收货地址
    @GET("api/v1/address/add")
    suspend fun addAddress(
        @Query("name") name: String,
        @Query("phone") phone: String,
        @Query("province") province: String,
        @Query("city") city: String,
        @Query("district") district: String,
        @Query("detail") detail: String,
        @Query("isDefault") isDefault: Boolean
    ): ApiResponse<Boolean>
    
    // 编辑收货地址
    @GET("api/v1/address/edit")
    suspend fun editAddress(
        @Query("id") id: String,
        @Query("name") name: String,
        @Query("phone") phone: String,
        @Query("province") province: String,
        @Query("city") city: String,
        @Query("district") district: String,
        @Query("detail") detail: String,
        @Query("isDefault") isDefault: Boolean
    ): ApiResponse<Boolean>
    
    // 删除收货地址
    @GET("api/v1/address/delete")
    suspend fun deleteAddress(@Query("id") id: String): ApiResponse<Boolean>
    
    // 获取优惠券列表
    @GET("api/v1/coupon/list")
    suspend fun getCouponList(): ApiResponse<List<Coupon>>
    
    // 获取豆豆（积分）信息
    @GET("api/v1/bean/info")
    suspend fun getBeanInfo(): ApiResponse<Any>
    
    // 获取收藏列表
    @GET("api/v1/collect/list")
    suspend fun getCollectList(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): ApiResponse<List<Goods>>
    
    // 收藏商品
    @GET("api/v1/collect/add")
    suspend fun collectGoods(@Query("goodsId") goodsId: String): ApiResponse<Boolean>
    
    // 取消收藏
    @GET("api/v1/collect/delete")
    suspend fun cancelCollect(@Query("goodsId") goodsId: String): ApiResponse<Boolean>
}