package com.yx.play

import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.blankj.utilcode.util.ProcessUtils
import com.blankj.utilcode.util.Utils
import com.yx.play.db.DataBaseManager

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

    }

    private fun initSdk() {
//        val rhsdkManager = RHSDKManager.initialize()
//        rhsdkManager.isPersonalRecommend = true
//        rhsdkManager.isDebug = true
//        rhsdkManager.startWithOptions(this, TestPosId.APP_ID.value, TestPosId.APP_KEY.value)
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