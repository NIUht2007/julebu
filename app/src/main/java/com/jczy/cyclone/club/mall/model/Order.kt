package com.jczy.cyclone.club.mall.model

import java.io.Serializable

class Order : Serializable {
    var id: String = ""
    var orderNo: String = ""
    var totalPrice: Double = 0.0
    var actualPrice: Double = 0.0
    var status: Int = 0 // 0: 待付款, 1: 待发货, 2: 待收货, 3: 已完成, 4: 已取消, 5: 售后中
    var createTime: Long = 0
    var payTime: Long = 0
    var shipTime: Long = 0
    var confirmTime: Long = 0
    var addressId: String = ""
    var address: Address? = null
    var goodsList: List<OrderGoods> = emptyList()
    var payType: Int = 0 // 0: 微信支付, 1: 支付宝, 2: 豆豆支付
    var couponId: String = ""
    var couponAmount: Double = 0.0
    var beanAmount: Int = 0
}

class OrderGoods : Serializable {
    var goodsId: String = ""
    var goodsName: String = ""
    var goodsImage: String = ""
    var price: Double = 0.0
    var quantity: Int = 0
}