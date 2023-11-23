package com.yx.play.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView


/**
 * @description
 * @version
 */
class MyRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr)  {
    private var mLastX = 0
    private var mLastY = 0

    //处理触摸事件的分发 是从dispatchTouchEvent开始的
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        //触摸点相对于其所在组件原点的X坐标
        val x = event.x.toInt()
        val y = event.y.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN ->                 //手按下屏幕,父布局没有作用,进行拦截
                //让父布局ViewPager禁用拦截功能,从而让父布局忽略事件后的一切行为
                //requestDisallowInterceptTouchEvent(true)表示：
                //getParent() 获取到父视图 父视图不拦截触摸事件
                //孩子不希望父视图拦截触摸事件
                parent.requestDisallowInterceptTouchEvent(true)

            MotionEvent.ACTION_MOVE -> {
                //水平移动的增量
                val deltaX = x - mLastX
                val deltaY = y - mLastY
                //Math.abs绝对值
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    //当水平增量大于竖直增量时，表示水平滑动，此时需要父View去处理事件，所以不拦截
                    //让父布局ViewPager使用拦截功能,从而让父布局完成事件后的一切行为

                    //requestDisallowInterceptTouchEvent(false)表示：
                    //孩子希望父视图拦截触摸事件,也就是让CustomViewPager拦截触摸事件，进行左右滑动
                    parent.requestDisallowInterceptTouchEvent(false)
                }
            }

            else -> {}
        }
        mLastX = x
        mLastY = y
        return super.dispatchTouchEvent(event)
    }
}