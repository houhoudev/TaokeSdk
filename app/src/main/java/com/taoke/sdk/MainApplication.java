package com.taoke.sdk;

import android.app.Application;
import com.houhoudev.store.utils.StoreSdk;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        initStoreSdk();
    }

    private void initStoreSdk() {
        String pid = "mm_118568524_485000365_109457800416"; // 淘宝联盟pid
        String adzoneid = "109457800416"; // pid最后一段
        String unionId = ""; // 联盟id（一般传""）
        String appKey = "26313026"; // 淘宝联盟appKey
        StoreSdk.init(this, pid, adzoneid, unionId, appKey);// 初始化sdk
    }
}