package com.jczy.cyclone.club.mall.model

import java.io.Serializable

/**
 * 商品数据模型
 *
 * 字段对齐参考项目 com.jczy.cyclone.model.MallGoods（MallModels.kt L815）
 * OpenApi 接口返回的商品分页数据（mallGoodsInfoPageList / mallGroupGoodsList 等）
 */
data class Goods(
    /** 主键 id（与 goodsId 通常相同） */
    var id: Long = 0L,
    val goodsId: Long = 0,

    // ---- 图片 ----
    /** 主图 id */
    val mainImgId: Long = 0,
    /** 主图 URL（列表封面） */
    var mainImgUrl: String? = null,
    var picUrl: String? = null,
    var goodsMainPhoto: String? = null,

    // ---- 价格 ----
    /** 划线价（市场价） */
    var marketPriceFee: Double = 0.0,
    /** 购买价（实付） */
    var purchasePrice: Double = 0.0,
    /** 龙豆价 */
    var price: Double = 0.0,
    /** 定金 */
    var deposit: Double = 0.0,

    // ---- 基础信息 ----
    /** 商品标题 */
    var title: String? = null,
    /** 商品描述 */
    var desc: String? = null,
    /** 月销量 */
    var soldNum: Long = 0,
    var monthSalesNum: Long = 0,
    /** 库存 */
    var stocks: Int = 0,
    /** 商品类型 */
    var productType: String? = "",

    // ---- 详情相关 ----
    val brandName: String? = null,
    val code: String? = null,
    val detailUrl: List<String>? = null,
    val videoUrl: List<String>? = null,
    val skuList: List<MallSkuItem>? = null,
    val skuItem: MallSkuItem? = null,
    val specAttrList: List<GoodsAttrOptions>? = null,
    val details: String? = null,
    val params: String? = null,
    val posterUll: List<String>? = null,
    val tlineCode: String? = null,
    val tlineName: String? = null,
    val videoImgUrl: String? = null,

    // ---- SKU ----
    val skuId: Long = 0,
    val skuName: String? = null,

    // ---- 其他 ----
    var longDou: Int = 0,
    var count: Int = 1,
    val discount: Int = 0,
    var stateName: String? = "",
    val post: Boolean = false,
    val ifVirtual: Boolean = false,
) : Serializable {

    /** 封面图（按优先级取第一个有效值） */
    fun coverUrl(): String =
        mainImgUrl?.takeIf { it.isNotBlank() }
            ?: goodsMainPhoto?.takeIf { it.isNotBlank() }
            ?: picUrl?.takeIf { it.isNotBlank() }
            ?: ""

    /** 展示标题（兼容 title 字段） */
    fun displayTitle(): String = title?.takeIf { it.isNotBlank() } ?: desc ?: ""

    /** 展示价格（实付价，单位分→元） */
    fun displayPrice(): Double = if (purchasePrice > 0) purchasePrice else price

    /** 展示原价 */
    fun displayOriginalPrice(): Double = marketPriceFee
}

/**
 * SKU 条目（与参考项目 MallSkuItem 对齐）
 */
data class MallSkuItem(
    val id: Long = 0,
    val skuName: String? = null,
    val price: Double = 0.0,
    val marketPriceFee: Double = 0.0,
    val stocks: Int = 0,
    val pointStock: Int = 0,
    val mainImgUrl: String? = null,
) : Serializable {
    fun priceInt(): String = price.toLong().toString()
    fun priceFloat(): String {
        val cent = ((price * 100).toLong() % 100)
        return if (cent == 0L) "00" else cent.toString().padStart(2, '0')
    }
}

/**
 * 规格属性选项
 */
data class GoodsAttrOptions(
    val id: Long = 0,
    val attrKeyId: Long = 0,
    val attrValue: String? = null,
    val fileUrl: String? = null,
    var checked: Boolean = false,
    var enable: Boolean = true,
) : Serializable
