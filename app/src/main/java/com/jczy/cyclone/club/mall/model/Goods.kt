package com.jczy.cyclone.club.mall.model

import java.io.Serializable

class Goods : Serializable {
    var id: String = ""
    var name: String = ""
    var price: Double = 0.0
    var originalPrice: Double = 0.0
    var image: String = ""
    var images: List<String> = emptyList()
    var description: String = ""
    var stock: Int = 0
    var sales: Int = 0
    var categoryId: String = ""
    var categoryName: String = ""
    var isSeckill: Boolean = false
    var seckillPrice: Double = 0.0
    var seckillStock: Int = 0
    var isCollected: Boolean = false
}