package com.jczy.cyclone.club.mall.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jczy.cyclone.club.mall.model.Address
import com.jczy.cyclone.club.mall.model.Coupon
import com.jczy.cyclone.club.mall.model.Goods
import com.jczy.cyclone.club.mall.model.Order
import com.jczy.cyclone.club.mmkv.AuthMMKV
import com.jczy.cyclone.club.network.ApiResponse
import com.jczy.cyclone.club.network.MallApi
import com.jczy.cyclone.club.network.MallBaseResponse
import com.jczy.cyclone.club.network.MallPageData
import com.jczy.cyclone.club.network.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject

/**
 * 商城 Repository
 *
 * ⚠️ 重要：商城使用奇墨云独立 OpenApi 鉴权体系。
 * 在调用任何商城接口前，需要先调用 [loginMall] 获取商城独立的
 * accessKey / secretKey / token，存入 AuthMMKV，后续请求由
 * OpenApiHeaderInterceptor 自动注入签名。
 *
 * 建议在用户登录主业务成功后，立即调用一次 [loginMall]。
 */
class MallRepository @Inject constructor() {

    private val api = RetrofitClient.createMallService<MallApi>()
    private val gson = Gson()

    // --------- 工具函数 ---------

    private fun jsonBody(vararg pairs: Pair<String, Any?>): RequestBody {
        val json = JSONObject()
        pairs.forEach { (k, v) -> if (v != null) json.put(k, v) }
        return json.toString().toRequestBody("application/json".toMediaTypeOrNull())
    }

    private fun emptyBody(): RequestBody =
        "{}".toRequestBody("application/json".toMediaTypeOrNull())

    // --------- 首页 ---------

    suspend fun mallGroupIndexList() = api.mallGroupIndexList()

    suspend fun mallGoodsGroupList() = api.mallGoodsGroupList()

    suspend fun cmallAdInfo(terminal: String = "APP", area: String = "") =
        api.cmallAdInfo(jsonBody("terminal" to terminal, "area" to area))

    suspend fun cmallConfList() = api.cmallConfList()

    // --------- 商品 ---------

    suspend fun mallGoodsInfoPageList(
        start: Int,
        limit: Int = 10,
        groupId: String? = null,
        keyword: String? = null
    ) = api.mallGoodsInfoPageList(
        jsonBody("start" to start, "limit" to limit, "groupId" to groupId, "keyword" to keyword)
    )

    suspend fun mallGroupGoodsList(groupId: String, start: Int, limit: Int = 10) =
        api.mallGroupGoodsList(jsonBody("groupId" to groupId, "start" to start, "limit" to limit))

    suspend fun mallGoodsInfoInfo(goodsId: String) =
        api.mallGoodsInfoInfo(jsonBody("goodsId" to goodsId))

    suspend fun goodsHotSearchList() = api.goodsHotSearchList()

    suspend fun goodsSearchKeywordList(keyword: String) =
        api.goodsSearchKeywordList(jsonBody("keyword" to keyword))

    suspend fun goodsEvaluateList(goodsId: String, start: Int, limit: Int = 10) =
        api.goodsEvaluateList(jsonBody("goodsId" to goodsId, "start" to start, "limit" to limit))

    // --------- 购物车 ---------

    suspend fun orderShoppingCartAdd(goodsId: String, skuId: String, num: Int) =
        api.orderShoppingCartAdd(jsonBody("goodsId" to goodsId, "skuId" to skuId, "num" to num))

    suspend fun orderShoppingCartDelete(cartIds: List<String>) =
        api.orderShoppingCartDelete(jsonBody("cartIds" to cartIds.joinToString(",")))

    suspend fun orderShoppingCartList(start: Int = 1, limit: Int = 100) =
        api.orderShoppingCartList(jsonBody("start" to start, "limit" to limit))

    suspend fun orderShoppingCartNum() = api.orderShoppingCartNum()

    // --------- 订单 ---------

    suspend fun orderCreate(
        addressId: String,
        cartIds: String,
        couponId: String? = null,
        remark: String? = null
    ) = api.orderCreate(
        jsonBody("addressId" to addressId, "cartIds" to cartIds, "couponId" to couponId, "remark" to remark)
    )

    suspend fun orderInfoPageList(
        start: Int,
        limit: Int = 10,
        orderStatus: String? = null  // 状态：unpaid/paid/delivered/finished/closed
    ) = api.orderInfoPageList(
        jsonBody("start" to start, "limit" to limit, "orderStatus" to orderStatus)
    )

