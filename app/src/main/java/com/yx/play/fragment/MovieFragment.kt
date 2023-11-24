package com.yx.play.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseBinderAdapter
import com.chad.library.adapter.base.binder.QuickViewBindingItemBinder
import com.sccdwxxyljx.com.R
import com.sccdwxxyljx.com.databinding.FragmentMovieBinding
import com.sccdwxxyljx.com.databinding.ItemVideoCategoryBinding
import com.sccdwxxyljx.com.databinding.ItemVideoRecommendBinding
import com.yx.play.activity.SearchActivity
import com.yx.play.api.CategoryList
import com.yx.play.api.CategoryListItemResponse
import com.yx.play.api.CategoryResponse
import com.yx.play.api.CategoryType
import com.yx.play.ext.addHorizontalItemDecoration
import com.yx.play.ext.dpToPx
import com.yx.play.ext.getColor
import com.yx.play.ext.gone
import com.yx.play.ext.visible
import com.yx.play.net.ResponseResult
import com.yx.play.util.IntentUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal

/**
 * @description
 * @version
 */
class MovieFragment(val parentId: Int) : BaseMovieFragment() {
    private lateinit var mBinding: FragmentMovieBinding
    private val mAdapter = SearchActivity.BinderAdapter()
    private val adapter1 = BaseBinderAdapter()
    private val adapter2 = BaseBinderAdapter()
    private val adapter3 = BaseBinderAdapter()
    private val adapter4 = BaseBinderAdapter()
    private val adapter5 = BaseBinderAdapter()

    private var mPage = 1

    private var selectOrder: String = "最热"
    private var selectTypeId: Int? = null
    private var selectArea: String? = ""
    private var selectLang: String? = ""
    private var selectYear: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMovieBinding.inflate(inflater, container, false)

        initView()

