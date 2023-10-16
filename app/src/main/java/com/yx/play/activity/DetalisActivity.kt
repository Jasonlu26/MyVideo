package com.yx.play.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.fqh.timeplan.timeplan.R
import com.fqh.timeplan.timeplan.databinding.ActivityDetalisBinding
import com.yx.play.api.Details
import com.yx.play.db.DataBaseManager
import com.yx.play.db.model.YXHistoryRecordModel
import com.yx.play.ext.bindView
import com.yx.play.ext.click
import com.yx.play.net.ResponseResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal


class DetalisActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityDetalisBinding
    private var id: String = ""

    var name: String = ""
    var playUrl: String = ""
    var imageUrl: String = ""
    var playName: String = ""

    companion object {
        fun newInstance(context: Context, id: String) {
            val intent = Intent(context, DetalisActivity::class.java)
            intent.putExtra("id", id)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityDetalisBinding.inflate(layoutInflater).bindView(this)
        id = intent.getStringExtra("id") ?: ""
        initView()
        fetchData()
    }


    private fun initView() {
        mBinding.ivBack.click {
            finish()
        }

    }

    private fun fetchData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val result = Details.execute(id)
            if (result is ResponseResult.Success) {
                val data = result.value
                withContext(Dispatchers.Main) {
                    mBinding.tvVideoName.text = data?.vod_name
                    mBinding.tvVideoScore.text = if (BigDecimal(data?.vod_douban_score).toFloat() == 0.0f) {
                        ""
                    } else {
                        data?.vod_douban_score
                    }

                    Glide.with(this@DetalisActivity)
                        .load(data?.vod_pic)
                        .dontAnimate()
//                    .skipMemoryCache(false)
//                        .centerCrop()
                        .error(R.drawable.uk_image_fail1)
//                    .override(300f.dpToPx(),300f.dpToPx())
                        .into(mBinding.ivVideoPic)

                    mBinding.tvVideoActor.text = "主演：${data?.vod_actor}"
                    mBinding.tvVideoDirector.text = "导演：${data?.vod_director}"
                    mBinding.tvVideoTime.text = "上映时间：${data?.created_time_text?.split(" ")?.getOrNull(0)}"
                    mBinding.tvVideoArea.text = "地区：${data?.vod_area}"
                    val typeName = if (data?.vod_class.isNullOrEmpty()) {
                        data?.type_name
                    } else {
                        data?.vod_class
                    }
                    mBinding.tvVideoType.text = "类型：$typeName"


                    this@DetalisActivity.name = data?.vod_name ?: ""
                    this@DetalisActivity.imageUrl = data?.vod_pic ?: ""


                    mBinding.tvVideoTips.text =
                            "${Html.fromHtml(data?.vod_content)}"
                }
            }
        }
    }

    override fun finish() {
        super.finish()

        val model = YXHistoryRecordModel()
        model.tvId = id
        model.name = name
        model.imageUrl = imageUrl
        model.playUrl = playUrl
        model.playName = playName
        model.duration = 10L
        model.playDuration = 10L
        model.timeStamp = System.currentTimeMillis()
        CoroutineScope(Dispatchers.IO).launch {
            DataBaseManager.getInstance().getDataBase()?.historyDataDao()?.insertHistory(model)
        }

    }

}