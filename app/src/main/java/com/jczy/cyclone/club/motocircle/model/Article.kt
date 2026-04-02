package com.jczy.cyclone.club.motocircle.model

import java.io.Serializable

class Article : Serializable {
    var id: String = ""
    var title: String = ""
    var author: String = ""
    var cover: String = ""
    var content: String = ""
    var readCount: Int = 0
    var likeCount: Int = 0
    var commentCount: Int = 0
    var createTime: Long = 0
}