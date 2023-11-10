package com.yx.play.view

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import androidx.fragment.app.Fragment
import com.sccdwxxyljx.com.R
import razerdp.basepopup.BasePopupWindow

class PageLoadingDialog : BasePopupWindow {

    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Fragment) : super(ctx)

    init {
        isOutSideTouchable = false
        setOutSideDismiss(false)
        setBackPressEnable(false)
        setBackgroundColor(Color.TRANSPARENT)
        popupGravity = Gravity.CENTER
        setAlignBackground(true)

//        contentView = createPopupById(R.layout.app_state_loading_page_layout)
    }

    override fun onCreateContentView(): View =
        createPopupById(R.layout.app_state_loading_page_layout)
}