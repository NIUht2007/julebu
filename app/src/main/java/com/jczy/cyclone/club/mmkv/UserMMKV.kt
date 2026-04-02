package com.jczy.cyclone.club.mmkv

import com.tencent.mmkv.MMKV

object UserMMKV {
    private val mmkv = MMKV.mmkvWithID("user")
    
    var userId: String?
        get() = mmkv.getString("userId", null)
        set(value) { mmkv.putString("userId", value) }
    
    var username: String?
        get() = mmkv.getString("username", null)
        set(value) { mmkv.putString("username", value) }
    
    var avatar: String?
        get() = mmkv.getString("avatar", null)
        set(value) { mmkv.putString("avatar", value) }
    
    var phone: String?
        get() = mmkv.getString("phone", null)
        set(value) { mmkv.putString("phone", value) }
    
    fun clear() {
        mmkv.clearAll()
    }
}
