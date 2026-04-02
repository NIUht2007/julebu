package com.jczy.cyclone.club.mall.model

import java.io.Serializable

class Address : Serializable {
    var id: String = ""
    var name: String = ""
    var phone: String = ""
    var province: String = ""
    var city: String = ""
    var district: String = ""
    var detail: String = ""
    var isDefault: Boolean = false
}