package com.yx.play.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.binder.QuickViewBindingItemBinder
import com.yx.play.R
import com.yx.play.api.SearchItemResponse
import com.yx.play.databinding.ActivityHistoryBinding
import com.yx.play.databinding.ItemSearchVideoBinding
import com.yx.play.databinding.ItemVideoHistoryBinding
import com.yx.play.db.DataBaseManager
import com.yx.play.db.model.YXHistoryRecordModel
import com.yx.play.ext.*
import com.yx.play.util.DateUtil
import com.yx.play.util.IntentUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @description
 * @version
 */

class HistoryActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityHistoryBinding
    private val mAdapter = SearchActivity.BinderAdapter()

    companion object {

        fun startIntent(context: Context) {
            val intent = Intent(context, HistoryActivity::class.java)
            context.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHistoryBinding.inflate(layoutInflater).bindView(this)
        initView()
        fetchData()
    }

    private fun initView() {
        mBinding.ivBack.click {
            finish()
        }

        mBinding.ivClear.click {
            clear()
            mAdapter.setList(mutableListOf())
            mAdapter.setEmptyView(R.layout.empty_layout)
        }

        mAdapter.addItemBinder(HistoryItemBinder())
        mBinding.listHistory.layoutManager = LinearLayoutManager(this)
        mBinding.listHistory.adapter = mAdapter

        mBinding.listHistory.addVerticalItemDecoration(
            color = R.color.transparent.getColor(),
            size = 10f.dpToPx(),
            isShowLastDivider = false
        )

        mAdapter.setOnItemClickListener { adapter, view, position ->
            val item = mAdapter.data[position]
            if (item !is YXHistoryRecordModel) return@setOnItemClickListener

            IntentUtils.intentDetails(this, item.tvId)
        }

    }

    private fun fetchData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val historyList =
                DataBaseManager.getInstance().getDataBase()?.historyDataDao()?.getAllHistory()

            withContext(Dispatchers.Main) {
                mAdapter.setList(historyList)
                mAdapter.setEmptyView(R.layout.empty_layout)
            }
        }
    }

    private fun clear() {
        lifecycleScope.launch(Dispatchers.IO) {
            DataBaseManager.getInstance().getDataBase()?.historyDataDao()?.deleteAll()
        }
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

            holder.viewBinding.tvHistoryVideoName.text = data.name
        }

        override fun onCreateViewBinding(
            layoutInflater: LayoutInflater,
            parent: ViewGroup,
            viewType: Int
        ) = ItemVideoHistoryBinding.inflate(layoutInflater, parent, false)
    }
}