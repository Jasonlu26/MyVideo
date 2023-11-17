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
import com.yx.play.databinding.FragmentHistoryBinding
import com.yx.play.databinding.FragmentHomeBinding
import com.yx.play.databinding.FragmentMovieBinding
import com.yx.play.databinding.ItemVideoHistoryBinding
import com.yx.play.db.DataBaseManager
import com.yx.play.db.model.YXHistoryRecordModel
import com.yx.play.ext.addVerticalItemDecoration
import com.yx.play.ext.click
import com.yx.play.ext.dpToPx
import com.yx.play.ext.getColor
import com.yx.play.util.DateUtil
import com.yx.play.util.IntentUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @description
 * @version
 */
class MovieFragment : Fragment() {
    private lateinit var mBinding: FragmentMovieBinding
    private val mAdapter = SearchActivity.BinderAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMovieBinding.inflate(inflater, container, false)

        initView()

        return mBinding?.root
    }

    private fun initView() {

//        mBinding.ivClear.click {
//            clear()
//            mAdapter.setList(mutableListOf())
//            mAdapter.setEmptyView(R.layout.empty_layout)
//        }
//
//        mAdapter.addItemBinder(HistoryItemBinder())
//        mBinding.listHistory.layoutManager = LinearLayoutManager(context)
//        mBinding.listHistory.adapter = mAdapter
//
//        mBinding.listHistory.addVerticalItemDecoration(
//            color = R.color.transparent.getColor(),
//            size = 10f.dpToPx(),
//            isShowLastDivider = false
//        )
//
//        mAdapter.setOnItemClickListener { adapter, view, position ->
//            val item = mAdapter.data[position]
//            if (item !is YXHistoryRecordModel) return@setOnItemClickListener
//
//            IntentUtils.intentDetails(requireActivity(), item.tvId)
//        }

    }

    private fun fetchData() {
//        lifecycleScope.launch(Dispatchers.IO) {
//            val historyList =
//                DataBaseManager.getInstance().getDataBase()?.historyDataDao()?.getAllHistory()
//
//            withContext(Dispatchers.Main) {
//                mAdapter.setList(historyList)
//                mAdapter.setEmptyView(R.layout.empty_layout)
//            }
//        }
    }


    inner class HistoryItemBinder :
        QuickViewBindingItemBinder<YXHistoryRecordModel, ItemVideoHistoryBinding>() {
        override fun convert(
            holder: BinderVBHolder<ItemVideoHistoryBinding>,
            data: YXHistoryRecordModel
        ) {
            Glide.with(context)
                .load(data.imageUrl)
                .dontAnimate()
                .skipMemoryCache(false)
                .centerCrop()
                .error(R.drawable.uk_image_fail1)
                .override(80f.dpToPx(), 80f.dpToPx())
                .into(holder.viewBinding.ivHistoryVideoThumb)

            holder.viewBinding.tvHistoryVideoName.text = data.name + "-" + data.playName
            holder.viewBinding.tvHistoryVideoTime.text = if (data.playDuration < data.duration) {
                "剩余：${DateUtil.getVideoTimeString(data.duration - data.playDuration)}"
            } else if (data.playDuration == data.duration) {
                "观看结束"
            } else {
                " "
            }
        }

        override fun onCreateViewBinding(
            layoutInflater: LayoutInflater,
            parent: ViewGroup,
            viewType: Int
        ) = ItemVideoHistoryBinding.inflate(layoutInflater, parent, false)
    }
}