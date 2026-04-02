package com.jczy.cyclone.club.mall.repository

import com.jczy.cyclone.club.mall.model.Goods
import com.jczy.cyclone.club.mall.model.Order
import com.jczy.cyclone.club.mall.model.Address
import com.jczy.cyclone.club.mall.model.Coupon
import com.jczy.cyclone.club.network.MallApi
import com.jczy.cyclone.club.network.RetrofitClient
import javax.inject.Inject

class MallRepository @Inject constructor() {
    private val api = RetrofitClient.createService<MallApi>()
    
    suspend fun getGoodsList(page: Int, pageSize: Int, categoryId: String? = null) = api.getGoodsList(page, pageSize, categoryId)
    suspend fun getGoodsDetail(id: String) = api.getGoodsDetail(id)
    suspend fun searchGoods(keyword: String, page: Int, pageSize: Int) = api.searchGoods(keyword, page, pageSize)
    suspend fun getCartList() = api.getCartList()
    suspend fun addToCart(goodsId: String, quantity: Int) = api.addToCart(goodsId, quantity)
    suspend fun deleteFromCart(id: String) = api.deleteFromCart(id)
    suspend fun updateCartQuantity(id: String, quantity: Int) = api.updateCartQuantity(id, quantity)
    suspend fun createOrder(addressId: String, goodsList: String, couponId: String? = null, payType: Int) = api.createOrder(addressId, goodsList, couponId, payType)
    suspend fun getOrderList(status: Int? = null, page: Int, pageSize: Int) = api.getOrderList(status, page, pageSize)
    suspend fun getOrderDetail(id: String) = api.getOrderDetail(id)
    suspend fun cancelOrder(id: String) = api.cancelOrder(id)
    suspend fun confirmOrder(id: String) = api.confirmOrder(id)
    suspend fun applyAfterSales(orderId: String, reason: String) = api.applyAfterSales(orderId, reason)
    suspend fun getAddressList() = api.getAddressList()
    suspend fun addAddress(name: String, phone: String, province: String, city: String, district: String, detail: String, isDefault: Boolean) = api.addAddress(name, phone, province, city, district, detail, isDefault)
    suspend fun editAddress(id: String, name: String, phone: String, province: String, city: String, district: String, detail: String, isDefault: Boolean) = api.editAddress(id, name, phone, province, city, district, detail, isDefault)
    suspend fun deleteAddress(id: String) = api.deleteAddress(id)
    suspend fun getCouponList() = api.getCouponList()
    suspend fun getBeanInfo() = api.getBeanInfo()
    suspend fun getCollectList(page: Int, pageSize: Int) = api.getCollectList(page, pageSize)
    suspend fun collectGoods(goodsId: String) = api.collectGoods(goodsId)
    suspend fun cancelCollect(goodsId: String) = api.cancelCollect(goodsId)
}