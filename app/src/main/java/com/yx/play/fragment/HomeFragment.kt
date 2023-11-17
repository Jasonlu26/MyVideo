package com.yx.play.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.StringUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.yx.play.R
import com.yx.play.activity.HistoryActivity
import com.yx.play.activity.MainActivity
import com.yx.play.activity.SearchActivity
import com.yx.play.api.Recommend
import com.yx.play.api.RecommendItemResponse
import com.yx.play.databinding.FragmentHomeBinding
import com.yx.play.db.DataBaseManager
import com.yx.play.ext.*
import com.yx.play.net.ResponseResult
import com.yx.play.util.DateUtil
import com.yx.play.util.IntentUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal

/**
 * @description
 * @version
 */
class HomeFragment : Fragment() {

    private lateinit var mBinding: FragmentHomeBinding
    private var mAdapter = VideoRecommendAdapter(mutableListOf())
    private var historyId = ""

    private val gridManager = GridLayoutManager(context, 3)

    private var fragments = mapOf<Int, Fragment>()
    private var tabs = arrayListOf<CustomTabEntity>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)

        initView()
        fetchData()
        return mBinding?.root
    }

    private fun initView() {
        //        mAdapter.addItemBinder(ItemVideoRecommendBinder())
//        mBinding.listView.adapter = mAdapter
//
////        val decoration = VerticalSectionDecoration.create(this)
////            .sectionTextSize(R.dimen.sp_16.getDimension().toFloat())
////            .sectionTextColor(R.color.text_0D1324.getColor())
////            .sectionTextLeftOffset(R.dimen.dp_16.getDimension().toFloat())
////            .sectionSize(R.dimen.dp_30.getDimension())
////            .size(R.dimen.dp_0_5.getDimension())
////            .sectionDrawable(R.drawable.decoration_contact_section_bg)
//////            .drawable(R.drawable.decoration_contact_list_bg)
////            .showLast(false)
////            .sectionProvider(object : VerticalSectionDecoration.SectionProvider {
////                override fun sectionName(position: Int, parent: RecyclerView?): String? {
////                    return getHeaderName(position)
////                }
////
////            })
////            .build()
////        mBinding.listView.addItemDecoration(decoration)
//        mBinding.listView.addHorizontalItemDecoration(
//            color = R.color.transparent.getColor(),
//            size = 10f.dpToPx(),
//            isShowLastDivider = true
//        )
//
//        mBinding.listView.layoutManager = gridManager



        mAdapter.setOnItemClickListener { adapter, view, position ->
            val data = mAdapter.data[position]
            if (data !is RecommendItemResponse) return@setOnItemClickListener
            IntentUtils.intentDetails(requireActivity(), data.video_id)
        }

        mBinding.tvSearch.click {
            SearchActivity.startIntent(requireActivity())
        }

        mBinding.ivHistory.click {
            HistoryActivity.startIntent(requireActivity())
        }

        mBinding.layoutHistory.click {
            if (historyId.isEmpty()) return@click
            IntentUtils.intentDetails(requireActivity(), historyId)
        }
    }

    private fun fetchData() {
        fragments = getFragments()

        tabs = getTabs() as ArrayList<CustomTabEntity>

        mBinding.vpBanner.adapter = FragmentResourceVpAdapter(childFragmentManager, lifecycle)
        mBinding.vpBanner.offscreenPageLimit = 3


        mBinding.tabResource.setTabData(tabs)


        mBinding.tabResource.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                mBinding.vpBanner.currentItem = position
            }

            override fun onTabReselect(position: Int) {
            }
        })

        mBinding.vpBanner.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                mBinding.tabResource.currentTab = position
            }
        })
