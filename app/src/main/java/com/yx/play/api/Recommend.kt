package com.yx.play.api

import androidx.annotation.Keep
import com.blankj.utilcode.util.GsonUtils
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.yx.play.ext.responseToEntity
import com.yx.play.net.*
import okhttp3.Request


data class RecommendResponse(
    var code: Int,
    var msg: String? = "",
    var data: MutableList<RecommendItemResponse>? = mutableListOf()
)

//{
//    "id": 1136,
//    "video_id": 30104,
//    ion: createUnique: call
//    "type_id": 24,
//    ion: HostConnection::get() New Host Connection established 0xe83e11e0, tid 3099
//    "type_id_1": 24,
//    dress-space: allocate: Ask for block of size 0x100
//    "vod_name": "野性英伦",
//    dress-space: allocate: ioctl allocate returned offset 0x3f3ffe000 size 0x2000
//    "vod_class": "纪录片",
//    "vod_pic": "https:\/\/img.ukuapi.com\/upload\/vod\/20230617-1\/c038d51d4391f82343d7d16a1fc2041d.jpg",
//
//    "vod_blurb": "《野性英伦》由大卫·爱登堡爵士主持。制作团队在为期3年的拍摄中，采用最新摄影技术，记录不列颠群岛生物精彩而不为人知的生命故事。从蝴蝶的交锋到劫掠海鹦的海鸥，从剑桥郡郊区争夺配
//    偶的野马到近海的蓝旗金枪鱼",
//    "vod_remarks": "更新至03集",
//    "vod_pubdate": "2023-03-12(英国)",
//    "vod_total": 6,
//    "vod_area": "英国",
//    "vod_lang": "英语",
//    "vod_year": "2023",
//    "vod_douban_score": "8.9",
//    "vod_content": "<p>《野性英伦》由大卫·爱登堡爵士主持。制作团队在为期3年的拍摄中，采用最新摄影技术，记录不列颠群岛生物精彩而不为人知的生命故事。从蝴蝶的交锋到劫掠海鹦的海鸥，从剑桥郡
//    郊区争夺配偶的野马到近海的蓝旗金枪鱼和虎鲸，纪录片将聚焦不列颠的林地，草原，淡水与海洋。大卫·爱登堡爵士曾说：“在我漫长的人生中，我曾踏足我们星球的每个角落，我可以向大家保证，英伦诸岛的绝美风光与野生动物奇观，绝不逊于别
//    处的任何自然胜景。”<\/p>",
//    "type_name": "记录片",
//    "vod_score_num": 651,
//    "created_time": 1689557847,
//    "created_time_text": "2023-07-17 09:37:27"
//},
@Keep
data class RecommendItemResponse(
    var id: Int = 0,
    var video_id: String = "",
    var type_id: Int = 0,
    var type_id_1: Int = 0,
    var vod_name: String = "",
    var vod_class: String = "",
    var vod_pic: String = "",

    var vod_blurb: String = "",
    var vod_remarks: String = "",
    var vod_pubdate: String = "",
    var vod_total: Int = 0,
    var vod_area: String = "",
    var vod_lang: String = "",
    var vod_year: String = "",
    var vod_douban_score: String = "",
    var vod_content: String = "",
    var type_name: String = "",
    var vod_score_num: Int = 0,
    var created_tim: Long = 0L,
    var created_time_text: String = ""
) : MultiItemEntity {
    override val itemType: Int
        get() = 1
}

object Recommend {
    fun execute(): ResponseResult<MutableList<RecommendItemResponse>?> {
        val okHttpClient = NetManager.getInstance().getOkhttpClient()
        val url = "http://vod.wxspb.cn/api/movies/recommend"
        try {
            val req =
                Request.Builder().url(url)
                    .build()
            val response = okHttpClient?.newCall(req)?.execute()
            if (response?.isSuccessful == false) {
                return ResponseResult.Failure(TmException(TmmError.ERROR_COMMON))
            }

            val type =
                GsonUtils.getType(
                    BaseResponseEntity::class.java,
                    GsonUtils.getListType(RecommendItemResponse::class.java)
                )
            val responseData: BaseResponseEntity<MutableList<RecommendItemResponse>>? =
                response?.body?.string()?.responseToEntity(type)
            if (responseData?.isSuccess() == true) {
                //success code 200
                return ResponseResult.Success(responseData.data)
            }

            return ResponseResult.Failure(TmException.common())
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseResult.Failure(TmException.common())
        }
    }
}