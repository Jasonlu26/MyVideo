package com.yx.play.activity

import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adspace.sdk.adlistener.SplashAdListener
import com.rh.sdk.api.RHSplashAd
import com.yx.play.databinding.ActivitySplashBinding
import com.yx.play.ext.bindView
import com.yx.play.util.TestPosId

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
        val rhSplashAd = RHSplashAd()
        rhSplashAd.loadAd(
            this,
            mBinding.container,
            TestPosId.POSID_SPLASH.value,
            object : SplashAdListener {
                override fun onADClose() {
                    gotoMain()
                }

                override fun onADClick() {

                }

                override fun onADExposure() {

                }

                override fun onADLoaded() {

                }

                override fun onADError(i: Int, s: String, s1: String) {
//                    gotoMain()
                }
            })
    }
}