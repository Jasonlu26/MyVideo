package com.yx.play.ext

import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import com.yx.play.view.FlexibleDividerDecoration
import com.yx.play.view.HorizontalDividerItemDecoration
import com.yx.play.view.VerticalDividerItemDecoration

/**
 * @description
 *
 * @time 2021/3/11 10:01
 * @version
 */

fun RecyclerView.addHorizontalItemDecoration(
    color: Int? = null,
    drawableRes: Int? = null,
    drawable: Drawable? = null,
    margin: Int = 0,
    marginLeft: Int = 0,
    marginRight: Int = 0,
    size: Int?,
    isShowLastDivider: Boolean = false,
    visibilityProvider: FlexibleDividerDecoration.VisibilityProvider? = null,
    colorProvider: FlexibleDividerDecoration.ColorProvider? = null,
    sizeProvider: FlexibleDividerDecoration.SizeProvider? = null,
    drawableProvider: FlexibleDividerDecoration.DrawableProvider? = null
) {
    val builder = VerticalDividerItemDecoration.Builder(context)

    if (color != null) {
        builder.color(color)
    }

    if (drawableRes != null) {
        builder.drawable(drawableRes)
    }

    if (drawable != null) {
        builder.drawable(drawable)
    }

    if (size != null) {
        builder.size(size)
    }

    if (visibilityProvider != null) {
        builder.visibilityProvider(visibilityProvider)
    }

    if (colorProvider != null) {
        builder.colorProvider(colorProvider)
    }

    if (sizeProvider != null) {
        builder.sizeProvider(sizeProvider)
    }

    if (drawableProvider != null) {
        builder.drawableProvider(drawableProvider)
    }

    if (margin != 0) {
        builder
            .margin(margin)
    } else {
        builder
            .margin(marginLeft, marginRight)
    }

    if (isShowLastDivider) {
        builder.showLastDivider()
    }

    addItemDecoration(
        builder
            .build()
    )
}

fun RecyclerView.addVerticalItemDecoration(
    color: Int? = null,
    drawableRes: Int? = null,
    drawable: Drawable? = null,
    margin: Int = 0,
    marginLeft: Int = 0,
    marginRight: Int = 0,
    size: Int? = null,
    isShowLastDivider: Boolean = false,
    visibilityProvider: FlexibleDividerDecoration.VisibilityProvider? = null,
    colorProvider: FlexibleDividerDecoration.ColorProvider? = null,
    sizeProvider: FlexibleDividerDecoration.SizeProvider? = null,
    drawableProvider: FlexibleDividerDecoration.DrawableProvider? = null,
    marginProvider: HorizontalDividerItemDecoration.MarginProvider? = null
) {
    val builder = HorizontalDividerItemDecoration.Builder(context)
        .positionInsideItem(false)

    if (color != null) {
        builder.color(color)
    }

    if (drawableRes != null) {
        builder.drawable(drawableRes)
    }

    if (drawable != null) {
        builder.drawable(drawable)
    }

    if (size != null) {
        builder.size(size)
    }

    if (visibilityProvider != null) {
        builder.visibilityProvider(visibilityProvider)
    }

    if (colorProvider != null) {
        builder.colorProvider(colorProvider)
    }

    if (sizeProvider != null) {
        builder.sizeProvider(sizeProvider)
    }

    if (drawableProvider != null) {
        builder.drawableProvider(drawableProvider)
    }

    if (marginProvider != null) {
        builder.marginProvider(marginProvider)
    } else {
        if (margin != 0) {
            builder
                .margin(margin)
        } else {
            builder
                .margin(marginLeft, marginRight)
        }
    }



    if (isShowLastDivider) {
        builder.showLastDivider()
    }

    addItemDecoration(
        builder.build()
    )
}




