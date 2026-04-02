package com.jczy.cyclone.club.motocircle.model

import java.io.Serializable

class Post : Serializable {
    var id: String = ""
    var userId: String = ""
    var username: String = ""
    var avatar: String = ""
    var content: String = ""
    var images: List<String> = emptyList()
    var video: String = ""
    var likeCount: Int = 0
    var commentCount: Int = 0
    var shareCount: Int = 0
    var isLiked: Boolean = false
    var createTime: Long = 0
}