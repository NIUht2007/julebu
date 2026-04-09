package com.jczy.cyclone.club.network

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * 商城 API（奇墨云 OpenApi 网关）
 * BASE_URL = http://test-zsjc-openapi.qimoyun.com/
 *
 * 所有请求统一 POST 到 gateway.do，由 @OpenApiService 注解指定 service 类型。
 * OpenApiHeaderInterceptor 会自动注入：service / token / requestNo / version / partnerId / 签名。
 *
 * 商城登录接口（sysLoginSmsAuto）获取到 accessKey/secretKey/token 后
 * 存入 AuthMMKV.openApiAccessToken / openApiAccessKey / openApiSecretKey。
 */
interface MallApi {

    // ==================== 商城登录（获取 OpenApi token） ====================

    /** 使用主业务 token 自动登录商城，获取商城独立的 accessKey/secretKey/token */
    @OpenApiService(service = "sysLoginSmsAuto")
    @POST("gateway.do")
    suspend fun sysLoginSmsAuto(@Body body: RequestBody): MallLoginResponse

    // ==================== 首页 / Banner ====================

    /** 首页分组（一级车型目录） */
    @OpenApiService(service = "mallGroupIndexList")
    @POST("gateway.do")
    suspend fun mallGroupIndexList(): MallBaseResponse

    /** 首页商品分组及列表（购车/改装/周边文创） */
    @OpenApiService(service = "mallGoodsGroupList")
    @POST("gateway.do")
    suspend fun mallGoodsGroupList(): MallBaseResponse

    /** 商城 Banner/广告 */
    @OpenApiService(service = "cmallAdInfo")
    @POST("gateway.do")
    suspend fun cmallAdInfo(@Body body: RequestBody): MallBaseResponse

    /** 商城版块配置（热门活动/品推荐/秒杀） */
    @OpenApiService(service = "cmallConfList")
    @POST("gateway.do")
    suspend fun cmallConfList(): MallBaseResponse

    // ==================== 商品 ====================

    /** c端商品分页列表（通用） */
    @OpenApiService(service = "mallGoodsInfoPageList")
    @POST("gateway.do")
    suspend fun mallGoodsInfoPageList(@Body body: RequestBody): MallBaseResponse

    /** 首页顶部目录按组查询商品 */
    @OpenApiService(service = "mallGroupGoodsList")
    @POST("gateway.do")
    suspend fun mallGroupGoodsList(@Body body: RequestBody): MallBaseResponse

    /** 商品详情 */
    @OpenApiService(service = "mallGoodsInfoInfo")
    @POST("gateway.do")
    suspend fun mallGoodsInfoInfo(@Body body: RequestBody): MallBaseResponse

    /** 搜索 - 热门关键词 */
    @OpenApiService(service = "goodsHotSearchList")
    @POST("gateway.do")
    suspend fun goodsHotSearchList(): MallBaseResponse

    /** 搜索 - 关键词列表 */
    @OpenApiService(service = "goodsSearchKeywordList")
    @POST("gateway.do")
    suspend fun goodsSearchKeywordList(@Body body: RequestBody): MallBaseResponse

    /** 商品评价列表 */
    @OpenApiService(service = "goodsEvaluateList")
    @POST("gateway.do")
    suspend fun goodsEvaluateList(@Body body: RequestBody): MallBaseResponse

    /** 商品评价统计 */
    @OpenApiService(service = "goodsEvaluateInfo")
    @POST("gateway.do")
    suspend fun goodsEvaluateInfo(@Body body: RequestBody): MallBaseResponse

    /** 推荐商品（订单详情/支付成功后） */
    @OpenApiService(service = "cMallRecommendGoodsList")
    @POST("gateway.do")
    suspend fun cMallRecommendGoodsList(@Body body: RequestBody): MallBaseResponse

    // ==================== 购物车 ====================

    /** 添加购物车 */
    @OpenApiService(service = "orderShoppingCartAdd")
    @POST("gateway.do")
    suspend fun orderShoppingCartAdd(@Body body: RequestBody): MallBaseResponse

    /** 删除购物车商品 */
    @OpenApiService(service = "orderShoppingCartDelete")
    @POST("gateway.do")
    suspend fun orderShoppingCartDelete(@Body body: RequestBody): MallBaseResponse

    /** 购物车列表 */
    @OpenApiService(service = "orderShoppingCartList")
    @POST("gateway.do")
    suspend fun orderShoppingCartList(@Body body: RequestBody): MallBaseResponse

    /** 购物车商品总数量 */
    @OpenApiService(service = "orderShoppingCartNum")
    @POST("gateway.do")
    suspend fun orderShoppingCartNum(): MallBaseResponse