    suspend fun orderInfoDetail(orderCode: String) =
        api.orderInfoDetail(jsonBody("orderCode" to orderCode))

    suspend fun orderInfoCancel(orderCode: String) =
        api.orderInfoCancel(jsonBody("orderCode" to orderCode))

    suspend fun orderInfoReceive(orderCode: String) =
        api.orderInfoReceive(jsonBody("orderCode" to orderCode))

    suspend fun orderApplyAfterSales(orderCode: String, reason: String, type: String = "refund") =
        api.orderApplyAfterSales(jsonBody("orderCode" to orderCode, "reason" to reason, "type" to type))

    suspend fun orderPayCash(orderCode: String, payChannel: String = "WXPAY") =
        api.orderPayCash(jsonBody("orderCode" to orderCode, "payChannel" to payChannel))

    suspend fun orderPayStatus(orderCode: String) =
        api.orderPayStatus(jsonBody("orderCode" to orderCode))

    // --------- 收货地址 ---------

    suspend fun memberShopAddressList() = api.memberShopAddressList()

    suspend fun memberShopAddressCreate(
        name: String, phone: String,
        province: String, city: String, district: String, detail: String,
        isDefault: Boolean = false
    ) = api.memberShopAddressCreate(
        jsonBody("name" to name, "phone" to phone, "province" to province,
            "city" to city, "district" to district, "detail" to detail, "isDefault" to isDefault)
    )

    suspend fun memberShopAddressUpdate(
        id: String, name: String, phone: String,
        province: String, city: String, district: String, detail: String,
        isDefault: Boolean = false
    ) = api.memberShopAddressUpdate(
        jsonBody("id" to id, "name" to name, "phone" to phone, "province" to province,
            "city" to city, "district" to district, "detail" to detail, "isDefault" to isDefault)
    )

    suspend fun memberShopAddressDelete(id: String) =
        api.memberShopAddressDelete(jsonBody("id" to id))

    // --------- 收藏 ---------

    suspend fun goodsFavoriteAdd(goodsId: String) =
        api.goodsFavoriteAdd(jsonBody("goodsId" to goodsId))

    suspend fun goodsFavoriteCancel(goodsId: String) =
        api.goodsFavoriteCancel(jsonBody("goodsId" to goodsId))

    // --------- 优惠券 ---------

    suspend fun couponPageList(start: Int, limit: Int = 10, status: String? = null) =
        api.couponPageList(jsonBody("start" to start, "limit" to limit, "status" to status))

    suspend fun placeOrderCouponSelect(cartIds: String, addressId: String) =
        api.placeOrderCouponSelect(jsonBody("cartIds" to cartIds, "addressId" to addressId))

    // --------- 兼容旧 ViewModel / PagingSource 的过渡方法 ---------

    /**
     * 获取商品分页列表
     */
    suspend fun getGoodsList(page: Int, pageSize: Int, categoryId: String?): ApiResponse<List<Goods>> {
        val resp = mallGoodsInfoPageList(start = page, limit = pageSize, groupId = categoryId)
        return resp.parsePageData { json ->
            val type = object : TypeToken<MallPageData<Goods>>() {}.type
            gson.fromJson<MallPageData<Goods>>(json, type)
        }
    }

    suspend fun searchGoods(keyword: String, page: Int, pageSize: Int): ApiResponse<List<Goods>> {
        val resp = mallGoodsInfoPageList(start = page, limit = pageSize, keyword = keyword)
        return resp.parsePageData { json ->
            val type = object : TypeToken<MallPageData<Goods>>() {}.type
            gson.fromJson<MallPageData<Goods>>(json, type)
        }
    }

    suspend fun getGoodsDetail(goodsId: String): ApiResponse<Goods> {
        val resp = mallGoodsInfoInfo(goodsId)
        return resp.parseData()
    }

    suspend fun addToCart(goodsId: String, quantity: Int): ApiResponse<Boolean> {
        return ApiResponse.error("加入购物车还缺少 skuId，当前页面参数模型需要继续对齐商城接口")
    }

    suspend fun createOrder(
        addressId: String,
        goodsList: String,
        couponId: String? = null,
        payType: Int
    ): ApiResponse<String> {
        return ApiResponse.error("下单参数仍是旧版 goodsList/payType 结构，需继续对齐商城 cartIds/支付链路后才能提交订单")
    }

