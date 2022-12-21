package com.taoke.sdk;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.houhoudev.store.utils.StoreSdk;
import com.olive.config.MiniProgramConfig;

public class MainApplicationJava extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        initStoreSdk();
    }

    private void initStoreSdk() {
        // ARouter路由初始化
        ARouter.init(this);

        // 初始化sdk，在application中调用
        StoreSdk.initApplication(this);

        // 以下代码在用户同意隐私协议后调用
        String pid = "mm_118568524_485000365_109457800416"; // 淘宝联盟pid
        String adZoneId = "109457800416"; // pid最后一段
        String unionId = ""; // 联盟id（一般传""）
        String appKey = "26313026"; // 淘宝联盟appKey
        boolean isRebate = false; // 是否带返利，返利功能需要部署自己的服务器
        StoreSdk.init(pid, adZoneId, unionId, appKey, isRebate);

        // 初始化小程序
        MiniProgramConfig.INSTANCE.init(this, null);
    }
}
