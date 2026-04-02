package com.jczy.cyclone.club.mmkv

import com.tencent.mmkv.MMKV

object AuthMMKV {
    private val mmkv = MMKV.mmkvWithID("auth")
    
    var token: String?
        get() = mmkv.getString("token", null)
        set(value) { mmkv.putString("token", value) }
    
    var isLogin: Boolean
        get() = mmkv.getBoolean("isLogin", false)
        set(value) { mmkv.putBoolean("isLogin", value) }
    
    fun clear() {
        mmkv.clearAll()
    }
}
