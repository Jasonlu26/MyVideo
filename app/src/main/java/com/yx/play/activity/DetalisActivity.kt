package com.yx.play.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.yx.play.R
import com.yx.play.api.Details
import com.yx.play.databinding.ActivityDetalisBinding
import com.yx.play.ext.bindView
import com.yx.play.ext.click
import com.yx.play.net.ResponseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetalisActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityDetalisBinding
    private var id: String = ""

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
                    mBinding.tvTitle.text = data?.vod_name
                    mBinding.tvVideoName.text = data?.vod_name
                    mBinding.tvVideoCode.text =
                        data?.vod_douban_score + "/" + data?.vod_year + "/" + data?.vod_lang + "/" + data?.type_name
                    mBinding.jzVideo.setUp(
                        "https://ukzyvod3.ukubf5.com/20230415/9HciOKan/index.m3u8",
                        data?.vod_name
                    )
                    mBinding.jzVideo.posterImageView.setImageURI(Uri.parse(data?.vod_pic))

                    mBinding.tvVideoTips.text = "导演：${data?.vod_director}\n"+
                            "作者：${data?.vod_writer}\n"+
                            "主演：${data?.vod_actor}\n"+
                            "类型：${data?.vod_class}\n"+
                            "地区：${data?.vod_area}\n"+
                            "${data?.vod_content}"
                }
            }
        }
    }

    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }
}