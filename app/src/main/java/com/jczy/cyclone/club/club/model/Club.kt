package com.jczy.cyclone.club.club.model

import java.io.Serializable

class Club : Serializable {
    var id: String = ""
    var name: String = ""
    var logo: String = ""
    var description: String = ""
    var ownerId: String = ""
    var ownerName: String = ""
    var memberCount: Int = 0
    var activityCount: Int = 0
    var score: Float = 0f
    var createTime: Long = 0
    var isJoined: Boolean = false
}