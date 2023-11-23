package com.yx.play.fragment

import androidx.fragment.app.Fragment
import com.yx.play.api.CategoryResponse

/**
 * @description
 * @version
 */
abstract class BaseMovieFragment: Fragment() {
    abstract fun setData(data: CategoryResponse)
}