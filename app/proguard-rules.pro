# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keep public class com.rh.sdk.**{ *;}
-keep interface com.adspace.sdk.**{*;}
-keep public class com.assembly.utils.**{*;}
-keep public class com.adspace.utils.*{*;}
-keep public class com.adspace.widget.*{*;}
-keep class com.adspace.sdk.channel.*.*{*;}
-keep public class com.adspace.sdk.net.model.**{*;}
-keep public class com.adspace.sdk.net.model.methodproxy**{
      public <methods>;
}
-keep class com.adspace.sdk.config.AdConfig{ *; }
-keep class com.adspace.sdk.config.AdConfig$*{
        <fields>;
        <methods>;
}

#oaid混淆
-dontwarn com.bun.**
-keep class com.bun.** {*;}
-keep class a.**{*;}
-keep class XI.CA.XI.**{*;}
-keep class XI.K0.XI.**{*;}
-keep class XI.XI.K0.**{*;}
-keep class XI.vs.K0.**{*;}
-keep class XI.xo.XI.XI.**{*;}
-keep class com.asus.msa.SupplementaryDID.**{*;}
-keep class com.asus.msa.sdid.**{*;}
-keep class com.huawei.hms.ads.identifier.**{*;}
-keep class com.samsung.android.deviceidservice.**{*;}
-keep class com.zui.opendeviceidlibrary.**{*;}
-keep class org.json.**{*;}
-keep public class com.netease.nis.sdkwrapper.Utils {public <methods>;}

-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable*Annotation*,EnclosingMethod
-keep class com.bytedance.sdk.openadsdk.** {*;}
-keep public interface com.bytedance.sdk.openadsdk.downloadnew.** {*;}
-keep class com.pgl.sys.ces.* {*;}
-keep class org.chromium.** {*;}
-keep class org.chromium.** { *; }
-keep class aegon.chrome.** { *; }
-keep class com.kwai.**{ *; }
-keep class com.kwad.**{ *; }
-keepclasseswithmembernames class * {
 native <methods>;
}
-dontwarn com.kwai.**
-dontwarn com.kwad.**
-dontwarn com.ksad.**
-dontwarn aegon.chrome.**
-keep class pro.dxys.ad.**{*;}
-keep class com.taobao.**{*; }

-dontwarn com.baidu.mobads.sdk.api.**
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class com.baidu.mobads.** { *; }
-keep class com.style.widget.** {*;}
-keep class com.component.** {*;}
-keep class com.baidu.ad.magic.flute.** {*;}

-keep class com.ads.widget.* {*;}
-keep class com.kwad.sdk.** { *;}
-keep class com.ksad.download.** { *;}
-keep class com.kwai.filedownloader.** { *;}

-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable*Annotation*,EnclosingMethod
-keep class com.bytedance.sdk.openadsdk.** {*;}
-keep public interface com.bytedance.sdk.openadsdk.downloadnew.** {*;}
-keep class com.pgl.sys.ces.* {*;}
-keep class org.chromium.** {*;}
-keep class org.chromium.** { *; }
-keep class aegon.chrome.** { *; }
-keep class com.kwai.**{ *; }
-keep class com.kwad.**{ *; }
-keepclasseswithmembernames class * {
 native <methods>;
}
-dontwarn com.kwai.**
-dontwarn com.kwad.**
-dontwarn com.ksad.**
-dontwarn aegon.chrome.**
-keep class pro.dxys.ad.**{*;}
-keep class com.taobao.**{*; }

-keep class com.bytedance.notpluginpro.tmapcloaknotplugin{*;}
-keep class com.bytedance.frameworks.** { *; }
-keep class ms.bd.c.Pgl.**{*;}
-keep class com.bytedance.mobsec.metasec.ml.**{*;}
-keep class com.ss.android.**{*;}
-keep class com.bytedance.embedapplog.** {*;}
-keep class com.bytedance.embed_dr.** {*;}
-keep class com.bykv.vk.** {*;}
-keep public interface com.uodis.** { *; }
-keep public class com.bytedance.sdk.api.*.**{ *; }
-keep public class com.bytedance.sdk.api.**{ *; }
-keep public class com.bytedance.sdk.adapter.*.**{ *; }


-keep public class cn.jzvd.JZMediaSystem {*; }
-keep public class cn.jzvd.demo.CustomMedia.CustomMedia {*; }
-keep public class cn.jzvd.demo.CustomMedia.JZMediaIjk {*; }
-keep public class cn.jzvd.demo.CustomMedia.JZMediaSystemAssertFolder {*; }

-keep class tv.danmaku.ijk.media.player.** {*; }
-dontwarn tv.danmaku.ijk.media.player.*
-keep interface tv.danmaku.ijk.media.player.** { *; }


-optimizationpasses 5
#-dontusemixedcaseclassnames
#-dontskipnonpubliclibraryclasses
#-dontpreverify
#-verbose
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
#-keepattributes *Annotation*,InnerClasses,Signature,EnclosingMethod
-renamesourcefileattribute SourceFile

-keepattributes SourceFile,LineNumberTable
-dontpreverify

