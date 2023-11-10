package com.yx.play.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SPUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.youth.banner.adapter.BannerAdapter
import com.sccdwxxyljx.com.R
import com.yx.play.activity.DetalisActivity
import com.sccdwxxyljx.com.databinding.FragmentMainBinding
import com.yx.play.db.DataBaseManager
import com.yx.play.db.model.KaPianModel
import com.yx.play.dialog.HomePopupWindow
import com.yx.play.ext.click
import com.yx.play.ext.getColor
import com.yx.play.util.ImageIdUtils
import com.yx.play.util.JsonFileUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import razerdp.util.animation.AnimationHelper
import razerdp.util.animation.Direction
import razerdp.util.animation.ScaleConfig
import java.io.File

/**
 * @description
 * @version
 */
class HomeFragment : BaseFragment() {

    private var mBinding: FragmentMainBinding? = null

    private var position = -1

    private var mAdapter: KapianAdapter? = null

    private var contentType = "学习"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMainBinding.inflate(inflater, container, false)

        initView()
        fetchData()
        return mBinding?.root
    }

    private fun initView() {
        mBinding?.ivMore?.click {
            val chatInfoMorePopupWindow = HomePopupWindow(requireContext())
            chatInfoMorePopupWindow.setData(getPopVos())
            chatInfoMorePopupWindow.setBackgroundColor(R.color.color_000000_alpha_20.getColor())
            val showAnimation = AnimationHelper.asAnimation()
                .withScale(ScaleConfig().from(Direction.TOP, Direction.LEFT).scale(0f, 1f))
                .toShow()
            showAnimation.duration = 150
            chatInfoMorePopupWindow.showAnimation = showAnimation

            val dismissAnimation = AnimationHelper.asAnimation()
                .withScale(ScaleConfig().to(Direction.TOP, Direction.LEFT))
                .toDismiss()
            dismissAnimation.duration = 150
            chatInfoMorePopupWindow.dismissAnimation = dismissAnimation
            chatInfoMorePopupWindow.showPopupWindow(mBinding?.ivMore)
        }
    }

    private fun getPopVos(): MutableList<HomePopupWindow.HomeMoreItemVo> {
        val list = mutableListOf<HomePopupWindow.HomeMoreItemVo>()
        val vo1 = HomePopupWindow.HomeMoreItemVo()
        vo1.content = "学习"
        vo1.action = {
            contentType = "学习"
            fetchData()
        }
        list.add(vo1)
        val vo2 = HomePopupWindow.HomeMoreItemVo()
        vo2.content = "生活"
        vo2.action = {
            contentType = "生活"
            fetchData()
        }
        list.add(vo2)

        val vo3 = HomePopupWindow.HomeMoreItemVo()
        vo3.content = "运动"
        vo3.action = {
            contentType = "运动"
            fetchData()
        }
        list.add(vo3)

        val vo4 = HomePopupWindow.HomeMoreItemVo()
        vo4.content = "享受"
        vo4.action = {
            contentType = "享受"
            fetchData()
        }
        list.add(vo4)

        val vo5 = HomePopupWindow.HomeMoreItemVo()
        vo5.content = "时光"
        vo5.action = {
            contentType = "时光"
            fetchData()
        }
        list.add(vo5)

        val vo6 = HomePopupWindow.HomeMoreItemVo()
        vo6.content = "旅行"
        vo6.action = {
            contentType = "旅行"
            fetchData()
        }
        list.add(vo6)

        val vo7 = HomePopupWindow.HomeMoreItemVo()
        vo7.content = "欣赏"
        vo7.action = {
            contentType = "欣赏"
            fetchData()
        }
        list.add(vo7)

        return list
    }

    override fun onResume() {
        super.onResume()
        if (position == -1) return
        lifecycleScope.launch(Dispatchers.IO) {
            var data = mAdapter?.getData(position) ?: return@launch
            val newData =
                DataBaseManager.getInstance().getDataBase()?.kapianDao()?.getKaPian(data.id)
                    ?: return@launch
            withContext(Dispatchers.Main) {
//                data.time = newData.time
//                data.img = newData.img
//                data.title = newData.title
//                data.type = newData.type
//                data.content = newData.content
//                data.address = newData.address
                mAdapter?.datas?.set(position, newData)
                mAdapter?.notifyDataSetChanged()
                position = -1
            }
        }
    }

    private fun fetchData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val isOpen = SPUtils.getInstance().getBoolean("isOpenKapian", false)

            if (!isOpen) {
                JsonFileUtils.readKaPian(this@HomeFragment.requireContext())
            }

            val list =
                DataBaseManager.getInstance().getDataBase()?.kapianDao()?.getTypeKaPian(contentType)

            withContext(Dispatchers.Main) {
                mAdapter = KapianAdapter(list ?: mutableListOf())
                mBinding?.banner?.setAdapter(mAdapter)
                    ?.addBannerLifecycleObserver((this@HomeFragment))
                    ?.setBannerGalleryMZ(20)
                mAdapter?.setOnBannerListener { data, position ->
                    this@HomeFragment.position = position
                    DetalisActivity.newInstance(this@HomeFragment.requireContext(), data.id)
                }
            }
