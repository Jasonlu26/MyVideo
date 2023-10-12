package com.yx.play.util

/**
 * @description
 * @version
 */
object VideoUtils {

    fun getVideoType(typeId: Int): String {
        return when (typeId) {
            1 -> {
                "电影"
            }
            2 -> {
                "电视剧"
            }
            3 -> {
                "综艺"
            }
            4 -> {
                "动漫"
            }
            24 -> {
                "纪录片"
            }
            else -> {
                "其他"
            }
        }
    }
}