# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android\SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#glide使用okhttp3网络框架
-keep class com.bumptech.glide.integration.okhttp3.OkHttpGlideModule
#GalleryFinal混淆配置
-keep class com.study.mingappk.common.views.gallerfinal.widget.*{*;}
-keep class com.study.mingappk.common.views.gallerfinal.widget.crop.*{*;}
-keep class com.study.mingappk.common.views.gallerfinal.widget.zoonview.*{*;}