package com.taoke.sdk

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.houhoudev.store.utils.StoreSdk
import com.olive.config.MiniProgramConfig

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        initStoreSdk()
    }

    private fun initStoreSdk() {
        // ARouter路由初始化
        ARouter.init(this)

        // 初始化sdk，在application中调用
        StoreSdk.initApplication(this)

        // 以下代码在用户同意隐私协议后调用
        val pid = "mm_118568524_485000365_109457800416" // 淘宝联盟pid
        val adZoneId = "109457800416" // pid最后一段
        val unionId = "" // 联盟id（一般传""）
        val appKey = "26313026" // 淘宝联盟appKey
        val isRebate = false // 是否带返利，返利功能需要部署自己的服务器
        StoreSdk.init(pid, adZoneId, unionId, appKey, isRebate)

        // 初始化小程序
        MiniProgramConfig.init(this, null)
    }
}