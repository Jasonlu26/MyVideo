package com.yx.play.util

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.adspace.sdk.adlistener.RewardAdListener
import com.blankj.utilcode.util.ToastUtils
import com.rh.sdk.api.RHRewardAd
import com.yx.play.activity.DetalisActivity
import com.yx.play.db.DataBaseManager

/**
 * @description
 * @version
 */
object AdUtils {

    fun showRewardAd(context: Activity, id: String, hideLoading: () -> Unit) {
        var isComplete = false
        val rhRewardAd = RHRewardAd.getInstance()
        rhRewardAd.setUserId("userId")
        rhRewardAd.setExtraInfo("extraInfo")
        rhRewardAd.loadShowRewardAd(context, TestPosId.POSID_REWARD.value, object :
            RewardAdListener {

            override fun onADLoaded() {
                hideLoading.invoke()
            }

            override fun onADCached() {

            }

            override fun onADShow() {

            }

            override fun onADExpose() {

            }

            override fun onADReward(p0: String?) {

            }

            override fun onADClick() {

            }

            override fun onADComplete() {
                isComplete = true
            }

            override fun onADClose() {
                if (isComplete) {
                    DataBaseManager.getInstance().setIsOpenReward()
                    DetalisActivity.newInstance(context, id)
                }
            }

            override fun onADError(p0: Int, p1: String?, p2: String?) {
                ToastUtils.showLong("网络错误，加载失败")
                hideLoading.invoke()
            }
        })
    }
}