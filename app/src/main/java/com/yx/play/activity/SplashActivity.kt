package com.yx.play.activity

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.SPUtils
import com.sccdwxxyljx.com.databinding.ActivitySplashBinding
import com.yx.play.dialog.MyPrivacyDialog
import com.yx.play.ext.bindView

/**
 * @description
 * @version
 */
class SplashActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawable(null)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        mBinding = ActivitySplashBinding.inflate(layoutInflater).bindView(this)

        loadAd()
    }

    private fun gotoMain() {
        MainActivity.startIntent(this)
        finish()
    }

    private fun loadAd() {
        val isAgree = SPUtils.getInstance().getBoolean("isAgree")
        if (isAgree) {
            gotoMain()
            return
        }

        val dialog = MyPrivacyDialog(this)
        dialog.show()

        dialog.setOnBtnClickListener { type ->
            when (type) {
                MyPrivacyDialog.ARGEEMENT_TEXT_CLICK -> {
                    XieyiActivity.newInstance(this)
                }
                MyPrivacyDialog.SECRET_TEXT_CLICK -> {
                    XieyiActivity.newInstance(this)
                }
                MyPrivacyDialog.NOT_ARGEE_BTN_CLICK -> {
                    dialog.dismiss()
                    finish()
                }
                MyPrivacyDialog.ARGEE_BTN_CLICK -> {
                    dialog.dismiss()
                    SPUtils.getInstance().put("isAgree", true)
                    gotoMain()
                }
            }
        }
//        val rhSplashAd = RHSplashAd()
//        rhSplashAd.loadAd(
//            this,
//            mBinding.container,
//            TestPosId.POSID_SPLASH.value,
//            object : SplashAdListener {
//                override fun onADClose() {
//                    gotoMain()
//                }
//
//                override fun onADClick() {
//
//                }
//
//                override fun onADExposure() {
//
//                }
//
//                override fun onADLoaded() {
//
//                }
//
//                override fun onADError(i: Int, s: String, s1: String) {
////                    gotoMain()
//                }
//            })
    }
}