package com.yx.play.api

import androidx.annotation.Keep
import com.blankj.utilcode.util.GsonUtils
import com.yx.play.ext.responseToEntity
import com.yx.play.net.*
import okhttp3.Request


data class DetailsResponse(
    var code: Int,
    var msg: String? = "",
    var data: DetailsItemResponse? = null
)

//"id": 181,
//"vod_id": 46548,
//"type_id": 23,
//"type_id_1": 2,
//"vod_name": "日影之下",
//"vod_sub": "Eclipse of the Heart,In the Shadow of the Sun,Under the shadow of the Sun,Tai Ngao Tawan,日影之下 ใต้เงาตะวัน",
//"vod_status": 1,
//"vod_letter": "R",
//"vod_class": "剧情,爱情",
//"vod_pic": "https://img.ukuapi.com/upload/vod/20230330-1/9957db5ded20d75adb590f22f6ce830c.jpg",
//"vod_actor": "普林·苏帕拉,梅拉达·素斯丽,陂帕同·颂通亚纳奇,察猜·本班尼,杜昂达·东卡米尼",
//"vod_director": "翁拉吾·尼永萨,贾里亚·安福 Jariya Anfone",
//"vod_writer": "Danaya Supying",
//"vod_blurb": "Pareena的生活总是完美的、平和的、美丽的。她是父亲的好女儿，她的父亲像就像她的太阳一样直到死亡将他带走。看似神秘的死亡，给Pareena留下了无法接受的谜团。为了恢复父亲的尊严，她竭尽全力寻找真",
//"vod_remarks": "更新至11集",
//"vod_pubdate": "2023-03-29(泰国)",
//"vod_total": 16,
//"vod_area": "泰国",
//"vod_lang": "泰语",
//"vod_year": "2023",
//"vod_author": "",
//"vod_douban_id": "35633209",
//"vod_douban_score": "3.2",
//"vod_content": "<p>Pareena的生活总是完美的、平和的、美丽的。她是父亲的好女儿，她的父亲像就像她的太阳一样直到死亡将他带走。看似神秘的死亡，给Pareena留下了无法接受的谜团。为了恢复父亲的尊严，她竭尽全力寻找真相。她越是调查这个案件，她就越是了解到人有多面性。不管是她的父亲，还是Korn，一个向往自由的浪子，还是Korn的哥哥，对她来说像一个完美兄长的Karn。最重要的是她学会了理解人性，每个人都会犯错，没有人是完美的。</p>",
//"vod_play_from": "ukyun$$$ukm3u8",
//"vod_play_url": [{
//    "name": "第01集",
//    "url": "https://ukzy.ukubf8.com/20230330/vuqTEORD/index.m3u8"
//},
//{
//    "name": "第02集",
//    "url": "https://ukzy.ukubf8.com/20230331/arGIV3fM/index.m3u8"
//},
//{
//    "name": "第03集",
//    "url": "https://ukzy.ukubf8.com/20230406/QqZ4fx8p/index.m3u8"
//},
//{
//    "name": "第04集",
//    "url": "https://ukzy.ukubf8.com/20230408/bVazWOhf/index.m3u8"
//},
//{
//    "name": "第05集",
//    "url": "https://ukzy.ukubf8.com/20230413/WXMrEpaN/index.m3u8"
//},
//{
//    "name": "第06集",
//    "url": "https://ukzy.ukubf8.com/20230414/wlBTB2Bo/index.m3u8"
//},
//{
//    "name": "第07集",
//    "url": "https://ukzy.ukubf8.com/20230420/PJqyjOsI/index.m3u8"
//},
//{
//    "name": "第08集",
//    "url": "https://ukzy.ukubf8.com/20230421/JoUz7fON/index.m3u8"
//},
//{
//    "name": "第09集",
//    "url": "https://ukzy.ukubf8.com/20230501/jTY1C9pi/index.m3u8"
//},
//{
//    "name": "第10集",
//    "url": "https://ukzy.ukubf8.com/20230501/4iNfZpHf/index.m3u8"
//},
//{
//    "name": "第11集",
//    "url": "https://ukzy.ukubf8.com/20230504/tlPQi4sJ/index.m3u8"
//}
//],
//"type_name": "泰剧",
//"vod_score_num": 998,
//"created_time": 1683186560,
//"created_time_text": "2023-05-04 15:49:20"
@Keep
data class DetailsItemResponse(
    var id: Int = 0,
    var vod_id: Int = 0,
    var type_id: Int = 0,
    var type_id_1: Int = 0,
    var vod_name: String = "",
    var vod_sub: String = "",
    var vod_status: Int = 0,
    var vod_letter: String = "",
    var vod_class: String = "",
    var vod_pic: String = "",
    var vod_actor: String = "",
    var vod_director: String = "",
    var vod_writer: String = "",
    var vod_blurb: String = "",
    var vod_remarks: String = "",
    var vod_pubdate: String = "",
    var vod_total: Int = 0,
    var vod_area: String = "",
    var vod_lang: String = "",
    var vod_year: String = "",
    var vod_author: String = "",
    var vod_douban_id: String = "",
    var vod_douban_score: String = "",
    var vod_content: String = "",
    var vod_play_from: String = "",
    var vod_play_url: MutableList<DetailsItemPlayResponse>? = mutableListOf(),
    var type_name: String = "",
    var vod_score_num: Int = 0,
    var created_tim: Long = 0L,
    var created_time_text: String = ""
)

@Keep
data class DetailsItemPlayResponse(
    var name: String = "",
    var url: String = ""
)


object Details {
    fun execute(id: String): ResponseResult<DetailsItemResponse?> {
        val okHttpClient = NetManager.getInstance().getOkhttpClient()
        val url = "http://vod.wxxykj.cn/api/movies/get_detail?id=${id}"
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
                    DetailsItemResponse::class.java,
                    DetailsItemPlayResponse::class.java
                )
            val responseData: BaseResponseEntity<DetailsItemResponse>? =
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