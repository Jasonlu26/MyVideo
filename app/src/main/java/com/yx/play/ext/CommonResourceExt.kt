package com.yx.play.ext

import android.app.Activity
import android.graphics.Outline
import android.os.SystemClock
import android.view.View
import android.view.ViewOutlineProvider
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ResourceUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils

/**
 * @description
 * @time 2021/3/8 6:21
 * @version
 */
fun Int.getColor(): Int {
    return ColorUtils.getColor(this)
}

fun Int.getDimension(): Int {
    return Utils.getApp().resources.getDimensionPixelSize(this)
}

fun String.toastShowLong() {
    ToastUtils.showLong(this)
}


fun String.toastShowShort() {
    ToastUtils.showShort(this)
}

fun Float.dpToPx(): Int {
    return Utils.getApp().resources.getDimensionPixelSize(ResourceUtils.getDimenIdByName(DIMEN_DP_PREFIX + this.toInt()))
}

fun Float.spToPx(): Int {
    return Utils.getApp().resources.getDimensionPixelSize(ResourceUtils.getDimenIdByName(DIMEN_SP_PREFIX + this.toInt()))
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.isShow() = visibility == View.VISIBLE

fun <T : ViewBinding> ViewBinding.bindView(context: Activity): T {
    context.setContentView(root)
    return this as T
}

inline fun View.click(debounceTime: Long = 600, crossinline block: (View) -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0
        override fun onClick(v: View) {

            if (debounceTime == 0.toLong()) {
                block(v)
                return
            }

            if (SystemClock.elapsedRealtime() - lastClickTime > debounceTime) {
                block(v)
            }
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

fun View.setOutlineProvider(corner: Float) {
    val outLine = object : ViewOutlineProvider() {
        override fun getOutline(view: View?, outline: Outline?) {
            outline?.setRoundRect(
                0,
                0,
                view?.width ?: 0,
                view?.height ?: 0,
                corner
            )
        }
    }

    outlineProvider = outLine
    clipToOutline = true
}

const val DIMEN_DP_PREFIX = "dp_"
const val DIMEN_SP_PREFIX = "sp_"