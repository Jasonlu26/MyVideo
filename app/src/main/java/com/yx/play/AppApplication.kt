package com.yx.play

import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.blankj.utilcode.util.ProcessUtils
import com.blankj.utilcode.util.Utils
import com.rh.sdk.api.RHSDKManager
import com.umeng.commonsdk.UMConfigure
import com.yx.play.db.DataBaseManager
import com.yx.play.util.TestPosId


/**
 * @description
 * @version
 */
open class AppApplication:  MultiDexApplication() {

    companion object{
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
        initUM()
    }

    private fun initSdk() {
        val rhsdkManager = RHSDKManager.initialize()
        rhsdkManager.isPersonalRecommend = true
        rhsdkManager.isDebug = true
        rhsdkManager.startWithOptions(this, TestPosId.APP_ID.value, TestPosId.APP_KEY.value)
    }

    private fun initIm() {
        DataBaseManager.getInstance().init(this)
    }

    private fun initUM(){
        //设置LOG开关，默认为false
        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(true)

        //友盟预初始化

        //友盟预初始化
        UMConfigure.preInit(this, "655eb21058a9eb5b0a0ee28d", "Android")

        /**
         * 打开app首次隐私协议授权，以及sdk初始化，判断逻辑请查看SplashTestActivity
         */
        //判断是否同意隐私协议，uminit为1时为已经同意，直接初始化umsdk
        /**
         * 打开app首次隐私协议授权，以及sdk初始化，判断逻辑请查看SplashTestActivity
         */
        //判断是否同意隐私协议，uminit为1时为已经同意，直接初始化umsdk
        //友盟正式初始化
        UMConfigure.init(this,"655eb21058a9eb5b0a0ee28d","Android",UMConfigure.DEVICE_TYPE_PHONE, "");
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