    suspend fun getOrderList(status: Int?, page: Int, pageSize: Int): ApiResponse<List<Order>> {
        val resp = orderInfoPageList(start = page, limit = pageSize, orderStatus = status.toMallOrderStatus())
        return resp.parsePageData { json ->
            val type = object : TypeToken<MallPageData<Order>>() {}.type
            gson.fromJson<MallPageData<Order>>(json, type)
        }
    }

    suspend fun getAddressList(): ApiResponse<List<Address>> {
        val resp = memberShopAddressList()
        return resp.parseListData()
    }

    suspend fun getCouponList(page: Int = 1, pageSize: Int = 20): ApiResponse<List<Coupon>> {
        val resp = couponPageList(start = page, limit = pageSize)
        return resp.parsePageData { json ->
            val type = object : TypeToken<MallPageData<Coupon>>() {}.type
            gson.fromJson<MallPageData<Coupon>>(json, type)
        }
    }

    suspend fun collectGoods(goodsId: String): ApiResponse<Boolean> =
        goodsFavoriteAdd(goodsId).toBooleanResponse()

    suspend fun cancelCollect(goodsId: String): ApiResponse<Boolean> =
        goodsFavoriteCancel(goodsId).toBooleanResponse()

    private fun MallBaseResponse.toBooleanResponse(): ApiResponse<Boolean> {
        return if (success) {
            ApiResponse.success(true)
        } else {
            ApiResponse.error(errorMessage(), code?.toIntOrNull() ?: -1)
        }
    }

    private inline fun <reified T> MallBaseResponse.parseListData(): ApiResponse<List<T>> {
        return when {
            !success -> ApiResponse.error(errorMessage(), code?.toIntOrNull() ?: -1)
            data == null || data.isJsonNull -> ApiResponse.success(emptyList())
            else -> {
                try {
                    val type = object : TypeToken<List<T>>() {}.type
                    val list: List<T> = gson.fromJson(data, type)
                    ApiResponse.success(list)
                } catch (e: Exception) {
                    ApiResponse.error("数据解析失败: ${e.message}")
                }
            }
        }
    }

    private fun MallBaseResponse.errorMessage(): String =
        detail?.takeIf { it.isNotBlank() }
            ?: message?.takeIf { it.isNotBlank() }
            ?: "请求失败"

    private fun Int?.toMallOrderStatus(): String? = when (this) {
        0 -> "unpaid"
        1 -> "paid"
        2 -> "delivered"
        3 -> "finished"
        4 -> "closed"
        else -> null
    }

    // --------- 响应解析工具 ---------

    /**
     * 解析分页数据响应
     * @param parser 将 data JsonElement 解析为 MallPageData<T> 的函数
     */
    private fun <T> MallBaseResponse.parsePageData(
        parser: (com.google.gson.JsonElement) -> MallPageData<T>
    ): ApiResponse<List<T>> {
        Log.d("MallRepo", "parsePageData: success=$success, code=$code, data=$data")
        return when {
            !success -> {
                Log.e("MallRepo", "parsePageData 业务失败: code=$code, message=$message, detail=$detail")
                ApiResponse.error(errorMessage(), code?.toIntOrNull() ?: -1)
            }
            data == null -> {
                Log.w("MallRepo", "parsePageData data为null, 返回空列表")
                ApiResponse.success(emptyList())
            }
            data.isJsonNull -> {
                Log.w("MallRepo", "parsePageData data为JsonNull, 返回空列表")
                ApiResponse.success(emptyList())
            }
            else -> {
                try {
                    val pageData = parser(data)
                    val items = pageData.getItems()
                    Log.d("MallRepo", "parsePageData 解析成功: rows=${pageData.rows?.size}, records=${pageData.records?.size}, items=${items.size}, total=${pageData.total}, totalRows=${pageData.totalRows}")
                    ApiResponse.success(items)
                } catch (e: Exception) {
                    Log.e("MallRepo", "parsePageData 解析异常: ${e.message}", e)
                    ApiResponse.error("数据解析失败: ${e.message}")
                }
            }
        }
    }

    /**
     * 解析单个对象数据响应
     */
    private inline fun <reified T> MallBaseResponse.parseData(): ApiResponse<T> {
        return when {
            !success -> ApiResponse.error(errorMessage(), code?.toIntOrNull() ?: -1)
            data == null || data.isJsonNull -> ApiResponse.error("数据为空")
            else -> {
                try {
                    val obj = gson.fromJson(data, T::class.java)
                    ApiResponse.success(obj)
                } catch (e: Exception) {
                    ApiResponse.error("数据解析失败: ${e.message}")
                }
            }
        }
    }
}
