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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.blankj.utilcode.util.SPUtils
import com.sccdwxxyljx.com.R
import com.sccdwxxyljx.com.databinding.ActivityMainBinding
import com.yx.play.ext.bindView
import com.yx.play.ext.click
import com.yx.play.fragment.BaseFragment
import com.yx.play.fragment.HomeFragment
import com.yx.play.fragment.ListFragment
import com.yx.play.fragment.MeFragment
import com.yx.play.util.JsonFileUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    //    private var mAdapter = VideoRecommendAdapter(mutableListOf())
    private lateinit var mBinding: ActivityMainBinding
    private val gridManager = GridLayoutManager(this, 3)

    private var historyId = ""
    private val PERMISSIONS_REQUEST_CODE = 1

    private val fragments: Map<Int, BaseFragment> = mapOf(
        0 to HomeFragment(),
        1 to ListFragment(),
        2 to MeFragment(),
    )

    companion object {
        fun startIntent(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
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
        mBinding.vpHome.adapter = MainVpAdapter(this, fragments)
        mBinding.vpHome.setCurrentItem(0, false)
        mBinding.vpHome.isUserInputEnabled = false
        mBinding.vpHome.offscreenPageLimit = 3

        mBinding.tab0.click {
            mBinding.vpHome.setCurrentItem(0, false)
            setCurrentItem(0)
        }

        mBinding.tab1.click {
            mBinding.vpHome.setCurrentItem(1, false)
            setCurrentItem(1)
        }

        mBinding.tab2.click {
            mBinding.vpHome.setCurrentItem(2, false)
            setCurrentItem(2)
        }
    }

    private fun fetchData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val datas = SPUtils.getInstance().getStringSet("geyan").toMutableSet()
            if (datas.isEmpty()) {
                JsonFileUtils.readGeYan(this@MainActivity)
            }
        }
    }

    private fun setCurrentItem(i: Int) {
        mBinding.indexTab0Img.setImageDrawable(
            ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.tab_1_off
            )
        )
        mBinding.indexTab1Img.setImageDrawable(
            ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.tab_2_off
            )
        )
        mBinding.indexTab2Img.setImageDrawable(
            ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.tab_3_off
            )
        )
        mBinding.indexTab0Text.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.c_8F9BAF
            )
        )
        mBinding.indexTab1Text.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.c_8F9BAF
            )
        )
        mBinding.indexTab2Text.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.c_8F9BAF
            )
        )
        when (i) {
            0 -> {
                mBinding.indexTab0Img.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@MainActivity,
                        R.drawable.tab_1_on
                    )
                )
                mBinding.indexTab0Text.setTextColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.c_FF4500
                    )
                )
            }
            1 -> {
                mBinding.indexTab1Img.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@MainActivity,
                        R.drawable.tab_2_on
                    )
                )
                mBinding.indexTab1Text.setTextColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.c_FF4500
                    )
                )
            }
            2 -> {
                mBinding.indexTab2Img.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@MainActivity,
                        R.drawable.tab_3_on
                    )
                )
                mBinding.indexTab2Text.setTextColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.c_FF4500
                    )
                )
            }
            else -> {}
        }
    }

//    fun getHeaderName(pos: Int): String? {
////
//        return if (pos >= 0 && pos < mAdapter.data.size) {
//            val data = mAdapter.data[pos]
//            if (data is RecommendItemResponse) {
//                return when (data.type_id_1) {
//                    1 -> {
//                        "电影"
//                    }
//                    2 -> {
//                        "电视剧"
//                    }
//                    3 -> {
//                        "综艺"
//                    }
//                    4 -> {
//                        "动漫"
//                    }
//                    24 -> {
//                        "纪录片"
//                    }
//                    else -> {
//                        "其他"
//                    }
//                }
//            } else {
//                null
//            }
//        } else {
//            null
//        }
//    }

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

    inner class MainVpAdapter(
        val fragmentActivity: FragmentActivity,
        private val fragments: Map<Int, BaseFragment>,
    ) : FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount() = fragments.size

        override fun createFragment(position: Int): Fragment =
            fragments[position] ?: error("")

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

//    inner class VideoRecommendAdapter : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
//
//        constructor(data: MutableList<MultiItemEntity>) : super(data) {
//
//            addItemType(0, R.layout.item_recommend_header)
//            addItemType(1, R.layout.item_video_recommend)
//        }
//
//        override fun convert(holder: BaseViewHolder, item: MultiItemEntity) {
//            if (item.itemType == 1) {
//                if (item !is RecommendItemResponse) return
//                if (BigDecimal(item.vod_douban_score).toFloat() == 0.0f) {
//                    holder.setGone(R.id.tvVideoCode, true)
//                } else {
//                    holder.setGone(R.id.tvVideoCode, false)
////                    holder.viewBinding.tvVideoCode.visible()
//                    holder.setText(R.id.tvVideoCode, item.vod_douban_score)
//                }
//                Glide.with(context)
//                    .load(item.vod_pic)
//                    .dontAnimate()
////                    .skipMemoryCache(false)
//                    .centerCrop()
//                    .error(R.drawable.uk_image_fail1)
////                    .override(300f.dpToPx(),300f.dpToPx())
//                    .encodeQuality(40)
//                    .into(holder.getView(R.id.ivVideoThumb))
//                holder.setText(R.id.tvVideoTips, item.vod_remarks)
//                holder.setText(R.id.tvVideoName, item.vod_name)
//            } else if (item.itemType == 0) {
//                if (item !is HearEntity) return
//                holder.setText(R.id.tvHeader, item.name)
//            }
//        }
//    }
//
//    data class HearEntity(val name: String) : MultiItemEntity {
//        override val itemType: Int
//            get() = 0
//    }
//
//    inner class SpecialSpanSizeLookup(val datas: MutableList<MultiItemEntity>?) : SpanSizeLookup() {
//        override fun getSpanSize(i: Int): Int {
//            // 返回在数据中定义的SpanSize
//            val gridItem: MultiItemEntity? = datas?.get(i)
//            if (gridItem?.itemType == 0) return 3
//            return 1
//        }
//    }
}