package com.taoke.sdk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.BarUtils;
import com.houhoudev.common.base.base.BaseFragment;
import com.houhoudev.common.constants.Res;
import com.houhoudev.common.eventbus.EventBusUtils;
import com.houhoudev.common.eventbus.EventMessage;
import com.houhoudev.common.network.HttpCallBack;
import com.houhoudev.common.network.HttpResult;
import com.houhoudev.common.utils.ToastUtils;
import com.houhoudev.store.utils.StoreSdk;

import org.greenrobot.eventbus.Subscribe;

public class SdkFragment extends BaseFragment implements View.OnClickListener {
    @Override
    protected int onCreateContentViewId() {
        return R.layout.frag_sdk;
    }

    @Override
    public void onResume() {
        super.onResume();
        onShow();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        onShow();
    }

    private void onShow() {
        if (!isHidden()) {
            // 设置状态栏文字颜色为黑色
            BarUtils.setNavBarLightMode(requireActivity(), true);
            // 设置状态栏文字颜色为白色
            // StatusBarUtils.setDarkMode(getActivity());
            // 设置状态栏背景颜色
            BarUtils.setStatusBarColor(requireActivity(), Res.getColor(R.color.statusBarColor));
        }
    }

    @Override
    protected void initFirst() {
        super.initFirst();
        EventBusUtils.register(this);
    }

    @Override
    protected void initView() {
        showContentView();
    }

    @Override
    protected void initListener() {
        addClickListener(this, R.id.frag_sdk_btn_order);
        addClickListener(this, R.id.frag_sdk_btn_main);
        addClickListener(this, R.id.frag_sdk_btn_classify);
        addClickListener(this, R.id.frag_sdk_btn_ranking);
        addClickListener(this, R.id.frag_sdk_btn_people);
        addClickListener(this, R.id.frag_sdk_btn_mine);

        addClickListener(this, R.id.frag_sdk_btn_glod);
        addClickListener(this, R.id.frag_sdk_btn_friends);
        addClickListener(this, R.id.frag_sdk_btn_gooddetail);
        addClickListener(this, R.id.frag_sdk_btn_collectiion);
        addClickListener(this, R.id.frag_sdk_btn_history);
        addClickListener(this, R.id.frag_sdk_btn_message);
        addClickListener(this, R.id.frag_sdk_btn_ercode);
        addClickListener(this, R.id.frag_sdk_btn_cart);
        addClickListener(this, R.id.frag_sdk_btn_sign);
        addClickListener(this, R.id.frag_sdk_btn_userinfo);
        addClickListener(this, R.id.frag_sdk_btn_exit);

        addClickListener(this, R.id.frag_sdk_sction_service);
    }

    @Override
    protected void initData() {

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frag_sdk_btn_order:
                // 我的订单activity（需配合返利功能使用）
                StoreSdk.startOrderAct(getActivity());
                break;
            case R.id.frag_sdk_btn_main:
                // 跳转首页activity
                StoreSdk.startMainAct(requireActivity());
                break;
            case R.id.frag_sdk_btn_classify:
                // 跳转分类activity
                StoreSdk.startClassifygAct(getActivity());
                break;
            case R.id.frag_sdk_btn_ranking:
                // 跳转榜单activity
                StoreSdk.startRankingAct(getActivity());
                break;
            case R.id.frag_sdk_btn_people:
                // 跳转发现activity
                StoreSdk.startPeopleAct(requireActivity());
                break;
            case R.id.frag_sdk_btn_mine:
                // 跳转我的activity
                StoreSdk.startMineAct(getActivity());
                break;
            case R.id.frag_sdk_btn_glod:
                // 跳转金币activity
                StoreSdk.startCoinsAct(getActivity());
                break;
            case R.id.frag_sdk_btn_friends:
                // 邀请好友activity
                StoreSdk.startFriends(getActivity());
                break;
            case R.id.frag_sdk_btn_gooddetail:
                // 商品详情activity
                StoreSdk.startGoodDetail(getActivity(), 521422451240L);
                break;
            case R.id.frag_sdk_btn_collectiion:
                // 我的收藏activity
                StoreSdk.startCollection(requireActivity());
                break;
            case R.id.frag_sdk_btn_history:
                // 历史记录activity
                StoreSdk.startHistory(requireActivity());
                break;
            case R.id.frag_sdk_btn_message:
                // 系统消息activity
                StoreSdk.startMessageAct(getActivity());
                break;
            case R.id.frag_sdk_btn_ercode:
                // 扫一扫activity
                // onActivity中处理：StoreSdk.onErCodeResult(activity, requestCode, resultCode, data);
                StoreSdk.startErCode(this);
                break;
            case R.id.frag_sdk_btn_cart:
                // 购物车activity
                StoreSdk.startCarts(getActivity());
                break;
            case R.id.frag_sdk_btn_sign:
                // 每日签到接口
                if (StoreSdk.isLogin()) {
                    StoreSdk.sign();
                } else {
                    // 跳转登录页面
                    StoreSdk.startLogin(requireActivity());
                }
                break;
            case R.id.frag_sdk_btn_userinfo:
                // 用户信息接口
                requestUserInfo();
                break;
            case R.id.frag_sdk_btn_exit:
                StoreSdk.exitLogin();
                break;
            case R.id.frag_sdk_sction_service:
                startActivity(new Intent(getActivity(), ServiceActivity.class));
                break;
        }
    }

    /**
     * 用户信息接口
     */
    private void requestUserInfo() {
        if (!StoreSdk.isLogin()) {
            ToastUtils.show("用户未登录");
            return;
        }
        StoreSdk.userInfo(new HttpCallBack() {
            @Override
            public void onResponse(HttpResult result) {
                if (result.isSuccess())
                    ToastUtils.show(result.data());
            }

            @Override
            public void onFailure(int i) {

            }
        });
    }

    @Subscribe
    public void onReceiveMessage(EventMessage message) {
        // 接收消息
        if ("GET_COINS_SUCCESS".equals(message.type)) {
            // 签到、浏览商品、每日签到等获得金币通知 做刷新用户信息操作
            ToastUtils.show("签到成功");
        }
        if ("LOGIN_SUCCESS".equals(message.type)) {
            // 登陆成功 做刷新用户信息操作
            ToastUtils.show("登录成功");
        }
        if ("EXIT_LOGIN".equals(message.type)) {
            // 退出成功 做清除用户信息操作
            ToastUtils.show("退出成功");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        StoreSdk.onErCodeResult(requireActivity(), requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        EventBusUtils.unregister(this);
        super.onDestroy();
    }
}