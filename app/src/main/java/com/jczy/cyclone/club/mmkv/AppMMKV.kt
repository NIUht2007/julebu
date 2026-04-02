package com.jczy.cyclone.club.mmkv

import com.tencent.mmkv.MMKV

object AppMMKV {
    private val mmkv = MMKV.mmkvWithID("app")
    
    var lastLaunchTime: Long
        get() = mmkv.getLong("lastLaunchTime", 0)
        set(value) { mmkv.putLong("lastLaunchTime", value) }
    
    var isFirstLaunch: Boolean
        get() = mmkv.getBoolean("isFirstLaunch", true)
        set(value) { mmkv.putBoolean("isFirstLaunch", value) }
    
    var messageSettings: Map<String, Boolean>?
        get() {
            val json = mmkv.getString("messageSettings", null)
            // 这里可以使用 Gson 或 Moshi 解析 JSON
            return null
        }
        set(value) {
            // 这里可以使用 Gson 或 Moshi 序列化 Map
        }
    
    fun clear() {
        mmkv.clearAll()
    }
}
