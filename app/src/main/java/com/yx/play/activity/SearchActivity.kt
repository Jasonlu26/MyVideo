package com.yx.play.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.KeyboardUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseBinderAdapter
import com.chad.library.adapter.base.binder.QuickViewBindingItemBinder
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.fqh.timeplan.timeplan.R
import com.fqh.timeplan.timeplan.databinding.ActivitySearchBinding
import com.fqh.timeplan.timeplan.databinding.ItemSearchVideoBinding
import com.yx.play.api.Search
import com.yx.play.api.SearchItemResponse
import com.yx.play.ext.*
import com.yx.play.net.ResponseResult
import com.yx.play.util.IntentUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @description
 * @version
 */
class SearchActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivitySearchBinding
    private val mAdapter = BinderAdapter()
    private var page = 1

    companion object {

        fun startIntent(context: Context) {
            val intent = Intent(context, SearchActivity::class.java)
            context.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySearchBinding.inflate(layoutInflater).bindView(this)
        initView()
//        fetchData()
    }

    private fun initView() {
        mBinding.tvGlobalSearchCancel.click {
            finish()
        }
        mBinding.btnGlobalSearchClear.click {
            mBinding.etGlobalSearchContent.setText("")
            mAdapter.setList(mutableListOf())
            mAdapter.setEmptyView(R.layout.layout_list_empty)
            page = 1
        }

        mAdapter.addItemBinder(SearchItemBinder())
        mBinding.rvSearchResult.layoutManager = LinearLayoutManager(this)
        mBinding.rvSearchResult.adapter = mAdapter

        mBinding.rvSearchResult.addVerticalItemDecoration(
            color = R.color.transparent.getColor(),
            size = 10f.dpToPx(),
            isShowLastDivider = false
        )


        mBinding.etGlobalSearchContent.setOnEditorActionListener { _, actionId, event ->

            if (mBinding.etGlobalSearchContent.text.isNullOrEmpty()) return@setOnEditorActionListener false
            if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER) || event.keyCode == KeyEvent.ACTION_DOWN) {
                page = 1
                search(mBinding.etGlobalSearchContent.text?.toString() ?: "", page)
            }
            KeyboardUtils.hideSoftInputByToggle(this)
            return@setOnEditorActionListener false
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            val item = mAdapter.data[position]
            if (item !is SearchItemResponse) return@setOnItemClickListener
            IntentUtils.intentDetails(this, "${item.id}")
        }
    }

    private fun search(keyword: String, page: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            val result = Search.execute(keyword, page)

            if (result is ResponseResult.Success) {
                val data = result.value
                withContext(Dispatchers.Main) {
                    if (page == 1) {
                        mAdapter.setList(data)
                        mAdapter.setEmptyView(R.layout.empty_layout)
                    } else {
                        mAdapter.addData(data ?: mutableListOf())
                    }
                }
            }
        }
    }

//    private fun setAdapter(data: MutableList<SearchItemResponse>, layoutId: Int) {
//        mAdapter.setList(data)
//    }


    inner class SearchItemBinder :
        QuickViewBindingItemBinder<SearchItemResponse, ItemSearchVideoBinding>() {
        override fun convert(
            holder: BinderVBHolder<ItemSearchVideoBinding>,
            data: SearchItemResponse
        ) {
            Glide.with(context)
                .load(data.vod_pic)
                .dontAnimate()
                .skipMemoryCache(false)
                .centerCrop()
                .error(R.drawable.uk_image_fail1)
                .override(80f.dpToPx(), 80f.dpToPx())
                .into(holder.viewBinding.ivSearchThumb)
            val str = when (data.type_id_1) {
                1 -> {
                    "电影"
                }
                2 -> {
                    "电视剧"
                }
                3 -> {
                    "综艺"
                }
                4 -> {
                    "动漫"
                }
                24 -> {
                    "纪录片"
                }
                else -> {
                    "其他"
                }
            }
            holder.viewBinding.tvVideoContent.text = data.vod_year + "/" + str + "/" + data.vod_area + "/" + data.vod_blurb
            holder.viewBinding.tvVideoName.text = data.vod_name
        }

        override fun onCreateViewBinding(
            layoutInflater: LayoutInflater,
            parent: ViewGroup,
            viewType: Int
        ) = ItemSearchVideoBinding.inflate(layoutInflater, parent, false)
    }

    class BinderAdapter : BaseBinderAdapter(), LoadMoreModule {

        override fun onViewRecycled(holder: BaseViewHolder) {
            super.onViewRecycled(holder)
        }
    }
}