    // ==================== 订单 ====================

    /** 提交订单 */
    @OpenApiService(service = "orderCreate")
    @POST("gateway.do")
    suspend fun orderCreate(@Body body: RequestBody): MallBaseResponse

    /** 订单列表（分页） */
    @OpenApiService(service = "orderInfoPageList")
    @POST("gateway.do")
    suspend fun orderInfoPageList(@Body body: RequestBody): MallBaseResponse

    /** 订单详情 */
    @OpenApiService(service = "orderInfoDetail")
    @POST("gateway.do")
    suspend fun orderInfoDetail(@Body body: RequestBody): MallBaseResponse

    /** 取消订单 */
    @OpenApiService(service = "orderInfoCancel")
    @POST("gateway.do")
    suspend fun orderInfoCancel(@Body body: RequestBody): MallBaseResponse

    /** 确认收货 */
    @OpenApiService(service = "orderInfoReceive")
    @POST("gateway.do")
    suspend fun orderInfoReceive(@Body body: RequestBody): MallBaseResponse

    /** 申请售后/退款 */
    @OpenApiService(service = "orderApplyAfterSales")
    @POST("gateway.do")
    suspend fun orderApplyAfterSales(@Body body: RequestBody): MallBaseResponse

    /** 订单支付（收银台提交） */
    @OpenApiService(service = "orderPayCash")
    @POST("gateway.do")
    suspend fun orderPayCash(@Body body: RequestBody): MallBaseResponse

    /** 查询支付状态 */
    @OpenApiService(service = "orderPayStatus")
    @POST("gateway.do")
    suspend fun orderPayStatus(@Body body: RequestBody): MallBaseResponse

    // ==================== 收货地址 ====================

    /** 地址列表 */
    @OpenApiService(service = "memberShopAddressList")
    @POST("gateway.do")
    suspend fun memberShopAddressList(): MallBaseResponse

    /** 新增地址 */
    @OpenApiService(service = "memberShopAddressCreate")
    @POST("gateway.do")
    suspend fun memberShopAddressCreate(@Body body: RequestBody): MallBaseResponse

    /** 修改地址 */
    @OpenApiService(service = "memberShopAddressUpdate")
    @POST("gateway.do")
    suspend fun memberShopAddressUpdate(@Body body: RequestBody): MallBaseResponse

    /** 删除地址 */
    @OpenApiService(service = "memberShopAddressDelete")
    @POST("gateway.do")
    suspend fun memberShopAddressDelete(@Body body: RequestBody): MallBaseResponse

    // ==================== 收藏 ====================

    /** 加入收藏 */
    @OpenApiService(service = "goodsFavoriteAdd")
    @POST("gateway.do")
    suspend fun goodsFavoriteAdd(@Body body: RequestBody): MallBaseResponse

    /** 取消收藏（收藏列表） */
    @OpenApiService(service = "goodsFavoriteCancel")
    @POST("gateway.do")
    suspend fun goodsFavoriteCancel(@Body body: RequestBody): MallBaseResponse

    // ==================== 优惠券 ====================

    /** 优惠券分页列表 */
    @OpenApiService(service = "couponPageList")
    @POST("gateway.do")
    suspend fun couponPageList(@Body body: RequestBody): MallBaseResponse

    /** 下单选择优惠券 */
    @OpenApiService(service = "placeOrderCouponSelect")
    @POST("gateway.do")
    suspend fun placeOrderCouponSelect(@Body body: RequestBody): MallBaseResponse
}

/** 分页数据包装（商城接口常见格式，兼容 rows/records 两种字段名） */
data class MallPageData<T>(
    val rows: List<T>? = null,
    val records: List<T>? = null,
    val totalRows: Int = 0,
    val total: Long = 0,
    val size: Long = 0,
    val current: Long = 0,
    val pages: Long = 0
) {
    /** 优先取 rows，为空则取 records，确保兼容后端两种返回格式 */
    fun getItems(): List<T> = rows ?: records ?: emptyList()
}

/** 商城接口通用返回（原始 Map，由 Repository 层解析具体字段） */
data class MallBaseResponse(
    val code: String? = null,
    val success: Boolean = false,
    val message: String? = null,
    val detail: String? = null,
    val data: com.google.gson.JsonElement? = null
)

/** 商城登录接口返回 */
data class MallLoginResponse(
    val code: String? = null,
    val success: Boolean = false,
    val message: String? = null,
    val accessKey: String? = null,
    val secretKey: String? = null,
    val token: String? = null
)
