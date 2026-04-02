package com.jczy.cyclone.club.club.model

import java.io.Serializable

class ClubActivity : Serializable {
    var id: String = ""
    var clubId: String = ""
    var title: String = ""
    var description: String = ""
    var startTime: Long = 0
    var endTime: Long = 0
    var location: String = ""
    var joinCount: Int = 0
    var createTime: Long = 0
    var isJoined: Boolean = false
}