package com.yx.play.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridItemDecoration(
    var mColumn: Int = 2,
    var mSpace: Int? = 20,
    var mTop: Int? = 0,
    var mRight: Int? = 0
) : RecyclerView.ItemDecoration() {

    private var mItemSpace: Int = (mSpace?.times((mColumn - 1)))?.div(mColumn) ?: 0

    init {
        if (mSpace == null) {
            mItemSpace = (mRight?.times((mColumn - 1)))?.div(mColumn) ?: 0
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)


        when {
            (position + 1) % mColumn == 1 -> {
                outRect.left = 0
                outRect.right = mItemSpace
            }
            (position + 1) % mColumn == 0 -> {
                outRect.left = mItemSpace
                outRect.right = 0
            }
            else -> {
                outRect.left = mItemSpace / 2
                outRect.right = mItemSpace / 2
            }
        }

        if (position < mColumn) {
            outRect.top = 0
        } else {
            outRect.top = mSpace ?: mTop ?: 0
        }
    }
}