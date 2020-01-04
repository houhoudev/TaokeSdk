package com.taoke.sdk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.houhoudev.common.base.navactivity.NavEntity;
import com.houhoudev.common.constants.Res;
import com.houhoudev.common.eventbus.EventBusUtils;
import com.houhoudev.common.eventbus.EventMessage;
import com.houhoudev.common.update.UpdateUtils;
import com.houhoudev.common.utils.ToastUtils;
import com.houhoudev.store.ui.home.find.FindFragment;
import com.houhoudev.store.ui.store.search_result.view.SearchResultPopupWindow;
import com.houhoudev.store.utils.StoreSdk;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 */
public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    protected BottomNavigationBar mNavBar;

    protected List<Fragment> mFragments;

    protected int mNavPosition = 0;

    protected List<NavEntity> mNavEntitys = new ArrayList<>();

    // 检测商品标题
    private SearchResultPopupWindow mSearchResultPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_nav);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSearchResultPopupWindow.onResume();
    }

    private void init() {
        initView();
        initListener();

        mSearchResultPopupWindow = new SearchResultPopupWindow(this, getWindow().getDecorView());
        // 检查软件更新
        new UpdateUtils().check(this);
    }

    protected void initView() {
        mNavBar = findViewById(R.id.act_main_navigation);

        // 初始化fragment
        mFragments = new ArrayList<>();
        initFrag();
        // 初始化nav
        initNav();
    }

    protected void initListener() {

    }

    /**
     * 初始化frag
     */
    protected void initFrag() {
        mFragments.add(new SdkFragment());

        // 首页fragment
        Bundle bundle = new Bundle();
        bundle.putString("hot_name", "今日上新");// 横向商品列表标题文字

        // 排序：0.综合（最新），1.券后价(低到高)，2.券后价（高到低），3.券面额（高到低），4.月销量（高到低），
        // 5.佣金比例（高到低），9.全天销量（高到低），11.近2小时销量（高到低）
        bundle.putString("hot_sort", "0");// 横向商品列表排序方式：
        bundle.putString("recommend_sort", "11");// 为你推荐/商品分类 列表排序
        bundle.putInt("span", 1);// 商品列表默认每行显示商品个数：传1或2

        mFragments.add(StoreSdk.getMainFrag(bundle));

        // 分类fragment
        mFragments.add(StoreSdk.getClassifyFrag());
        // 榜单fragment
        mFragments.add(StoreSdk.getRankingFrag());
        // 发现fragment
        mFragments.add(new FindFragment());
        // 我的fragment
        mFragments.add(StoreSdk.getMineFrag());

        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < mFragments.size(); i++) {
            tr.add(R.id.act_main_fl, mFragments.get(i), "f" + i).hide(mFragments.get(i));
        }
        tr.show(mFragments.get(0)).commit();
    }

    /**
     * 初始化navButton
     */
    private void initNav() {
        mNavEntitys.add(new NavEntity(
                Res.getStr(R.string.sdk),
                R.drawable.icon_help,
                R.drawable.icon_help_dark));
        mNavEntitys.add(new NavEntity(
                Res.getStr(R.string.jingxuan),
                R.drawable.icon_featured_select,
                R.drawable.icon_featured_unselect));
        mNavEntitys.add(new NavEntity(
                Res.getStr(R.string.fenlei),
                R.drawable.icon_classify_select,
                R.drawable.icon_classify_unselect));
        mNavEntitys.add(new NavEntity(
                Res.getStr(R.string.bangdan),
                R.drawable.icon_ranking_select,
                R.drawable.icon_ranking_unselect));
        mNavEntitys.add(new NavEntity(
                Res.getStr(R.string.faxian),
                R.drawable.icon_com_select,
                R.drawable.icon_com_unselect));
        mNavEntitys.add(new NavEntity(
                Res.getStr(R.string.wode),
                R.drawable.icon_mine_select,
                R.drawable.icon_mine_unselect));


        mNavBar.setMode(BottomNavigationBar.MODE_FIXED);
        // 按下有水波
        mNavBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        // 正常字体颜色
        mNavBar.setInActiveColor(R.color.font_gray);
        // 选中字体颜色
        mNavBar.setActiveColor(R.color.font_main_red);
        // 背景颜色
        mNavBar.setBarBackgroundColor(R.color.white);
        // 阴影高度
        // navigationBar.setElevation(24);
        mNavBar.hide(false);//关闭动画效果
        mNavBar.show(false);//关闭动画效果

        for (int i = 0; i < mNavEntitys.size(); i++) {
            NavEntity navEntity = mNavEntitys.get(i);
            BottomNavigationItem bt =
                    new BottomNavigationItem(navEntity.getSelectDrwable(), navEntity.getTitle());
            bt.setInactiveIconResource(navEntity.getUnSelectDrwable());
            mNavBar.addItem(bt);
        }
        mNavBar.initialise();

        mNavBar.setTabSelectedListener(this);

    }

    /**
     * nav选中时调用
     */
    @Override
    public void onTabSelected(int position) {
        mNavPosition = position;
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.show(mFragments.get(position)).commit();
    }

    /**
     * nav去选时调用
     */
    @Override
    public void onTabUnselected(int position) {
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.hide(mFragments.get(position)).commit();
    }

    /**
     * nav选中时点击
     */
    @Override
    public void onTabReselected(int position) {
        if (position == 1) {
            // MainFrag置顶
            EventMessage message = new EventMessage();
            message.type = "HOME_TOP";
            EventBusUtils.post(message);
        }
        if (position == 3) {
            // RankingFrag置顶
            EventMessage message = new EventMessage();
            message.type = "RANK_TOP";
            EventBusUtils.post(message);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫一扫处理
        StoreSdk.onErCodeResult(this, requestCode, resultCode, data);
    }

    // 记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    ToastUtils.show("再按一次退出程序");
                    firstTime = secondTime;
                    return true;
                }
                finish();
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // super.onSaveInstanceState(outState);
    }
}