package com.yx.play.util

import android.content.Context
import androidx.annotation.Keep
import com.alibaba.fastjson.JSON
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yx.play.db.DataBaseManager
import com.yx.play.db.model.KaPianModel
import com.yx.play.db.model.QingDanModel
import com.yx.play.ext.fromStringListJson
import com.yx.play.ext.toEntityList
import com.yx.play.ext.toJson
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * @description
 * @version
 */
object JsonFileUtils {
    fun readKaPian(context: Context) {
        var br: BufferedReader? = null
        var fileName = "reliankapian.json"
        try {
            br = BufferedReader(InputStreamReader(context.resources.assets.open(fileName)))
            var line: String? = null
            val sb = StringBuilder()
            while (br.readLine().also { line = it } != null) {
                sb.append(line)
            }
            br.close()
            val json = sb.toString().trimIndent()

            val gson = Gson()
            val dataType = object : TypeToken<Map<String, Category>>() {}.type
            val data: Map<String, Category> = gson.fromJson(json, dataType)

            // 现在你可以访问解析后的数据
            val 学习ContentList = data["学习"]?.content
            val 生活ContentList = data["生活"]?.content
            val 运动ContentList = data["运动"]?.content
            val 享受ContentList = data["享受"]?.content
            val 时光ContentList = data["时光"]?.content
            val 旅行ContentList = data["旅行"]?.content
            val 欣赏ContentList = data["欣赏"]?.content

            val kapianList = mutableListOf<KaPianModel>()
            学习ContentList?.forEach { content ->
                val model = KaPianModel()
                model.contentType = "学习"
                model.type = content.type
                model.img = content.img
                model.content = content.content
                model.time = content.time
                model.title = content.title
                model.address = content.address
                kapianList.add(model)
            }

            生活ContentList?.forEach { content ->
                val model = KaPianModel()
                model.contentType = "生活"
                model.type = content.type
                model.img = content.img
                model.content = content.content
                model.time = content.time
                model.title = content.title
                model.address = content.address
                kapianList.add(model)
            }

            运动ContentList?.forEach { content ->
                val model = KaPianModel()
                model.contentType = "运动"
                model.type = content.type
                model.img = content.img
                model.content = content.content
                model.time = content.time
                model.title = content.title
                model.address = content.address
                kapianList.add(model)
            }

            享受ContentList?.forEach { content ->
                val model = KaPianModel()
                model.contentType = "享受"
                model.type = content.type
                model.img = content.img
                model.content = content.content
                model.time = content.time
                model.title = content.title
                model.address = content.address
                kapianList.add(model)
            }


            时光ContentList?.forEach { content ->
                val model = KaPianModel()
                model.contentType = "时光"
                model.type = content.type
                model.img = content.img
                model.content = content.content
                model.time = content.time
                model.title = content.title
                model.address = content.address
                kapianList.add(model)
            }


            旅行ContentList?.forEach { content ->
                val model = KaPianModel()
                model.contentType = "旅行"
                model.type = content.type
                model.img = content.img
                model.content = content.content
                model.time = content.time
                model.title = content.title
                model.address = content.address
                kapianList.add(model)
            }


            欣赏ContentList?.forEach { content ->
                val model = KaPianModel()
                model.contentType = "欣赏"
                model.type = content.type
                model.img = content.img
                model.content = content.content
                model.time = content.time
                model.title = content.title
                model.address = content.address
                kapianList.add(model)
            }


            DataBaseManager.getInstance().getDataBase()?.kapianDao()?.insert(kapianList)
            SPUtils.getInstance().put("isOpenKapian", true)

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            br?.close()
        }
    }

    @Keep
    data class Content(
        val type: String,
        val content: String,
        val img: String,
        val title: String,
        val time: String,
        val address: String
    )

    @Keep
    data class Category(
        val content: List<Content>,
        val geyan: String
    )

    fun readQingDan(context: Context) {
        var br: BufferedReader? = null
        var fileName = "lianaiqingdan.json"
        try {
            br = BufferedReader(InputStreamReader(context.resources.assets.open(fileName)))
            var line: String? = null
            val sb = StringBuilder()
            while (br.readLine().also { line = it } != null) {
                sb.append(line)
            }
            br.close()
            val json = sb.toString().trimIndent()

            val datas = json.toEntityList<QingDan>()?.toMutableList() ?: return

            val list = mutableListOf<QingDanModel>()

            datas.forEach { qingDan ->
                qingDan.des.forEach { data ->

                    val model = QingDanModel()
                    model.contentType = qingDan.name
                    model.img = "p_moren_img"
                    model.title = data.title
                    model.type = data.type

                    list.add(model)

                }
            }

            DataBaseManager.getInstance().getDataBase()?.qingdanDao()?.insert(list)
            SPUtils.getInstance().put("isOpenQingDan", true)

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            br?.close()
        }
    }

    @Keep
    data class QingDan(
        val des: MutableList<QingDanData> = mutableListOf(),
        val name: String = ""
    )

    @Keep
    data class QingDanData(
        val img: String = "",
        val title: String = "",
        val type: String = "1"
    )


    fun readGeYan(context: Context) {
        var br: BufferedReader? = null
        var fileName = "geyan.json"
        try {
            br = BufferedReader(InputStreamReader(context.resources.assets.open(fileName)))
            var line: String? = null
            val sb = StringBuilder()
            while (br.readLine().also { line = it } != null) {
                sb.append(line)
            }
            br.close()
            val json = sb.toString().trimIndent()

            val datas = fromStringListJson(json).toMutableSet()

            SPUtils.getInstance().put("geyan", datas)

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            br?.close()
        }
    }
}