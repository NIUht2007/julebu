package com.jczy.cyclone.club.mall.model

import java.io.Serializable

class Coupon : Serializable {
    var id: String = ""
    var name: String = ""
    var type: Int = 0 // 0: 满减券, 1: 折扣券
    var value: Double = 0.0 // 满减金额或折扣率
    var minAmount: Double = 0.0 // 最低使用金额
    var startTime: Long = 0
    var endTime: Long = 0
    var status: Int = 0 // 0: 未使用, 1: 已使用, 2: 已过期
}