//            val result = Recommend.execute()
//            if (result is ResponseResult.Success) {
//                val data = result.value?.toMutableList()
//                val dataMap = data?.groupBy { it.type_id_1 }
//                val list = mutableListOf<MultiItemEntity>()
//                list.add(HearEntity("电影"))
//                list.addAll(
//                    dataMap?.get(1)
//                        ?.sortedByDescending { BigDecimal(it.vod_douban_score).toFloat() }
//                        ?: mutableListOf())
//
//                list.add(HearEntity("电视剧"))
//                list.addAll(
//                    dataMap?.get(2)
//                        ?.sortedByDescending { BigDecimal(it.vod_douban_score).toFloat() }
//                        ?: mutableListOf())
//
//                list.add(HearEntity("综艺"))
//                list.addAll(
//                    dataMap?.get(3)
//                        ?.sortedByDescending { BigDecimal(it.vod_douban_score).toFloat() }
//                        ?: mutableListOf())
//
//                list.add(HearEntity("动漫"))
//                list.addAll(
//                    dataMap?.get(4)
//                        ?.sortedByDescending { BigDecimal(it.vod_douban_score).toFloat() }
//                        ?: mutableListOf())
//
//                list.add(HearEntity("记录片"))
//                list.addAll(
//                    dataMap?.get(24)
//                        ?.sortedByDescending { BigDecimal(it.vod_douban_score).toFloat() }
//                        ?: mutableListOf())
//                withContext(Dispatchers.Main) {
//                    gridManager.spanSizeLookup = SpecialSpanSizeLookup(list)
//                    mAdapter.setList(list)
//                }
//            }
        }
    }

    inner class KapianViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivDiary: AppCompatImageView
        val tvTitle: AppCompatTextView
        val tvContent: AppCompatTextView
        val tvAddress: AppCompatTextView
        val tvTime: AppCompatTextView


        init {
            ivDiary = view.findViewById(R.id.ivDiary)
            tvTitle = view.findViewById(R.id.tvTitle)
            tvContent = view.findViewById(R.id.tvContent)
            tvAddress = view.findViewById(R.id.tvAddress)
            tvTime = view.findViewById(R.id.tvTime)
        }
    }


    inner class KapianAdapter(val datas: MutableList<KaPianModel>) :
        BannerAdapter<KaPianModel, KapianViewHolder>(datas) {
        override fun onCreateHolder(parent: ViewGroup?, viewType: Int): KapianViewHolder {
            return KapianViewHolder(
                LayoutInflater.from(parent?.context).inflate(R.layout.item_diary, parent, false)
            )
        }

        override fun onBindView(
            holder: KapianViewHolder?,
            data: KaPianModel?,
            position: Int,
            size: Int
        ) {
            holder?.tvTitle?.text = data?.title
            holder?.tvContent?.text = data?.content
            holder?.tvAddress?.text = data?.address
            holder?.tvTime?.text = data?.time
            if (data?.type != "1") {
                holder?.ivDiary?.let {
                    Glide.with(this@HomeFragment)
                        .load(File(data?.img))
                        .dontAnimate()
                        .skipMemoryCache(false)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(it)
                }
            } else {
                val resId = ImageIdUtils.getImageId(data.img)
                holder?.ivDiary?.setImageResource(resId)
            }
        }
    }

    override fun setDate(contentType: String) {
        super.setDate(contentType)
        this.contentType = contentType

        fetchData()
    }
}