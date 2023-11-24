package com.yx.play.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import cn.jzvd.Jzvd
import com.adspace.sdk.adlistener.InterstitialAdListener
import com.chad.library.adapter.base.BaseBinderAdapter
import com.chad.library.adapter.base.binder.QuickViewBindingItemBinder
import com.kwad.sdk.core.b.a.it
import com.rh.sdk.api.RHInterstitialAd
import com.sccdwxxyljx.com.R
import com.sccdwxxyljx.com.databinding.ActivityDetalisBinding
import com.sccdwxxyljx.com.databinding.ItemVideoNumBinding
import com.yx.play.api.Details
import com.yx.play.api.DetailsItemPlayResponse
import com.yx.play.db.DataBaseManager
import com.yx.play.db.model.YXHistoryRecordModel
import com.yx.play.ext.addHorizontalItemDecoration
import com.yx.play.ext.addVerticalItemDecoration
import com.yx.play.ext.bindView
import com.yx.play.ext.click
import com.yx.play.ext.dpToPx
import com.yx.play.ext.getColor
import com.yx.play.net.ResponseResult
import com.yx.play.util.TestPosId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetalisActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityDetalisBinding
    private var id: String = ""

    private val mAdapter = BaseBinderAdapter()

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

        mAdapter.addItemBinder(ItemVideoBinder())
        mBinding.listVideo.layoutManager = GridLayoutManager(this, 5)
        mBinding.listVideo.addHorizontalItemDecoration(
            color = R.color.transparent.getColor(),
            size = 10f.dpToPx(),
            isShowLastDivider = true
        )

        mBinding.listVideo.addVerticalItemDecoration(
            color = R.color.transparent.getColor(),
            size = 10f.dpToPx(),
            isShowLastDivider = true
        )
        mBinding.listVideo.adapter = mAdapter

        mAdapter.setOnItemClickListener { adapter, view, position ->
            val item = mAdapter.getItem(position)
            if (item !is DetailsItemPlayResponse) {
                return@setOnItemClickListener
            }

            startPlay(playUrl = item.url, playName = item.name)
            mAdapter.data.forEach { vo ->
                if (vo is DetailsItemPlayResponse) {
                    vo.isSelect = false

                    if (vo.name == item.name) {
                        vo.isSelect = true
                    }
                }
            }

            mAdapter.notifyDataSetChanged()

        }
    }

    private fun startAd() {
        val rhInterstitialAd = RHInterstitialAd()
        rhInterstitialAd.loadAd(this, TestPosId.POSID_INTERSTITIAL.value, object :
            InterstitialAdListener {
            override fun onADCached() {

            }

            override fun onADOpen() {

            }

            override fun onADExposure() {

            }

            override fun onADClick() {

            }

            override fun onADClose() {
                mBinding.jzVideo.startVideo()
            }

            override fun onADError(p0: Int, p1: String?, p2: String?) {
                mBinding.jzVideo.startVideo()
            }
        })
    }

    private fun fetchData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val result = Details.execute(id)
            if (result is ResponseResult.Success) {
                val history =
                    DataBaseManager.getInstance().getDataBase()?.historyDataDao()?.getHistory(id)
                val data = result.value
                withContext(Dispatchers.Main) {
                    mBinding.tvTitle.text = data?.vod_name
                    mBinding.tvVideoName.text = data?.vod_name
                    mBinding.tvVideoCode.text =
                        data?.vod_douban_score + "/" + data?.vod_year + "/" + data?.vod_lang + "/" + data?.type_name

                    this@DetalisActivity.name = data?.vod_name ?: ""
                    this@DetalisActivity.imageUrl = data?.vod_pic ?: ""
                    if (history != null) {
                        startPlay(
                            playUrl = history.playUrl ?: "",
                            playName = history.playName ?: "",
                            seekDuration = history.playDuration
                        )
                        data?.vod_play_url?.forEach { item ->
                            if (item.name == history.playName) {
                                item.isSelect = true
                            }
                        }
                    } else {
                        startPlay(
                            playUrl = data?.vod_play_url?.get(0)?.url ?: "",
                            playName = data?.vod_play_url?.get(0)?.name ?: "",
                        )
                        data?.vod_play_url?.get(0)?.isSelect = true
                    }

                    mBinding.tvVideoTips.text = "导演：${data?.vod_director}\n" +
                            "作者：${data?.vod_writer}\n" +
                            "主演：${data?.vod_actor}\n" +
                            "类型：${data?.vod_class}\n" +
                            "地区：${data?.vod_area}\n" +
                            "${Html.fromHtml(data?.vod_content)}"
                    mAdapter.setList(data?.vod_play_url)
                }
            }
        }
    }

    private fun startPlay(playUrl: String, playName: String, seekDuration: Long = 0L) {

        this.playUrl = playUrl
        this.playName = playName
        mBinding.jzVideo.setUp(
            playUrl,
            playName
        )
        if (seekDuration != 0L) {
//            mBinding.jzVideo.progressBar.set
            mBinding.jzVideo.seekToInAdvance = seekDuration
        }
        startAd()
    }

    override fun finish() {
        super.finish()
        val duration = mBinding.jzVideo.duration
        val playDuration = mBinding.jzVideo.currentPositionWhenPlaying

        if (playDuration > 10) {
            val model = YXHistoryRecordModel()
            model.tvId = id
            model.name = name
            model.imageUrl = imageUrl
            model.playUrl = playUrl
            model.playName = playName
            model.duration = duration
            model.playDuration = playDuration
            model.timeStamp = System.currentTimeMillis()
            CoroutineScope(Dispatchers.IO).launch {
                DataBaseManager.getInstance().getDataBase()?.historyDataDao()?.insertHistory(model)
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


    inner class ItemVideoBinder :
        QuickViewBindingItemBinder<DetailsItemPlayResponse, ItemVideoNumBinding>() {
        override fun convert(
            holder: BinderVBHolder<ItemVideoNumBinding>,
            data: DetailsItemPlayResponse
        ) {
            holder.viewBinding.tvVideoNumName.text = data.name
            if (data.isSelect) {
                holder.viewBinding.tvVideoNumName.setBackgroundResource(R.drawable.bg_video_category)
                holder.viewBinding.tvVideoNumName.setTextColor(R.color.white.getColor())
            } else {
                holder.viewBinding.tvVideoNumName.setBackgroundResource(0)
                holder.viewBinding.tvVideoNumName.setTextColor(R.color.c_0D1324.getColor())
            }
        }

        override fun onCreateViewBinding(
            layoutInflater: LayoutInflater,
            parent: ViewGroup,
            viewType: Int
        ) = ItemVideoNumBinding.inflate(layoutInflater, parent, false)
    }
}