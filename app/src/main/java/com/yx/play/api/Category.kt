package com.yx.play.api

import androidx.annotation.Keep
import com.blankj.utilcode.util.GsonUtils
import com.google.gson.annotations.SerializedName
import com.yx.play.ext.responseToEntity
import com.yx.play.net.*
import okhttp3.Request


//{
//    "id": 1,
//    "name": "电影",
//    "order": [
//    "最新",
//    "最热",
//    "好评"
//    ],
//    "type": [{
//    "id": 6,
//    "name": "动作片"
//},
//    {
//        "id": 7,
//        "name": "喜剧片"
//    },
//    {
//        "id": 8,
//        "name": "爱情片"
//    },
//    {
//        "id": 9,
//        "name": "科幻片"
//    },
//    {
//        "id": 10,
//        "name": "恐怖片"
//    },
//    {
//        "id": 11,
//        "name": "剧情片"
//    },
//    {
//        "id": 12,
//        "name": "战争片"
//    },
//    {
//        "id": 20,
//        "name": "动漫电影"
//    }
//    ],
//    "vod_area": [
//    "意大利",
//    "比利时",
//    "美国"
//    ],
//    "vod_lang": [
//    "意大利语",
//    "拉丁语",
//    "英语"
//    ],
//    "vod_year": [
//    "2023",
//    "2022",
//    "2021",
//    "2020",
//    "2019",
//    "2018",
//    "2017",
//    "2016",
//    "2015",
//    "2014",
//    "2013",
//    "2012",
//    "2011",
//    "2010",
//    "2001-2009",
//    "2000以前"
//    ]
//}
@Keep
data class CategoryResponse(
    val id: Int = 0,
    val name: String = "",
    @SerializedName("order")
    val orders: MutableList<String> = mutableListOf(),
    @SerializedName("type")
    val types: MutableList<CategoryType> = mutableListOf(),
    @SerializedName("vod_area")
    val vodAreas: MutableList<String> = mutableListOf(),
    @SerializedName("vod_lang")
    val vodLangs: MutableList<String> = mutableListOf(),
    @SerializedName("vod_year")
    val vodYears: MutableList<String> = mutableListOf()
)

@Keep
data class CategoryType(
    var id: Int = 0,
    var name: String = "",
    var isSelect: Boolean = false
)

object Category {
    fun execute(): ResponseResult<MutableList<CategoryResponse>?> {
        val okHttpClient = NetManager.getInstance().getOkhttpClient()
        val url =
            "http://vod.wxxykj.cn/api/movies/get_type"
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
                    GsonUtils.getListType(CategoryResponse::class.java),
                    GsonUtils.getListType(CategoryType::class.java)
                )
            val responseData: BaseResponseEntity<MutableList<CategoryResponse>>? =
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