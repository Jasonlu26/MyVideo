package com.yx.play.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yx.play.R
import com.yx.play.api.Recommend
import com.yx.play.api.RecommendItemResponse
import com.yx.play.databinding.ActivityMainBinding
import com.yx.play.db.DataBaseManager
import com.yx.play.ext.*
import com.yx.play.net.ResponseResult
import com.yx.play.util.DateUtil
import com.yx.play.util.IntentUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal


class MainActivity : AppCompatActivity() {

    private var mAdapter = VideoRecommendAdapter(mutableListOf())
    private lateinit var mBinding: ActivityMainBinding
    private val gridManager = GridLayoutManager(this, 3)

    private var historyId = ""
    private val PERMISSIONS_REQUEST_CODE = 1

    companion object{
        fun startIntent(context: Context) {
            val intent = Intent(context, MainActivity:: class.java)
            context.startActivity(intent)
            return
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater).bindView(this)
//        checkAndRequestPermission()
        initView()
        fetchData()
    }


    private fun initView() {
//        mAdapter.addItemBinder(ItemVideoRecommendBinder())
        mBinding.listView.adapter = mAdapter

//        val decoration = VerticalSectionDecoration.create(this)
//            .sectionTextSize(R.dimen.sp_16.getDimension().toFloat())
//            .sectionTextColor(R.color.text_0D1324.getColor())
//            .sectionTextLeftOffset(R.dimen.dp_16.getDimension().toFloat())
//            .sectionSize(R.dimen.dp_30.getDimension())
//            .size(R.dimen.dp_0_5.getDimension())
//            .sectionDrawable(R.drawable.decoration_contact_section_bg)
////            .drawable(R.drawable.decoration_contact_list_bg)
//            .showLast(false)
//            .sectionProvider(object : VerticalSectionDecoration.SectionProvider {
//                override fun sectionName(position: Int, parent: RecyclerView?): String? {
//                    return getHeaderName(position)
//                }
//
//            })
//            .build()
//        mBinding.listView.addItemDecoration(decoration)
        mBinding.listView.addHorizontalItemDecoration(
            color = R.color.transparent.getColor(),
            size = 10f.dpToPx(),
            isShowLastDivider = true
        )
        mBinding.listView.layoutManager = gridManager

        mAdapter.setOnItemClickListener { adapter, view, position ->
            val data = mAdapter.data[position]
            if (data !is RecommendItemResponse) return@setOnItemClickListener
            IntentUtils.intentDetails(this, data.video_id)
        }

        mBinding.tvSearch.click {
            SearchActivity.startIntent(this)
        }

        mBinding.layoutHistory.click {
            if (historyId.isEmpty()) return@click
            IntentUtils.intentDetails(this, historyId)
        }

        mBinding.ivHistory.click {
            HistoryActivity.startIntent(this)
        }
    }

    private fun fetchData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val result = Recommend.execute()
            if (result is ResponseResult.Success) {
                val data = result.value?.toMutableList()
                val dataMap = data?.groupBy { it.type_id_1 }
                val list = mutableListOf<MultiItemEntity>()
                list.add(HearEntity("电影"))
                list.addAll(
                    dataMap?.get(1)
                        ?.sortedByDescending { BigDecimal(it.vod_douban_score).toFloat() }
                        ?: mutableListOf())

                list.add(HearEntity("电视剧"))
                list.addAll(
                    dataMap?.get(2)
                        ?.sortedByDescending { BigDecimal(it.vod_douban_score).toFloat() }
                        ?: mutableListOf())

                list.add(HearEntity("综艺"))
                list.addAll(
                    dataMap?.get(3)
                        ?.sortedByDescending { BigDecimal(it.vod_douban_score).toFloat() }
                        ?: mutableListOf())

                list.add(HearEntity("动漫"))
                list.addAll(
                    dataMap?.get(4)
                        ?.sortedByDescending { BigDecimal(it.vod_douban_score).toFloat() }
                        ?: mutableListOf())

                list.add(HearEntity("记录片"))
                list.addAll(
                    dataMap?.get(24)
                        ?.sortedByDescending { BigDecimal(it.vod_douban_score).toFloat() }
                        ?: mutableListOf())
                withContext(Dispatchers.Main) {
                    gridManager.spanSizeLookup = SpecialSpanSizeLookup(list)
                    mAdapter.setList(list)
                }
            }
        }
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
//        lifecycleScope.launch(Dispatchers.IO) {
//            val history =
//                DataBaseManager.getInstance().getDataBase()?.historyDataDao()?.getNewHistory()
//            withContext(Dispatchers.Main) {
//                if (history == null) {
//                    mBinding.layoutHistory.gone()
//                } else {
//                    historyId = history.tvId
//                    val time = DateUtil.getVideoTimeString(history.playDuration)
//                    mBinding.tvHistoryTime.text = "$time |"
//                    mBinding.tvHistoryName.text = history.name
//                    mBinding.layoutHistory.visible()
//                }
//            }
//        }
    }

    /**
     * 【非常重要】READ_PHONE_STATE 权限用于允许 SDK 获取用户标识，
     * 允许获取权限的，投放定向广告；不允许获取权限的用户，投放通投广告，会导致广告填充和 eCPM 单价下降。
     */
    private fun checkAndRequestPermission(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        var requestPermissions = arrayOf<String>()
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions = requestPermissions.plus(Manifest.permission.READ_PHONE_STATE)
        }
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions = requestPermissions.plus(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions = requestPermissions.plus(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (requestPermissions.isEmpty()) {
            return true
        }
        // 请求缺少的权限，在 onRequestPermissionsResult 中返回是否获得权限
        requestPermissions(requestPermissions, PERMISSIONS_REQUEST_CODE)
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        checkAndRequestPermission()
    }

    @Keep
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (supportFragmentManager.backStackEntryCount > 0 && keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed()
            return true
        }

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true)
            return true
        }

        return super.onKeyDown(keyCode, event)
    }

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

    inner class SpecialSpanSizeLookup(val datas: MutableList<MultiItemEntity>?) : SpanSizeLookup() {
        override fun getSpanSize(i: Int): Int {
            // 返回在数据中定义的SpanSize
            val gridItem: MultiItemEntity? = datas?.get(i)
            if (gridItem?.itemType == 0) return 3
            return 1
        }
    }
}