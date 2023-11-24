package com.yx.play.api

import androidx.annotation.Keep
import com.blankj.utilcode.util.GsonUtils
import com.yx.play.ext.responseToEntity
import com.yx.play.net.*
import okhttp3.Request
import java.lang.StringBuilder


//"id": 70,
//"vod_name": "拳击传奇",
//"vod_sub": "职业拳击,拳击传奇 Prizefighter: The Life of Jem Belcher",
//"vod_class": "传记",
//"vod_pic": "https://img.ukuapi.com/upload/vod/20230503-1/d1158440464a58ecfa38ea6ca191b20b.jpg",
//"vod_remarks": "正片",
//"vod_douban_score": "1.7",
//"vod_score_num": 535,
//"created_time_text": ""
@Keep
data class CategoryListItemResponse(
    val created_time_text: String = "",
    val id: Long = 0,
    val vod_class: String = "",
    val vod_douban_score: String = "",
    val vod_name: String = "",
    val vod_pic: String = "",
    val vod_remarks: String = "",
    val vod_score_num: Int = 0,
    val vod_sub: String = ""
)

object CategoryList {
    fun execute(
        parentTypeId: Int,
        typeId: Int? = 0,
        order: String = "最热",
        vodArea: String? = "",
        vodLang: String? = "",
        vodYear: String? = "",
        page: Int
    ): ResponseResult<MutableList<CategoryListItemResponse>?> {
        val okHttpClient = NetManager.getInstance().getOkhttpClient()

        var params = "type_id_1=$parentTypeId&order=$order"

        if (typeId!= null){
            params = params.plus("&type_id=$typeId")
        }
        if (!vodArea.isNullOrEmpty()){
            params = params.plus("&vod_area=$vodArea")
        }

        if (!vodLang.isNullOrEmpty()){
            params = params.plus("&vod_lang=$vodLang")
        }

        if (!vodYear.isNullOrEmpty()){
            params = params.plus("&vod_year=$vodYear")
        }

        params = params.plus("&page=$page")

        val url =
            "http://vod.wxxykj.cn/api/movies/get_list?${params}"
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
                    GsonUtils.getListType(CategoryListItemResponse::class.java)
                )
            val responseData: BaseResponseEntity<MutableList<CategoryListItemResponse>>? =
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