
@[TOC](淘宝客SDK，一键导入淘宝客商城，快速实现流量变现)
<font size=6 color=#FF0000>特别注意：旧版本停止维护，需尽快升级到v1.2.3版本</font>
# 一、Demo项目地址
- github地址：[https://github.com/houhoudev/TaokeSdk](https://github.com/houhoudev/TaokeSdk)
- 部分接口文档：[https://www.showdoc.cc/348614373887448?page_id=2006667515972703](https://www.showdoc.cc/348614373887448?page_id=2006667515972703)

- demo下载：[立即下载](https://houhoudev.oss-cn-shenzhen.aliyuncs.com/downLoad/TaokeSdk.apk)

- 扫码下载

![在这里插入图片描述](https://img-blog.csdnimg.cn/20191016170031792.png)
# 二、关于SDK

 - 支持淘宝授权登录、免登录
 - 一键接入各种商城模块
 - 部署自己的服务器，实现用户返利
 - 一键配置淘宝客推广位，赚取收益
 - 体积小，增量约为5M左右
# 三、接入前准备
- 1、注册淘宝联盟，获取推广位id，注册链接：[https://pub.alimama.com/](https://pub.alimama.com/)
- 2、注册阿里百川，获取电商权限、安全图片，注册链接：[https://baichuan.taobao.com/](https://baichuan.taobao.com/)
# 四、全局参数配置
Module下build.gradle中配置

```java
defaultConfig {

	///////

	manifestPlaceholders = [
	scheme : "sdk", // 协议，自己定义一个任意字符串即可
		product: 1 // 产品id，邀请好友，软件更新等地方需要用到，需要在我们后台配置
	]
}
```
AndroidManifest.xml配置

```xml
<!--配置FileProvider-->
<provider
	android:name="android.support.v4.content.FileProvider"
	android:authorities="您的包名.fileprovider"
	android:exported="false"
	android:grantUriPermissions="true">
		<meta-data
			android:name="android.support.FILE_PROVIDER_PATHS"
			android:resource="@xml/file_paths" />
</provider>

<!--友盟 若项目中需要集成友盟统计，加入如下配置-->
<meta-data
	android:name="UMENG_APPKEY"
	android:value="友盟id"
	tools:replace="android:value" />
```
file_paths.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<paths xmlns:android="http://schemas.android.com/apk/res/android">
	<external-files-path
		name="external-files"
		path="/" />
	<external-cache-path
		name="external-cache"
		path="/" />
</paths>
```

# 五、接入SDK
## 1、组件说明
 - 导入组件


```java
// 商城基础
implementation 'com.houhoudev:store:2.0.0.210709'
// 用户基础
implementation 'com.houhoudev:user:2.0.0.210709'
// 扫描二维码
implementation 'com.houhoudev:zxing:2.0.0.210709'
```
- 组件中已经包含了如下组件，请勿重复导入
```java
// 图片缓存
implementation 'com.github.bumptech.glide:glide:4.8.0'
annotationProcessor 'com.github.bumptech.glide:compiler:4.8.0'
// gson解析
implementation 'com.google.code.gson:gson:2.8.2'
// 友盟统计
implementation 'com.umeng.umsdk:analytics:8.0.0'
implementation 'com.umeng.umsdk:common:2.0.0'
// OKHttp
implementation 'com.squareup.okhttp3:okhttp:4.8.1'
// RecyclerViewAdapter
implementation 'com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.31'
// EventBus
implementation ('org.greenrobot:eventbus:3.1.1')
```
- 如果无法下载组件，请在工程build.gradle中添加以下仓库

```java
repositories {
	// 阿里百川
	maven { url "http://repo.baichuan-android.taobao.com/content/groups/BaichuanRepositories/" }
	// 友盟
	maven { url 'https://repo1.maven.org/maven2/' }
	// 商城
	maven { url 'https://houhoudev.oss-cn-shenzhen.aliyuncs.com/repository/' }
}

```
## 2、SDK调用

-  首先需要在application中初始sdk


```java
// 初始化sdk，在application中调用
StoreSdk.initApplication(this);
// 以下代码在用户同意隐私协议后调用
String pid = "mm_118568524_485000365_109457800416"; // 淘宝联盟pid
String adzoneid = "109457800416"; // pid最后一段
String unionId = ""; // 联盟id（一般传""）
String appKey = "26313026"; // 淘宝联盟appKey
boolean isRebate = false; // 是否带返利，返利功能需要部署自己的服务器
StoreSdk.init(pid, adzoneid, unionId, appKey, isRebate);
```
-  模块、页面调用
```java
// 首页fragment
Bundle bundle = new Bundle();
bundle.putString("hot_name", "今日上新");// 横向商品列表标题文字
// 排序：0.综合（最新），1.券后价(低到高)，2.券后价（高到低），3.券面额（高到低），4.月销量（高到低），
// 5.佣金比例（高到低），9.全天销量（高到低），11.近2小时销量（高到低）
bundle.putString("hot_sort", "0");// 横向商品列表排序方式：
bundle.putString("recommend_sort", "11");// 为你推荐/商品分类 列表排序
bundle.putInt("span", 1);// 商品列表默认每行显示商品个数：传1或2
Fragment fragment = StoreSdk.getMainFrag(bundle);
```
```java
// 我的订单activity（需配合返利功能使用）
StoreSdk.startOrderAct(getActivity());
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201013170725636.jpg)

```java
// 跳转首页Activity
StoreSdk.startMainAct(getActivity());
```
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200104103913925.jpg)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;![在这里插入图片描述](https://img-blog.csdnimg.cn/20200104104028279.jpg)

```java
// 检测商品标题（在首页Activity中检测）
private SearchResultPopupWindow mSearchResultPopupWindow;
private void init() {
	// 初始对象
	mSearchResultPopupWindow = new SearchResultPopupWindow(this, getWindow().getDecorView());
}
@Override
protected void onResume() {
	super.onResume();
	// 首页每次获得焦点时检测，检测到商品标题时弹出
	mSearchResultPopupWindow.onResume();
}
```
```java
// 扫一扫activity
StoreSdk.startErCode(this);

// onActivity中处理
@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	// 扫一扫处理
	StoreSdk.onErCodeResult(this, requestCode, resultCode, data);
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200104105257727.jpg)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;![在这里插入图片描述](https://img-blog.csdnimg.cn/2020010410534398.jpg)
```java
// 分类fragment
Fragment classifyfrag = StoreSdk.getClassifyFrag();
// 跳转分类Activity
StoreSdk.startClassifygAct(this);
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200104110213213.jpg) &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;![在这里插入图片描述](https://img-blog.csdnimg.cn/2020010411020340.jpg)
```java
// 榜单Fragment
Fragment rankingFrag = StoreSdk.getRankingFrag();
// 跳转榜单Activity
StoreSdk.startClassifygAct(this);
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200104111539782.jpg)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;![在这里插入图片描述](https://img-blog.csdnimg.cn/20200104111531747.jpg)
```java
// 发现Fragment
Frament propleFrag = StoreSdk.getPeopleFrag();
// 发现Activity
StoreSdk.startPeopleAct(this);
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200104111658915.jpg)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;![在这里插入图片描述](https://img-blog.csdnimg.cn/20200104111657551.jpg)
```java
// 品牌fragment
Fragment brandFrag = StoreSdk.getBrandFrag());
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201013164713649.jpg)

```java
// 我的Fragment
Fragment mineFrag = StoreSdk.getMineFrag();
// 跳转我的Activity
StoreSdk.startMineAct(this);
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200104111927151.jpg)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;![在这里插入图片描述](https://img-blog.csdnimg.cn/2020010411185677.jpg)
```java
// 跳转金币Activity
StoreSdk.startCoinsAct(this);
// 邀请好友Activity
StoreSdk.startFriends(this);
```
```java
// 商品详情Activity
StoreSdk.startGoodDetail(this, 521422451240L);
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200104111808339.jpg)
```java
// 我的收藏Activity
StoreSdk.startCollection(this);
// 我的足迹Acivity
StoreSdk.startHistory(this);
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200104112132874.jpg)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;![在这里插入图片描述](https://img-blog.csdnimg.cn/20200104112102426.jpg)
```java
// 系统消息Activity
StoreSdk.startMessageAct(this);

// 购物车Activity
StoreSdk.startCarts(this);

// 登录Activity
// 判断是否登录
if (!StoreSdk.isLogin()) {
	// 跳转登录页面
	StoreSdk.startLogin(getActivity());
}

// 每日签到
if (StoreSdk.isLogin()) {
	StoreSdk.sign();
}
```

```java
// 查询用户信息接口
StoreSdk.userInfo(new HttpCallBack() {
	@Override
	public void onResponse(HttpResult result) {
		if (result.isSuccess()){
			ToastUtils.show(result.getData());
		}
	}

	@Override
	public void onFailure(int code) {
		// 出错
	}
});
```

```java

{
    "coinsBalance":105302, // 金币余额
    "coinsDay":128, // 今日金币
    "messageCount":0, // 未读消息条数
    "code":"QQQW", // 邀请码
    "coinsMonth":1024, // 本月金币
    "recommend_id1":0, // 邀请人id
    "name":"小小小小木木夕", // 昵称
    "photo":"http://gw.alicdn.com/tps/i3/TB1yeWeIFXXXXX5XFXXuAZJYXXX-210-210.png_160x160.jpg", // 头像
    "isSign":true // 是否已签到
}
```
- 发送消息事件

```java
// mainFrag置顶
EventMessage message = new EventMessage();
message.type = "HOME_TOP";
EventBusUtils.post(message);
```

```java
// rankingFrag置顶
EventMessage message = new EventMessage();
message.type = "RANK_TOP";
EventBusUtils.post(message);
```
- 接收消息事件

```java
// 需要在接收事件的类中注册和取消注册事件

// 注册事件
EventBusUtils.register(this);

// 取消注册事件
EventBusUtils.unregister(this);
```

```java
// 在类添加订阅事件
@Subscribe
public void onReceiveMessage(EventMessage message) {
	if ("GET_COINS_SUCCESS".equals(message.type)) {
		// 签到、浏览商品、每日签到等获得金币通知 做刷新用户信息操作
		ToastUtils.show("签到成功");
	}

	if ("LOGIN_SUCCESS".equals(message.type)) {
		// 登陆成功 做刷新用户信息操作
		ToastUtils.show("登录成功");
	}

	if ("EXIT_LOGIN".equals(message.type)) {
		// 退出登录成功 做清除用户信息操作
		ToastUtils.show("退出成功");
	}
}
```
- 软件更新

```java
// 检查软件更新，需要在我们后台配置
new UpdateUtils().check(this);
```
# 六、混淆

```java
# OkHttp
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn com.squareup.okhttp.**
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okio.**

# EventBus
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

# glide图片缓存
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class com.bumptech.glide.** { *; }
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

# RecyclerAdapter
-keep class com.chad.library.adapter.** {
    *;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}
-keepattributes InnerClasses

# Gson
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod

# 友盟umeng
-keep class com.umeng.** {*;}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 阿里百川
-keepattributes Signature
-ignorewarnings
-keep class com.houhoudev.** {*;}
-keepclassmembers class com.houhoudev.** {*;}
-keep class javax.ws.rs.** { *; }
-keep class com.alibaba.fastjson.** { *; }
-dontwarn com.alibaba.fastjson.**
-keep class sun.misc.Unsafe { *; }
-dontwarn sun.misc.**
-keep class com.taobao.** {*;}
-keep class com.alibaba.** {*;}
-keep class com.alipay.** {*;}
-dontwarn com.taobao.**
-dontwarn com.alibaba.**
-dontwarn com.alipay.**
-keep class com.ut.** {*;}
-dontwarn com.ut.**
-keep class com.ta.** {*;}
-dontwarn com.ta.**
-keep class org.json.** {*;}
-keep class com.ali.auth.**  {*;}
-dontwarn com.ali.auth.**
-keep class com.taobao.securityjni.** {*;}
-keep class com.taobao.wireless.security.** {*;}
-keep class com.taobao.dp.**{*;}
-keep class com.alibaba.wireless.security.**{*;}
-keep interface mtopsdk.mtop.global.init.IMtopInitTask {*;}
-keep class * implements mtopsdk.mtop.global.init.IMtopInitTask {*;}
```
# 七、更新日志
- v2.0.0.210709（2021-07-09）
升级androidx
预初始化，合规化
重构、性能优化
- v1.2.3（2020-10-13）
新增返利功能（需部署自己的服务器）
新增我的订单功能（需配合返利使用）
新增品牌模块
- v1.0.9（2020-01-04）
新增发现Fragment、Activity
新增首页商品标题检测
修改首页Fragment参数定制
- v1.0.3（2019-10-30）
新增商品视频详情功能
新增首页活动弹窗、悬浮入口
优化金币提现功能
- v1.0.2（2019-10-15）
首个版本
