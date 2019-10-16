# 一、Demo项目地址
github地址：[https://github.com/houhoudev/TaokeSdk](https://github.com/houhoudev/TaokeSdk)
demo下载：[立即下载](https://houhoudev.oss-cn-shenzhen.aliyuncs.com/downLoad/TaokeSdk.apk)
扫码下载
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191016170031792.png)
市场下载：[查看市场下载](https://sj.qq.com/myapp/detail.htm?apkName=com.houhoudev.red)
扫码下载：
![在这里插入图片描述](https://img-blog.csdnimg.cn/201910161705088.png)
# 二、关于SDK

 - 支持淘宝授权登录、免登录
 - 一键接入各种商城模块
 - 一键配置淘宝客推广位，赚取收益
 - 体积小，增量约为5M左右
# 三、接入前准备
1、注册淘宝联盟，获取推广位id，注册链接：[https://pub.alimama.com/](https://pub.alimama.com/)
2、注册阿里百川，获得电商权限，注册链接：[https://baichuan.taobao.com/](https://baichuan.taobao.com/)
ps：自行注册或可以联系作者提供
# 四、全局参数配置
Module下build.gradle配置

```java
	defaultConfig {

		///////
	
		manifestPlaceholders = [
       		scheme : "sdk", // 协议，自己定义一个任意字符串即可
      		product: 5 // 产品id，邀请好友页面等地方需要用到
    	]
	}
```
AndroidManifest.xml配置

```xml
	<provider
        android:name="android.support.v4.content.FileProvider"
        android:authorities="com.houhoudev.red.fileprovider"
        android:exported="false"
        android:grantUriPermissions="true">
        <meta-data
            android:name="android.support.FILE_PROVIDER_PATHS"
            android:resource="@xml/file_paths" />
    </provider>
	<!-- 友盟 若项目中需要集成友盟统计，加入如下配置-->
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
	// 基础工具
	implementation 'com.houhoudev:common:1.0.2'
	// 商城基础
	implementation 'com.houhoudev:store:1.0.2'
	// 用户基础
	implementation 'com.houhoudev:user:1.0.2'
	// 扫描二维码
	implementation 'com.houhoudev:zxing:1.0.2'
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
    implementation 'com.squareup.okhttp3:okhttp:3.10.0'
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
    maven { url 'https://dl.bintray.com/umsdk/release' }
    // 商城
    maven { url 'https://raw.githubusercontent.com/houhoudev/repository/master' }
}

```
## 2、SDK调用

-  首先需要在application中初始sdk
 

```java
	String pid = "mm_118568524_485000365_109457800416"; // 淘宝联盟pid
    String adzoneid = "109457800416"; // pid最后一段
    String unionId = ""; // 联盟id（一般传""）
    String appKey = "26313026"; // 淘宝联盟appKey
    StoreSdk.init(this, pid, adzoneid, unionId, appKey);// 初始化sdk
```
- 首页模块Fragment

```java
	Fragment mainFrag = StoreSdk.getMainFrag();
```
![首页Fragment](https://img-blog.csdnimg.cn/20191015172942822.jpg)
- 跳转首页Activity
```java
    // 跳转首页Activity
    StoreSdk.startMainAct(getActivity());
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/2019101517383471.jpg)
- 更多调用

```java
	// 分类fragment
	Fragment classifyfrag = StoreSdk.getClassifyFrag();
	// 跳转分类Activity
	StoreSdk.startClassifygAct(this);
	
	// 榜单Fragment
	Fragment rankingFrag = StoreSdk.getRankingFrag();
	// 跳转榜单Activity
	StoreSdk.startClassifygAct(this);

	// 我的Fragment
	Fragment mineFrag = StoreSdk.getMineFrag();
	// 跳转我的Activity
	StoreSdk.startMineAct(this);

	// 跳转金币Activity
	StoreSdk.startCoinsAct(this);
	
	// 邀请好友Activity
	StoreSdk.startFriends(this);
	
	// 商品详情Activity
	StoreSdk.startGoodDetail(this, 521422451240L);
	
	// 我的收藏Activity
	StoreSdk.startCollection(this);
	
	// 历史记录Acivity
	StoreSdk.startHistory(this);
	
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

```json

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
# 七、联系作者
微信：lijunjie8579
QQ：2276280645
