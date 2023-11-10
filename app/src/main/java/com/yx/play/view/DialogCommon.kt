package com.yx.play.view

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.sccdwxxyljx.com.R
import com.yx.play.ext.click
import com.yx.play.ext.gone
import com.yx.play.ext.visible
import razerdp.basepopup.BasePopupWindow

class DialogCommon(context: Context) : BasePopupWindow(context) {

    override fun onCreateContentView(): View = createPopupById(R.layout.dialog_common)

    companion object {

        fun create(context: Context): DialogCommon {
            return DialogCommon(context)
        }
    }

    fun setCancelClickListener(isDismiss: Boolean = true, block: () -> Unit): DialogCommon {
        this.contentView.findViewById<View>(R.id.cancelBtn).click {
            block()
            if (isDismiss) {
                dismiss()
            }
        }
        return this
    }

    fun setConfirmClickListener(isDismiss: Boolean = true, block: () -> Unit): DialogCommon {
        this.contentView.findViewById<View>(R.id.confirmBtn).click {
            block()
            if (isDismiss) {
                dismiss()
            }
        }
        return this
    }

    fun setConfirmTextColor(color: Int): DialogCommon {
        this.contentView.findViewById<TextView>(R.id.confirmBtn).setTextColor(ContextCompat.getColor(context, color))
        return this
    }

    fun setCancelTextColor(color: Int): DialogCommon {
        this.contentView.findViewById<TextView>(R.id.cancelBtn).setTextColor(ContextCompat.getColor(context, color))
        return this
    }

    fun setCanOutSideDismiss(outSideDismiss: Boolean = true): DialogCommon {
        this.setOutSideDismiss(outSideDismiss)
        return this
    }

    fun showNoCancel(
        content: String,
        confirmBtnText: String = "确定",
        title: String? = null,
    ): DialogCommon {
        showPopupWindow()
        title?.let {
            this.contentView.findViewById<TextView>(R.id.titleView).text = title
            this.contentView.findViewById<TextView>(R.id.titleView).visible()
        } ?: run {
            this.contentView.findViewById<TextView>(R.id.titleView).gone()
        }
        this.contentView.findViewById<TextView>(R.id.contentView).text = content
        this.contentView.findViewById<TextView>(R.id.cancelBtn).gone()
        this.contentView.findViewById<TextView>(R.id.confirmBtn).text =
            confirmBtnText
        return this
    }

    fun show(
        content: String,
        confirmBtnText: String = "确定",
        cancelBtnText: String = "取消",
        title: String? = null,
        anchorView: View? = null,
    ): DialogCommon {
        anchorView?.let {
            showPopupWindow(it)
        } ?: run {
            showPopupWindow()
        }
        title?.let {
            this.contentView.findViewById<TextView>(R.id.titleView).text = title
            this.contentView.findViewById<TextView>(R.id.titleView).visible()
        } ?: run {
            this.contentView.findViewById<TextView>(R.id.titleView).gone()
        }
        this.contentView.findViewById<TextView>(R.id.contentView).text = content
        this.contentView.findViewById<TextView>(R.id.cancelBtn).text =
            cancelBtnText
        this.contentView.findViewById<TextView>(R.id.confirmBtn).text =
            confirmBtnText
        return this
    }
}