#kotlin
-keep class kotlin.* { *; }
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.**

-keep class androidx.* { *; }

# Glide proguard
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-keepattributes Signature

-keep class com.iflytek.*{*;}
-keepattributes *Annotation*, Signature, Exception
# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule


-ignorewarnings
#-libraryjars libs/YoudaoBase_v2.0.1.jar
#-libraryjars libs/YoudaoTranslateOnline_v2.0.0.jar
#-libraryjars libs/YoudaoVoiceRecognize_v2.0.0.jar
#-libraryjars libs/ydasrsdk-release.jar
#-libraryjars libs/ydaudiosdk-release.aar

#That does not confuse the class name and native method name of any class containing native methods, which is consistent with the results we just verified
-keepclasseswithmembernames class * {
    native <methods>;
}

#AsrSdk
-keep class com.youdao.* { *; }
-keep class com.youdao.ydasr.* { *; }
-keep class com.youdao.ydasr.asrengine.model.* { *; }
-keep interface com.youdao.ydasr.* { *; }
-keep class com.youdao.audio.* { *; }
-keep interface com.youdao.audio.* { *; }
-keep class com.youdao.sdk.ydtranslate.* { *;}
-keep class com.youdao.voicerecognize.online.* { *;}

-keep class kotlin.* { *; }
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.**
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
}

-keep class io.agora.*{*;}

-keep class tv.danmaku.ijk.media.player.* {*; }
-dontwarn tv.danmaku.ijk.media.player.*
-keep interface tv.danmaku.ijk.media.player.* { *; }

-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.SerializationKt

#-dontwarn com.tencent.bugly.**
#-keep public class com.tencent.bugly.*{*;}

-dontwarn com.yalantis.ucrop.**
-keep class com.yalantis.ucrop.* { *; }
-keep interface com.yalantis.ucrop.* { *; }

#video progaurd
-keep public class * extends android.view.View{*;}
-keep public class * implements com.kk.taurus.playerbase.player.IPlayer{*;}
# ijkplayer------------------------------------
-keep class tv.danmaku.ijk.media.player.**{*;}
# ijKplayer------------------------------------

-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator CREATOR;
}

-keepnames class androidx.lifecycle.ViewModel
-keepclassmembers class * extends androidx.lifecycle.ViewModel { <init>(...); }
-keepclassmembers class * implements androidx.lifecycle.LifecycleObserver { <init>(...); }
-keepclassmembers class * implements androidx.lifecycle.LifecycleOwner { <init>(...); }
-keepclassmembers class androidx.lifecycle.Lifecycle$State { *; }
-keepclassmembers class androidx.lifecycle.Lifecycle$Event { *; }
-keep class * implements androidx.lifecycle.LifecycleOwner { public <init>(...); }
-keep class * implements androidx.lifecycle.LifecycleObserver { public <init>(...); }

#-keep class io.agora.rtc.**{*;}
#-keep class io.agora.rtc.internal.*{*;}
#-keep class io.agora.rtc.internal.RtcEngineImpl.* {*;}
#-keep class io.agora.rtc.mediaio.VideoFrameConsumerImpl.* {*;}
#-keepclasseswithmembernames class io.agora.rtc.mediaio.VideoFrameConsumerImpl{
#  native <methods>;
#}
#-keep class io.agora.rtm.**{*;}
#-keep class io.agora.rtm.jni.*{*;}

#-keepattributes *Annotation*
#-keepclassmembers class * {
#    @org.greenrobot.eventbus.Subscribe <methods>;
#}
#-keep enum org.greenrobot.eventbus.ThreadMode { *; }
#
## And if you use AsyncExecutor:
#-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
#    <init>(java.lang.Throwable);
#}

#-keep public class com.alibaba.android.arouter.routes.**{*;}
#-keep public class com.alibaba.android.arouter.facade.**{*;}
#-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
#
## If you use the byType method to obtain Service, add the following rules to protect the interface:
#-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider

-keep class com.zxy.** { *; }

-keepclassmembers class ** implements androidx.viewbinding.ViewBinding {
    public static ** bind(***);
    public static ** inflate(***);
    public static ** inflate(**,**);
}

#log4j2
-dontwarn org.apache.logging.log4j.**
-keepattributes Signature
-keep class org.apache.logging.log4j.** { *; }

# If single-type injection is used, that is, no interface is defined to implement IProvider, the following rules need to be added to protect the implementation
# -keep class * implements com.alibaba.android.arouter.facade.template.IProvider

# Class names are needed in reflection
-keepnames class com.amazonaws.**
-keepnames class com.amazon.**

# Request handlers defined in request.handlers
-keep class com.amazonaws.services.**.*Handler

# The SDK has several references of Apache HTTP client
-dontwarn com.amazonaws.http.**
-dontwarn com.amazonaws.metrics.**


-keep class com.appsflyer.** { *; }

-keep class com.alibaba.sdk.android.oss.** { *; }
-dontwarn okio.**
-dontwarn org.apache.commons.codec.binary.**

-keep class com.umeng.** {*;}

-keep class org.repackage.** {*;}

-keep class com.uyumao.** { *; }

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
