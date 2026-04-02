package com.jczy.cyclone.club.motocircle.model

import java.io.Serializable

class Guide : Serializable {
    var id: String = ""
    var title: String = ""
    var author: String = ""
    var cover: String = ""
    var description: String = ""
    var distance: Double = 0.0
    var duration: Long = 0
    var startPoint: String = ""
    var endPoint: String = ""
    var waypoints: List<String> = emptyList()
    var likeCount: Int = 0
    var commentCount: Int = 0
    var createTime: Long = 0
}