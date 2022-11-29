package com.taoke.sdk;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.houhoudev.common.constants.Res;
import com.houhoudev.common.utils.LogUtils;
import com.houhoudev.common.utils.ToastUtils;

public class ServiceActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        init();
    }

    private void init() {
        findViewById(R.id.act_service_close).setOnClickListener(this);
        findViewById(R.id.act_service_tv_cloud).setOnClickListener(this);
        findViewById(R.id.act_service_tv_qq).setOnClickListener(this);
        findViewById(R.id.act_service_tv_wechat).setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_service_close:
                finish();
                break;
            case R.id.act_service_tv_cloud:
                nextCloud();
                break;
            case R.id.act_service_tv_qq:
                nextQQ();
                break;
            case R.id.act_service_tv_wechat:
                nextWeChat();
                break;
        }
    }

    private void nextWeChat() {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText("lijunjie8579");
        ToastUtils.show(Res.getStr(R.string.fuzhichenggong));
    }

    private void nextQQ() {
        try {
            String url = "mqqwpa://im/chat?chat_type=wpa&uin=2276280645";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            LogUtils.e(e);
            ToastUtils.show("您未安装qq");
        }
    }

    private void nextCloud() {
        String url = "https://promotion.aliyun.com/ntms/yunparter/invite.html?userCode=yutwrx9n";
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        startActivity(intent);
    }
}