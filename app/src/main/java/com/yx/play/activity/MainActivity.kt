package com.yx.play.activity

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chad.library.adapter.base.BaseBinderAdapter
import com.chad.library.adapter.base.binder.QuickViewBindingItemBinder
import com.yx.play.R
import com.yx.play.api.Recommend
import com.yx.play.api.RecommendItemResponse
import com.yx.play.databinding.ActivityMainBinding
import com.yx.play.databinding.ItemVideoRecommendBinding
import com.yx.play.ext.*
import com.yx.play.net.NetManager
import com.yx.play.net.ResponseResult
import com.yx.play.view.VerticalSectionDecoration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Request


class MainActivity : AppCompatActivity() {

    private val mAdapter = BaseBinderAdapter()
    private lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater).bindView(this)
        initView()
        fetchData()
    }


    private fun initView() {
        mAdapter.addItemBinder(ItemVideoRecommendBinder())
        mBinding.listView.adapter = mAdapter
        mBinding.listView.layoutManager = GridLayoutManager(this, 3)
        val decoration = VerticalSectionDecoration.create(this)
            .sectionTextSize(R.dimen.sp_16.getDimension().toFloat())
            .sectionTextColor(R.color.text_0D1324.getColor())
            .sectionTextLeftOffset(R.dimen.dp_16.getDimension().toFloat())
            .sectionSize(R.dimen.dp_30.getDimension())
            .size(R.dimen.dp_0_5.getDimension())
            .sectionDrawable(R.drawable.decoration_contact_section_bg)
//            .drawable(R.drawable.decoration_contact_list_bg)
            .showLast(false)
            .sectionProvider(object : VerticalSectionDecoration.SectionProvider {
                override fun sectionName(position: Int, parent: RecyclerView?): String? {
                    return getHeaderName(position)
                }

            })
            .build()
        mBinding.listView.addItemDecoration(decoration)

        mAdapter.setOnItemClickListener { adapter, view, position ->
            val data = mAdapter.data[position]
            if (data !is RecommendItemResponse) return@setOnItemClickListener
            DetalisActivity.newInstance(this, data.video_id)
        }
    }

    private fun fetchData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val result = Recommend.execute()
            if (result is ResponseResult.Success){
                val data = result.value
                withContext(Dispatchers.Main){
                    mAdapter.setList(data)
                }
            }
        }
    }


    fun getHeaderName(pos: Int): String? {
//
        return if (pos >= 0 && pos < mAdapter.data.size) {
            val data = mAdapter.data[pos]
            if (data is RecommendItemResponse) {
                return data.type_name
            } else {
                null
            }
        } else {
            null
        }
    }

    inner class ItemVideoRecommendBinder :
        QuickViewBindingItemBinder<RecommendItemResponse, ItemVideoRecommendBinding>() {
        override fun convert(
            holder: BinderVBHolder<ItemVideoRecommendBinding>,
            data: RecommendItemResponse
        ) {
            if (data.vod_douban_score.isEmpty()) {
                holder.viewBinding.tvVideoCode.gone()
            } else {
                holder.viewBinding.tvVideoCode.visible()
                holder.viewBinding.tvVideoCode.text = data.vod_douban_score
            }
            Glide.with(context)
                .load(data.vod_pic)
                .dontAnimate()
                .skipMemoryCache(false)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.viewBinding.ivVideoThumb)
            holder.viewBinding.tvVideoTips.text = data.vod_remarks
            holder.viewBinding.tvVideoName.text = data.vod_name
        }

        override fun onCreateViewBinding(
            layoutInflater: LayoutInflater,
            parent: ViewGroup,
            viewType: Int
        ) = ItemVideoRecommendBinding.inflate(layoutInflater, parent, false)
    }
}