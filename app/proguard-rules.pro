# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Program\android-sdk-windows/tools/proguard/proguard-android.txt
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
-keep class com.tencent.mm.sdk.** {

   *;
}
-keepclassmembers class * {
    public <init>(org.json.JSONObject);
}

#把[您的应用包名] 替换成您自己的包名，如"com.example.R$*"。
-keep public class com.cardholder.adwlf.R$*{
    public static final int *;
}

# 如"com.example.R$*"
-keep public class com.cardholder.adwlf.R$*{
    public static final int *;
}
-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
#==================gson==========================
-dontwarn com.google.**
-keep class com.google.gson.** {*;}

#==================protobuf======================
-dontwarn com.google.**
-keep class com.google.protobuf.** {*;}
#-dontwarn kdx.kdy.kdz.**
#-keepclassmembers class kdx.kdy.kdz.libs.adsbase.js.base.JsInterface_Impl {
#    *;
#}
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
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
-keep class com.alibaba.sdk.android.feedback.** {*;}

