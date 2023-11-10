package com.yx.play.util

import com.blankj.utilcode.util.PathUtils
import com.sccdwxxyljx.com.BuildConfig
import java.io.File

/**
 * @description
 * @version
 */
object FileUtils {

    private var mOssPath = ""
    private var rootPathOld = ""

    fun getFileCacheDirectory(): String {

        if (mOssPath.isNotEmpty()) return mOssPath

        var root = if (BuildConfig.BUILD_TYPE == "release") {
            PathUtils.getInternalAppCachePath()
        } else {
            PathUtils.getExternalAppCachePath()
        }
        root = root.replace("${File.separator}cache", "")
        val stringBuffer = StringBuffer(root)
            .append(File.separator)
            .append("media")
            .append(File.separator)
            .append("oss")
            .append(File.separator)
        mOssPath =
            stringBuffer.toString()

        return mOssPath
    }
}