//        lifecycleScope.launch(Dispatchers.IO) {
//            val result = Recommend.execute()
//            if (result is ResponseResult.Success) {
//                val data = result.value?.toMutableList()
//                val dataMap = data?.groupBy { it.type_id_1 }
//                val list = mutableListOf<MultiItemEntity>()
//                list.add(HomeFragment.HearEntity("电影"))
//                list.addAll(
//                    dataMap?.get(1)
//                        ?.sortedByDescending { BigDecimal(it.vod_douban_score).toFloat() }
//                        ?: mutableListOf())
//
//                list.add(HomeFragment.HearEntity("电视剧"))
//                list.addAll(
//                    dataMap?.get(2)
//                        ?.sortedByDescending { BigDecimal(it.vod_douban_score).toFloat() }
//                        ?: mutableListOf())
//
//                list.add(HomeFragment.HearEntity("综艺"))
//                list.addAll(
//                    dataMap?.get(3)
//                        ?.sortedByDescending { BigDecimal(it.vod_douban_score).toFloat() }
//                        ?: mutableListOf())
//
//                list.add(HomeFragment.HearEntity("动漫"))
//                list.addAll(
//                    dataMap?.get(4)
//                        ?.sortedByDescending { BigDecimal(it.vod_douban_score).toFloat() }
//                        ?: mutableListOf())
//
//                list.add(HomeFragment.HearEntity("记录片"))
//                list.addAll(
//                    dataMap?.get(24)
//                        ?.sortedByDescending { BigDecimal(it.vod_douban_score).toFloat() }
//                        ?: mutableListOf())
//                withContext(Dispatchers.Main) {
//                    gridManager.spanSizeLookup = SpecialSpanSizeLookup(list)
//                    mAdapter.setList(list)
//                }
//            }
//        }
    }

    fun getHeaderName(pos: Int): String? {
//
        return if (pos >= 0 && pos < mAdapter.data.size) {
            val data = mAdapter.data[pos]
            if (data is RecommendItemResponse) {
                return when (data.type_id_1) {
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
            } else {
                null
            }
        } else {
            null
        }
    }
    override fun onResume() {
        super.onResume()
        lifecycleScope.launch(Dispatchers.IO) {
            val history =
                DataBaseManager.getInstance().getDataBase()?.historyDataDao()?.getNewHistory()
            withContext(Dispatchers.Main) {
                if (history == null) {
                    mBinding.layoutHistory.gone()
                } else {
                    historyId = history.tvId
                    val time = DateUtil.getVideoTimeString(history.playDuration)
                    mBinding.tvHistoryTime.text = "$time |"
                    mBinding.tvHistoryName.text = history.name
                    mBinding.layoutHistory.visible()
                }
            }
        }
    }


    private fun getTabs() = arrayListOf(
        TabLayoutEntry("电影"),
        TabLayoutEntry("电视剧"),
        TabLayoutEntry("综艺"),
        TabLayoutEntry("动漫"),
        TabLayoutEntry("纪录片"),
    )

    private fun getFragments() = mapOf(
        0 to MovieFragment(),
        1 to MovieFragment(),
        2 to MovieFragment(),
        3 to MovieFragment(),
        4 to MovieFragment(),
    )

    //    inner class ItemVideoRecommendBinder :
//        QuickViewBindingItemBinder<RecommendItemResponse, ItemVideoRecommendBinding>() {
//        override fun convert(
//            holder: BinderVBHolder<ItemVideoRecommendBinding>,
//            data: RecommendItemResponse
//        ) {
//            if (data.vod_douban_score.isEmpty()) {
//                holder.viewBinding.tvVideoCode.gone()
//            } else {
//                holder.viewBinding.tvVideoCode.visible()
//                holder.viewBinding.tvVideoCode.text = data.vod_douban_score
//            }
//            Glide.with(context)
//                .load(data.vod_pic)
//                .dontAnimate()
//                .skipMemoryCache(false)
//                .centerCrop()
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .into(holder.viewBinding.ivVideoThumb)
//            holder.viewBinding.tvVideoTips.text = data.vod_remarks
//            holder.viewBinding.tvVideoName.text = data.vod_name
//        }
//
//        override fun onCreateViewBinding(
//            layoutInflater: LayoutInflater,
//            parent: ViewGroup,
//            viewType: Int
//        ) = ItemVideoRecommendBinding.inflate(layoutInflater, parent, false)
//    }

    inner class VideoRecommendAdapter : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

        constructor(data: MutableList<MultiItemEntity>) : super(data) {

            addItemType(0, R.layout.item_recommend_header)
            addItemType(1, R.layout.item_video_recommend)
        }

        override fun convert(holder: BaseViewHolder, item: MultiItemEntity) {
            if (item.itemType == 1) {
                if (item !is RecommendItemResponse) return
                if (BigDecimal(item.vod_douban_score).toFloat() == 0.0f) {
                    holder.setGone(R.id.tvVideoCode, true)
                } else {
                    holder.setGone(R.id.tvVideoCode, false)
//                    holder.viewBinding.tvVideoCode.visible()
                    holder.setText(R.id.tvVideoCode, item.vod_douban_score)
                }
                Glide.with(context)
                    .load(item.vod_pic)
                    .dontAnimate()
//                    .skipMemoryCache(false)
                    .centerCrop()
                    .error(R.drawable.uk_image_fail1)
//                    .override(300f.dpToPx(),300f.dpToPx())
                    .encodeQuality(40)
                    .into(holder.getView(R.id.ivVideoThumb))
                holder.setText(R.id.tvVideoTips, item.vod_remarks)
                holder.setText(R.id.tvVideoName, item.vod_name)
            } else if (item.itemType == 0) {
                if (item !is HearEntity) return
                holder.setText(R.id.tvHeader, item.name)
            }
        }
    }

    data class HearEntity(val name: String) : MultiItemEntity {
        override val itemType: Int
            get() = 0
    }

    inner class SpecialSpanSizeLookup(val datas: MutableList<MultiItemEntity>?) :
        GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(i: Int): Int {
            // 返回在数据中定义的SpanSize
            val gridItem: MultiItemEntity? = datas?.get(i)
            if (gridItem?.itemType == 0) return 3
            return 1
        }
    }


    inner class FragmentResourceVpAdapter(
        val manager: FragmentManager,
        val lifecycle: Lifecycle
    ) : FragmentStateAdapter(manager, lifecycle) {

        override fun getItemCount() = fragments.size

        override fun createFragment(position: Int): Fragment =
            fragments[position] ?: error("")
    }

    inner class TabLayoutEntry(val title: String, private val selectedIcon: Int = 0, private val unSelectedIcon: Int = 0) :
        CustomTabEntity {

        override fun getTabUnselectedIcon(): Int {
            return unSelectedIcon
        }

        override fun getTabSelectedIcon(): Int {
            return selectedIcon
        }

        override fun getTabTitle(): String {
            return title
        }
    }

}