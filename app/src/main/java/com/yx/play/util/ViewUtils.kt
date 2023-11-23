package com.yx.play.util

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


/**
 * @description
 * @version
 */
object ViewUtils {
    fun getChildRecyclerView(view: View): View? {
        val unvisited = ArrayList<View>()
        unvisited.add(view)
        while (unvisited.isNotEmpty()) {
            val child = unvisited.removeAt(0)
            if (child is RecyclerView) {
                return child
            }
            if (child !is ViewGroup) {
                continue
            }
            val viewGroup = child
            for (i in 0 until viewGroup.childCount) {
                unvisited.add(viewGroup.getChildAt(i))
            }
        }
        return null
    }

}