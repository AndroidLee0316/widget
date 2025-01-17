# 控件组件

## 广告栏库

### 配置
1. 引入包

```
api 'com.pasc.lib.widget:LibWidget:+'
```
2. 添加权限

```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```

### 初级使用
1. 在布局文件添加广告栏

```
<com.pasc.lib.widget.banner.SliderLayout
    android:id="@+id/slider"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
/>
```
2. 初始化

```
// 获取广告栏布局
SliderLayout sliderLayout = (SliderLayout) findViewById(R.id.slider);
// 设置广告栏图片地址数组
sliderLayout.setImageUrls(urls); // datas是url数组，类型时String[]
// 设置广告栏点击监听
sliderLayout.setOnItemClickListener(listener);
```

### 完整配置
配置中的属性值是属性的默认值，譬如不配置ratio则默认就是2
```
<com.daimajia.slider.library.SliderLayout
    android:id="@+id/slider"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    custom:ratio="2"
    custom:auto_cycle="true"
    custom:indicator_visibility="visible"
    custom:pager_animation="Default"
    custom:pager_animation_span="1000"
    custom:pager_animation_duration="4000"
    custom:defaultImgRes="@drawable/bg_default_img"
    />
```
###### 字段说明
字段 | 说明
---|---
ratio | 广告栏的宽高比，layout_height为wrap_content时才生效
auto_cycle | 是否自动轮播
indicator_visibility | 指示器是否显示
pager_animation | 页面切换动画类型
pager_animation_span | 动画时长
pager_animation_duration | 动画执行间隔时长
default_img | 默认图片资源#   w i d g e t  
 