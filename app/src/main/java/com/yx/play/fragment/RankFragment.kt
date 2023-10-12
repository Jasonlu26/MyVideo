package com.yx.play.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.binder.QuickViewBindingItemBinder
import com.yx.play.R
import com.yx.play.activity.SearchActivity
import com.yx.play.api.Rank
import com.yx.play.api.RankItemResponse
import com.yx.play.databinding.FragmentRankBinding
import com.yx.play.databinding.ItemRankVideoBinding
import com.yx.play.db.model.YXHistoryRecordModel
import com.yx.play.ext.addVerticalItemDecoration
import com.yx.play.ext.dpToPx
import com.yx.play.ext.getColor
import com.yx.play.net.ResponseResult
import com.yx.play.util.IntentUtils
import com.yx.play.util.VideoUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * @description
 * @version
 */
class RankFragment : Fragment() {
    private lateinit var mBinding: FragmentRankBinding
    private val mAdapter = SearchActivity.BinderAdapter()
    private var page = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentRankBinding.inflate(inflater, container, false)

        initView()
        fetchData()
        return mBinding?.root
    }

    private fun initView() {


        mAdapter.addItemBinder(RankItemBinder())
        mBinding.listRank.layoutManager = LinearLayoutManager(context)
        mBinding.listRank.adapter = mAdapter

        mBinding.listRank.addVerticalItemDecoration(
            color = R.color.transparent.getColor(),
            size = 10f.dpToPx(),
            isShowLastDivider = true
        )

        mAdapter.setOnItemClickListener { adapter, view, position ->
            val item = mAdapter.data[position]
            if (item !is RankItemResponse) return@setOnItemClickListener

            IntentUtils.intentDetails(requireActivity(), "${item.id}")
        }

        mAdapter.loadMoreModule.setOnLoadMoreListener {
            page++
            loadMore()
        }

    }

    private fun fetchData() {
        page = 1
        lifecycleScope.launch(Dispatchers.IO) {
            val result = Rank.execute(page)

            if (result is ResponseResult.Success) {
                val data = result.value
                withContext(Dispatchers.Main) {
                    mAdapter.setList(data)
                    mAdapter.setEmptyView(R.layout.empty_layout)
                }
            }
        }
    }

    private fun loadMore() {
        lifecycleScope.launch(Dispatchers.IO) {
            val result = Rank.execute(page)

            if (result is ResponseResult.Success) {
                val data = result.value
                withContext(Dispatchers.Main) {
                    if (page == 1) {
                        mAdapter.setList(data)
                        mAdapter.setEmptyView(R.layout.empty_layout)
                    } else {
                        mAdapter.addData(data ?: mutableListOf())
                    }

                    if (data.isNullOrEmpty()){
                        mAdapter.loadMoreModule.loadMoreEnd();
                    } else {
                        mAdapter.loadMoreModule.loadMoreComplete()
                    }
                }
            }
        }
    }


    inner class RankItemBinder :
        QuickViewBindingItemBinder<RankItemResponse, ItemRankVideoBinding>() {
        override fun convert(
            holder: BinderVBHolder<ItemRankVideoBinding>,
            data: RankItemResponse
        ) {
            Glide.with(context)
                .load(data.vod_pic)
                .dontAnimate()
                .skipMemoryCache(false)
                .centerCrop()
                .error(R.drawable.uk_image_fail1)
                .override(100f.dpToPx(), 120f.dpToPx())
                .into(holder.viewBinding.ivRankThumb)

            holder.viewBinding.tvVideoName.text = data.vod_name
            holder.viewBinding.tvVideoTips.text =
                data.vod_year + "/" + VideoUtils.getVideoType(data.type_id_1) + "/" + data.vod_area
            holder.viewBinding.tvVideoContent.text = data.vod_blurb
            val typeName = if (data.vod_class.isEmpty()) {
                data.type_name
            } else {
                data.vod_class
            }
            holder.viewBinding.tvVideoType.text = typeName
        }

        override fun onCreateViewBinding(
            layoutInflater: LayoutInflater,
            parent: ViewGroup,
            viewType: Int
        ) = ItemRankVideoBinding.inflate(layoutInflater, parent, false)
    }
}