        return mBinding.root
    }

    private fun initView() {
        val manager1 = LinearLayoutManager(context)
        manager1.orientation = LinearLayoutManager.HORIZONTAL
        adapter1.addItemBinder(VideoCategoryItemBinder())
        mBinding.rv1.layoutManager = manager1
        mBinding.rv1.adapter = adapter1
        adapter1.setOnItemClickListener { adapter, view, position ->
            val datas = adapter.data as MutableList<CategoryType>
            datas.forEach { type ->
                type.isSelect = false
            }
            val item = datas[position]
            item.isSelect = true

            adapter1.notifyDataSetChanged()

            selectOrder = item.name
            mPage = 1
            fetchData()
        }


        val manager2 = LinearLayoutManager(context)
        manager2.orientation = LinearLayoutManager.HORIZONTAL
        adapter2.addItemBinder(VideoCategoryItemBinder())
        mBinding.rv2.layoutManager = manager2
        mBinding.rv2.adapter = adapter2

        adapter2.setOnItemClickListener { adapter, view, position ->
            val datas = adapter.data as MutableList<CategoryType>
            datas.forEach { type ->
                type.isSelect = false
            }
            val item = datas[position]
            item.isSelect = true

            adapter2.notifyDataSetChanged()

            if (item.name == "全部") {
                selectTypeId = null
            } else {
                selectTypeId = item.id
            }
            mPage = 1
            fetchData()
        }


        val manager3 = LinearLayoutManager(context)
        manager3.orientation = LinearLayoutManager.HORIZONTAL
        adapter3.addItemBinder(VideoCategoryItemBinder())
        mBinding.rv3.layoutManager = manager3
        mBinding.rv3.adapter = adapter3

        adapter3.setOnItemClickListener { adapter, view, position ->
            val datas = adapter.data as MutableList<CategoryType>
            datas.forEach { type ->
                type.isSelect = false
            }
            val item = datas[position]
            item.isSelect = true

            adapter3.notifyDataSetChanged()

            if (item.name == "全部") {
                selectArea = ""
            } else {
                selectArea = item.name
            }
            mPage = 1
            fetchData()
        }


        val manager4 = LinearLayoutManager(context)
        manager4.orientation = LinearLayoutManager.HORIZONTAL
        adapter4.addItemBinder(VideoCategoryItemBinder())
        mBinding.rv4.layoutManager = manager4
        mBinding.rv4.adapter = adapter4

        adapter4.setOnItemClickListener { adapter, view, position ->
            val datas = adapter.data as MutableList<CategoryType>
            datas.forEach { type ->
                type.isSelect = false
            }
            val item = datas[position]
            item.isSelect = true

            adapter4.notifyDataSetChanged()

            if (item.name == "全部") {
                selectLang = ""
            } else {
                selectLang = item.name
            }
            mPage = 1
            fetchData()
        }


        val manager5 = LinearLayoutManager(context)
        manager5.orientation = LinearLayoutManager.HORIZONTAL
        adapter5.addItemBinder(VideoCategoryItemBinder())
        mBinding.rv5.layoutManager = manager5
        mBinding.rv5.adapter = adapter5

        adapter5.setOnItemClickListener { adapter, view, position ->
            val datas = adapter.data as MutableList<CategoryType>
            datas.forEach { type ->
                type.isSelect = false
            }
            val item = datas[position]
            item.isSelect = true

            adapter5.notifyDataSetChanged()

            if (item.name == "全部") {
                selectYear = ""
            } else {
                selectYear = item.name
            }
            mPage = 1
            fetchData()
        }



        mAdapter.addItemBinder(VideoItemBinder())
        val gridManager = GridLayoutManager(context, 3)
        mBinding.rvContent.addHorizontalItemDecoration(
            color = R.color.transparent.getColor(),
            size = 10f.dpToPx(),
            isShowLastDivider = true
        )

        mBinding.rvContent.layoutManager = gridManager
        mBinding.rvContent.adapter = mAdapter

        mAdapter.loadMoreModule.setOnLoadMoreListener {
            mPage++
            fetchData()
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            val data = mAdapter.data[position]
            if (data !is CategoryListItemResponse) return@setOnItemClickListener
            IntentUtils.intentDetails(requireActivity(), data.id.toString())
        }
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

    override fun setData(data: CategoryResponse) {
        if (data.orders.isEmpty()) {
            mBinding.rv1.gone()
        } else {
            mBinding.rv1.visible()

            val list = mutableListOf<CategoryType>()
            data.orders.forEachIndexed { index, s ->
                val vo = CategoryType()
                vo.id = index
                vo.name = s
                vo.isSelect = (index == 0)
                list.add(vo)
            }
            adapter1.setList(list)
        }
        if (data.types.isEmpty()) {
            mBinding.rv2.gone()
        } else {
            mBinding.rv2.visible()

            val list = data.types
            val vo = CategoryType(id = -1, name = "全部", isSelect = true)
            list.add(0, vo)
            adapter2.setList(list)
        }

        if (data.vodAreas.isEmpty()) {
            mBinding.rv3.gone()
        } else {
            mBinding.rv3.visible()

            val list = mutableListOf<CategoryType>()
            val vo = CategoryType(id = -1, name = "全部", isSelect = true)
            list.add(vo)
            data.vodAreas.forEachIndexed { index, s ->
                val vo = CategoryType()
                vo.id = index
                vo.name = s
                list.add(vo)
            }
            adapter3.setList(list)
        }

        if (data.vodLangs.isEmpty()) {
            mBinding.rv4.gone()
        } else {
            mBinding.rv4.visible()

            val list = mutableListOf<CategoryType>()
            val vo = CategoryType(id = -1, name = "全部", isSelect = true)
            list.add(vo)
            data.vodLangs.forEachIndexed { index, s ->
                val vo = CategoryType()
                vo.id = index
                vo.name = s
                list.add(vo)
            }
            adapter4.setList(list)
        }

        if (data.vodYears.isEmpty()) {
            mBinding.rv5.gone()
        } else {
            mBinding.rv5.visible()

            val list = mutableListOf<CategoryType>()
            val vo = CategoryType(id = -1, name = "全部", isSelect = true)
            list.add(vo)
            data.vodYears.forEachIndexed { index, s ->
                val vo = CategoryType()
                vo.id = index
                vo.name = s
                list.add(vo)
            }
            adapter5.setList(list)
        }

        fetchData()
    }


    private fun fetchData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val result = CategoryList.execute(
                parentTypeId = parentId,
                typeId = selectTypeId,
                order = selectOrder,
                vodArea = selectArea,
                vodLang = selectLang,
                vodYear = selectYear,
                page = mPage
            )

            if (result !is ResponseResult.Success) return@launch
            withContext(Dispatchers.Main) {
                val data = result.value
                if (data.isNullOrEmpty()) {
                    mAdapter.loadMoreModule.loadMoreEnd()
                    return@withContext
                }
                mAdapter.loadMoreModule.loadMoreComplete()
                if (mPage == 1) {
                    mAdapter.setList(data)
                } else {
                    mAdapter.addData(data)
                }
            }

        }
    }


    inner class VideoCategoryItemBinder :
        QuickViewBindingItemBinder<CategoryType, ItemVideoCategoryBinding>() {
        override fun convert(
            holder: BinderVBHolder<ItemVideoCategoryBinding>,
            data: CategoryType
        ) {
            if (data.isSelect) {
                holder.viewBinding.tvVideoNumName.setBackgroundResource(R.drawable.bg_video_category)
                holder.viewBinding.tvVideoNumName.setTextColor(R.color.white.getColor())
            } else {
                holder.viewBinding.tvVideoNumName.setBackgroundResource(0)
                holder.viewBinding.tvVideoNumName.setTextColor(R.color.c_0D1324.getColor())
            }
            holder.viewBinding.tvVideoNumName.text = data.name
        }

        override fun onCreateViewBinding(
            layoutInflater: LayoutInflater,
            parent: ViewGroup,
            viewType: Int
        ) = ItemVideoCategoryBinding.inflate(layoutInflater, parent, false)
    }


    inner class VideoItemBinder :
        QuickViewBindingItemBinder<CategoryListItemResponse, ItemVideoRecommendBinding>() {
        override fun convert(
            holder: BinderVBHolder<ItemVideoRecommendBinding>,
            data: CategoryListItemResponse
        ) {
            if (BigDecimal(data.vod_douban_score).toFloat() == 0.0f) {
                holder.viewBinding.tvVideoCode.gone()
            } else {

                holder.viewBinding.tvVideoCode.visible()
                holder.viewBinding.tvVideoCode.text = data.vod_douban_score
            }
            Glide.with(context)
                .load(data.vod_pic)
                .dontAnimate()
//                    .skipMemoryCache(false)
                .centerCrop()
                .error(R.drawable.uk_image_fail1)
//                    .override(300f.dpToPx(),300f.dpToPx())
                .encodeQuality(40)
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