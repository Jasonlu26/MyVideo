package com.yx.play.util

import com.yx.play.R

/**
 * @description
 * @version
 */
object ImageIdUtils {
    fun getImageId(str: String): Int {
//        val resId = try {
//            val resourceName = "R.drawable.$str"
//            val field = R.drawable::class.java.getField(resourceName)
//            field.getInt(null)
//        } catch (e: Exception) {
//            // 处理资源不存在的情况
//            // 这里可以根据需求进行适当的处理，比如设置默认图片资源
//            0
//        }
//        return resId
        return when (str) {
            "m_lvxing_1" -> R.drawable.m_lvxing_1
            "m_lvxing_2" -> R.drawable.m_lvxing_2
            "m_lvxing_3" -> R.drawable.m_lvxing_3
            "m_lvxing_4" -> R.drawable.m_lvxing_4
            "m_lvxing_5" -> R.drawable.m_lvxing_5
            "m_lvxing_6" -> R.drawable.m_lvxing_6
            "m_lvxing_7" -> R.drawable.m_lvxing_7
            "m_lvxing_8" -> R.drawable.m_lvxing_8
            "m_lvxing_9" -> R.drawable.m_lvxing_9
            "m_lvxing_10" -> R.drawable.m_lvxing_10
            "m_lvxing_11" -> R.drawable.m_lvxing_11
            "m_lvxing_12" -> R.drawable.m_lvxing_12
            "m_shenghuo_1" -> R.drawable.m_shenghuo_1
            "m_shenghuo_2" -> R.drawable.m_shenghuo_2
            "m_shenghuo_3" -> R.drawable.m_shenghuo_3
            "m_shenghuo_4" -> R.drawable.m_shenghuo_4
            "m_shenghuo_5" -> R.drawable.m_shenghuo_5
            "m_shenghuo_6" -> R.drawable.m_shenghuo_6
            "m_shenghuo_7" -> R.drawable.m_shenghuo_7
            "m_shenghuo_8" -> R.drawable.m_shenghuo_8
            "m_shenghuo_9" -> R.drawable.m_shenghuo_9
            "m_shenghuo_10" -> R.drawable.m_shenghuo_10
            "m_shenghuo_11" -> R.drawable.m_shenghuo_11
            "m_shenghuo_12" -> R.drawable.m_shenghuo_12
            "m_shiguang_1" -> R.drawable.m_shiguang_1
            "m_shiguang_2" -> R.drawable.m_shiguang_2
            "m_shiguang_3" -> R.drawable.m_shiguang_3
            "m_shiguang_4" -> R.drawable.m_shiguang_4
            "m_shiguang_5" -> R.drawable.m_shiguang_5
            "m_shiguang_6" -> R.drawable.m_shiguang_6
            "m_shiguang_7" -> R.drawable.m_shiguang_7
            "m_shiguang_8" -> R.drawable.m_shiguang_8
            "m_xaingshou_1" -> R.drawable.m_xaingshou_1
            "m_xaingshou_2" -> R.drawable.m_xaingshou_2
            "m_xaingshou_3" -> R.drawable.m_xaingshou_3
            "m_xaingshou_4" -> R.drawable.m_xaingshou_4
            "m_xaingshou_5" -> R.drawable.m_xaingshou_5
            "m_xaingshou_6" -> R.drawable.m_xaingshou_6
            "m_xaingshou_7" -> R.drawable.m_xaingshou_7
            "m_xaingshou_8" -> R.drawable.m_xaingshou_8
            "m_xaingshou_9" -> R.drawable.m_xaingshou_9
            "m_xaingshou_10" -> R.drawable.m_xaingshou_10
            "m_xinshang_1" -> R.drawable.m_xinshang_1
            "m_xinshang_2" -> R.drawable.m_xinshang_2
            "m_xinshang_3" -> R.drawable.m_xinshang_3
            "m_xinshang_4" -> R.drawable.m_xinshang_4
            "m_xinshang_5" -> R.drawable.m_xinshang_5
            "m_xinshang_6" -> R.drawable.m_xinshang_6
            "m_xinshang_7" -> R.drawable.m_xinshang_7
            "m_xinshang_8" -> R.drawable.m_xinshang_8
            "m_xinshang_9" -> R.drawable.m_xinshang_9
            "m_xinshang_10" -> R.drawable.m_xinshang_10
            "m_xinshang_11" -> R.drawable.m_xinshang_11
            "m_xinshang_12" -> R.drawable.m_xinshang_12
            "m_xuexi_1" -> R.drawable.m_xuexi_1
            "m_xuexi_2" -> R.drawable.m_xuexi_2
            "m_xuexi_3" -> R.drawable.m_xuexi_3
            "m_xuexi_4" -> R.drawable.m_xuexi_4
            "m_xuexi_5" -> R.drawable.m_xuexi_5
            "m_xuexi_6" -> R.drawable.m_xuexi_6
            "m_xuexi_7" -> R.drawable.m_xuexi_7
            "m_xuexi_8" -> R.drawable.m_xuexi_8
            "m_xuexi_9" -> R.drawable.m_xuexi_9
            "m_yundong_1" -> R.drawable.m_yundong_1
            "m_yundong_2" -> R.drawable.m_yundong_2
            "m_yundong_3" -> R.drawable.m_yundong_3
            "m_yundong_4" -> R.drawable.m_yundong_4
            "m_yundong_5" -> R.drawable.m_yundong_5
            "m_yundong_6" -> R.drawable.m_yundong_6
            "m_yundong_7" -> R.drawable.m_yundong_7
            "m_yundong_8" -> R.drawable.m_yundong_8
            "m_yundong_9" -> R.drawable.m_yundong_9

            else -> 0
        }
    }
}