package com.yx.play

import android.content.ServiceConnection
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.blankj.utilcode.util.ProcessUtils
import com.blankj.utilcode.util.Utils
import com.bytedance.sdk.openadsdk.TTAdConfig
import com.bytedance.sdk.openadsdk.TTAdConstant
import com.bytedance.sdk.openadsdk.TTAdSdk
import com.bytedance.sdk.openadsdk.TTAdSdk.updateAdConfig
import com.yx.play.db.DataBaseManager

/**
 * @description
 * @version
 */
open class AppApplication : MultiDexApplication() {

    companion object {
        var isSettingLanguage = false
    }

    override fun unbindService(conn: ServiceConnection) {
        try {
            super.unbindService(conn)
        } catch (e: Exception) {
        }
    }

    override fun onCreate() {
        super.onCreate()


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        if (ProcessUtils.isMainProcess()) {

            Utils.init(this)

            initIm()


//            initActivityLifecycleCallbacks()

        }

        initSdk()
        initAd()
    }

    private fun initSdk() {
//        val rhsdkManager = RHSDKManager.initialize()
//        rhsdkManager.isPersonalRecommend = true
//        rhsdkManager.isDebug = true
//        rhsdkManager.startWithOptions(this, TestPosId.APP_ID.value, TestPosId.APP_KEY.value)
    }

    private fun initAd() {
        TTAdSdk.init(
            this,
            TTAdConfig.Builder()
                .appId("5040922")//xxxxxxx为穿山甲媒体平台注册的应用ID
                .useTextureView(true) //默认使用SurfaceView播放视频广告,当有SurfaceView冲突的场景，可以使用TextureView
                .appName("MyVideo")
                .titleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)//落地页主题
                .allowShowNotify(true) //是否允许sdk展示通知栏提示,若设置为false则会导致通知栏不显示下载进度
                .debug(false) //测试阶段打开，可以通过日志排查问题，上线时去除该调用
                .directDownloadNetworkType(TTAdConstant.NETWORK_STATE_WIFI) //允许直接下载的网络状态集合,没有设置的网络下点击下载apk会有二次确认弹窗，弹窗中会披露应用信息
                .supportMultiProcess(false) //是否支持多进程，true支持
//                .asyncInit(true) //是否异步初始化sdk,设置为true可以减少SDK初始化耗时。3450版本开始废弃~~
                //.httpStack(new MyOkStack3())//自定义网络库，demo中给出了okhttp3版本的样例，其余请自行开发或者咨询工作人员。
//                .updateAdConfig(ttAdConfig)//参数类型为TTAdConfig；注意使用该方法会覆盖之前初始化sdk的配置的data值；个性化推荐设置详见：https://www.csjplatform.com/supportcenter/26234
//                .injectionAuth()
                .build()
        )

        TTAdSdk.start(object : TTAdSdk.Callback {
            override fun success() {
                Log.e("app", "success: ================ ")
            }

            override fun fail(p0: Int, p1: String?) {
                Log.e("app", "fail: $p0  $p1")
            }

        })
    }

    private fun initIm() {
        DataBaseManager.getInstance().init(this)
    }


//    private fun initActivityLifecycleCallbacks() {
//        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
//            override fun onActivityPaused(activity: Activity) {
//            }
//
//            override fun onActivityStarted(activity: Activity) {
//            }
//
//            override fun onActivityDestroyed(activity: Activity) {
//            }
//
//            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
//            }
//
//            override fun onActivityStopped(activity: Activity) {
//            }
//
//            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
//                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
//                    StatusBarUtil.setStatusBarTextColorLight(activity)
//                } else {
//                    StatusBarUtil.setStatusBarTextColorDark(activity)
//                }
//            }
//
//            override fun onActivityResumed(activity: Activity) {
//            }
//
//        })
//    }
}