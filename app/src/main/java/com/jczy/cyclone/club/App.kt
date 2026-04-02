package com.jczy.cyclone.club

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.mmkv.MMKV
import com.tencent.bugly.crashreport.CrashReport
import com.umeng.commonsdk.UMConfigure
import com.amap.api.maps.MapsInitializer
import cn.jpush.android.api.JPushInterface
import dagger.hilt.android.HiltAndroidApp
import com.jczy.cyclone.club.im.IMManager

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // 初始化 ARouter
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
        
        // 初始化 MMKV
        MMKV.initialize(this)
        
        // 初始化 IM SDK
        IMManager.init(this)
        
        // 初始化极光推送
        initJPush()
        
        // 初始化友盟统计
        initUMeng()
        
        // 初始化 Bugly
        initBugly()
        
        // 初始化高德地图
        initAMap()
    }
    
    private fun initJPush() {
        JPushInterface.setDebugMode(BuildConfig.DEBUG)
        JPushInterface.init(this)
    }
    
    private fun initUMeng() {
        UMConfigure.setLogEnabled(BuildConfig.DEBUG)
        UMConfigure.init(
            this,
            "your_umeng_appkey",
            "developer-default",
            UMConfigure.DEVICE_TYPE_PHONE,
            ""
        )
        UMConfigure.submitPolicyGrantResult(this, true)
    }
    
    private fun initBugly() {
        CrashReport.initCrashReport(this, "your_bugly_appid", BuildConfig.DEBUG)
    }
    
    private fun initAMap() {
        MapsInitializer.updatePrivacyShow(this, true, true)
        MapsInitializer.updatePrivacyAgree(this, true)
    }
    
    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
    }
}

