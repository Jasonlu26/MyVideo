package com.yx.play.util

import android.app.Activity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.ThreadUtils
import com.yx.play.activity.DetalisActivity
import com.yx.play.db.DataBaseManager
import com.yx.play.view.DialogCommon
import com.yx.play.view.PageLoadingDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import razerdp.basepopup.BasePopupWindow

/**
 * @description
 * @version
 */
object IntentUtils {

    private var loadingDialog: PageLoadingDialog? = null

    fun intentDetails(
        context: AppCompatActivity,
        id: String,
    ) {

        DetalisActivity.newInstance(context, id)
        return
//        val isOpenReward = DataBaseManager.getInstance().getIsOpenReward()
//        if (isOpenReward){
//            DetalisActivity.newInstance(context, id)
//            return
//        }
//        DialogCommon.create(context)
//            .setCancelClickListener {
//
//            }
//            .setConfirmClickListener {
//                showLoading(context)
//                AdUtils.showRewardAd(context, id) {
//                    hideLoading()
//                }
//            }
//            .show(
//                "看完视频可以获取所有视频观看权限！",
//                confirmBtnText = "确定",
//                cancelBtnText = "取消"
//            )

    }


    fun showLoading(
        context: Activity,
        isOutDismiss: Boolean = false,
        view: View? = null,
        isBackDismiss: Boolean = false,
        dismissCallback: (() -> Unit)? = null
    ) {
        loadingDialog = PageLoadingDialog(context)
        if (isOutDismiss) {
            loadingDialog?.isOutSideTouchable = true
            loadingDialog?.setOutSideDismiss(true)
        }

        loadingDialog?.setBackPressEnable(isBackDismiss)

        dismissCallback?.run {
            loadingDialog?.setOnDismissListener(object : BasePopupWindow.OnDismissListener() {
                override fun onDismiss() {
                    dismissCallback()
                }

            })
        }


        if (view == null) {
            loadingDialog?.showPopupWindow()
        } else {
            loadingDialog?.showPopupWindow(view)
        }
    }

    fun hideLoading() {
        if (loadingDialog == null || loadingDialog?.isShowing == false) {
            ThreadUtils.runOnUiThreadDelayed({
                loadingDialog?.dismiss()
            }, 50)
            return
        }
        loadingDialog?.dismiss()
    }
}