package com.yx.play.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.SPUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chad.library.adapter.base.BaseBinderAdapter
import com.chad.library.adapter.base.binder.QuickViewBindingItemBinder
import com.sccdwxxyljx.com.R
import com.sccdwxxyljx.com.databinding.FragmentListBinding
import com.sccdwxxyljx.com.databinding.ItemQingdanBinding
import com.yx.play.activity.QingDanDetalisActivity
import com.yx.play.db.DataBaseManager
import com.yx.play.db.model.QingDanModel
import com.yx.play.ext.addHorizontalItemDecoration
import com.yx.play.ext.dpToPx
import com.yx.play.ext.getColor
import com.yx.play.util.ImageIdUtils
import com.yx.play.util.JsonFileUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

/**
 * @description
 * @version
 */
class ListFragment : BaseFragment() {

    private var mBinding: FragmentListBinding? = null

    private val mAdapter1 = BaseBinderAdapter()
    private val mAdapter2 = BaseBinderAdapter()
    private val mAdapter3 = BaseBinderAdapter()
    private val mAdapter4 = BaseBinderAdapter()

    private var count1 = 0
    private var totalCount1 = 0

    private var count2 = 0
    private var totalCount2 = 0

    private var count3 = 0
    private var totalCount3 = 0

    private var count4 = 0
    private var totalCount4 = 0

    private var position1 = -1
    private var position2 = -1
    private var position3 = -1
    private var position4 = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentListBinding.inflate(inflater, container, false)

        initView()
        fetchData()
        return mBinding?.root
    }

    private fun initView() {
        val manager1 = GridLayoutManager(requireContext(), 4)
        mBinding?.rv1?.layoutManager = manager1
        mBinding?.rv1?.isNestedScrollingEnabled = false
        mBinding?.rv1?.addHorizontalItemDecoration(
            color = R.color.transparent.getColor(),
            size = 8f.dpToPx(),
            isShowLastDivider = true
        )
        mAdapter1.addItemBinder(ItemQingDanBinder())
        mBinding?.rv1?.adapter = mAdapter1

        mAdapter1.setOnItemClickListener { adapter, view, position ->
            val item = adapter.data[position]
            if (item !is QingDanModel) return@setOnItemClickListener
            position1 = position
            QingDanDetalisActivity.newInstance(requireContext(), item.id)
        }


        val manager2 = GridLayoutManager(requireContext(), 4)
        mBinding?.rv2?.layoutManager = manager2
        mBinding?.rv2?.isNestedScrollingEnabled = false
        mBinding?.rv2?.addHorizontalItemDecoration(
            color = R.color.transparent.getColor(),
            size = 8f.dpToPx(),
            isShowLastDivider = true
        )
        mAdapter2.addItemBinder(ItemQingDanBinder())
        mBinding?.rv2?.adapter = mAdapter2

        mAdapter2.setOnItemClickListener { adapter, view, position ->
            val item = adapter.data[position]
            if (item !is QingDanModel) return@setOnItemClickListener
            position2 = position
            QingDanDetalisActivity.newInstance(requireContext(), item.id)
        }


        val manager3 = GridLayoutManager(requireContext(), 4)
        mBinding?.rv3?.layoutManager = manager3
        mBinding?.rv3?.isNestedScrollingEnabled = false
        mBinding?.rv3?.addHorizontalItemDecoration(
            color = R.color.transparent.getColor(),
            size = 8f.dpToPx(),
            isShowLastDivider = true
        )
        mAdapter3.addItemBinder(ItemQingDanBinder())
        mBinding?.rv3?.adapter = mAdapter3

        mAdapter3.setOnItemClickListener { adapter, view, position ->
            val item = adapter.data[position]
            if (item !is QingDanModel) return@setOnItemClickListener
            position3 = position
            QingDanDetalisActivity.newInstance(requireContext(), item.id)
        }


        val manager4 = GridLayoutManager(requireContext(), 4)
        mBinding?.rv4?.layoutManager = manager4
        mBinding?.rv4?.isNestedScrollingEnabled = false
        mBinding?.rv4?.addHorizontalItemDecoration(
            color = R.color.transparent.getColor(),
            size = 8f.dpToPx(),
            isShowLastDivider = true
        )
        mAdapter4.addItemBinder(ItemQingDanBinder())
        mBinding?.rv4?.adapter = mAdapter4

        mAdapter4.setOnItemClickListener { adapter, view, position ->
            val item = adapter.data[position]
            if (item !is QingDanModel) return@setOnItemClickListener
            position4 = position
            QingDanDetalisActivity.newInstance(requireContext(), item.id)
        }
    }

    private fun fetchData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val isOpen = SPUtils.getInstance().getBoolean("isOpenQingDan", false)
            if (!isOpen) {
                JsonFileUtils.readQingDan(this@ListFragment.requireContext())
            }

            val list = DataBaseManager.getInstance().getDataBase()?.qingdanDao()?.getAllQingDan()

            val map = list?.groupBy { it.contentType }

            val list1 = map?.get("爱的初体验")?.toMutableList() ?: mutableListOf()
            val list2 = map?.get("享受慢时光")?.toMutableList() ?: mutableListOf()
            val list3 = map?.get("挑战不可能")?.toMutableList() ?: mutableListOf()
            val list4 = map?.get("爱的高光时刻")?.toMutableList() ?: mutableListOf()

            val listComplete1 = list1.filter { it.time.isNotEmpty() }.toMutableList()
            val listComplete2 = list2.filter { it.time.isNotEmpty() }.toMutableList()
            val listComplete3 = list3.filter { it.time.isNotEmpty() }.toMutableList()
            val listComplete4 = list4.filter { it.time.isNotEmpty() }.toMutableList()

            withContext(Dispatchers.Main) {
                count1 = listComplete1.size
                totalCount1 = list1.size

                count2 = listComplete2.size
                totalCount2 = list2.size


                count3 = listComplete3.size
                totalCount3 = list3.size


                count4 = listComplete4.size
                totalCount4 = list4.size

                mBinding?.tvListCount?.text = "${count1 + count2 + count3 + count4}/100"
                mBinding?.tvListCount1?.text = "$count1/$totalCount1"
                mBinding?.tvListCount2?.text = "$count2/$totalCount2"
                mBinding?.tvListCount3?.text = "$count3/$totalCount3"
                mBinding?.tvListCount4?.text = "$count4/$totalCount4"

                mAdapter1.setList(list1)
                mAdapter2.setList(list2)
                mAdapter3.setList(list3)
                mAdapter4.setList(list4)
            }

        }
    }

    override fun onResume() {
        super.onResume()
        if (position1 == -1 && position2 == -1 && position3 == -1 && position4 == -1) return

        lifecycleScope.launch(Dispatchers.IO) {
            if (position1 != -1) {
                val item = mAdapter1.data.getOrNull(position1)

                if (item !is QingDanModel) {
                    position1 = -1
                    return@launch
                }

                val data =
                    DataBaseManager.getInstance().getDataBase()?.qingdanDao()?.getQingDan(item.id)

                if (data == null) {
                    position1 = -1
                    return@launch
                }

                if (data.time.isEmpty()) {
                    position1 = -1
                    return@launch
                }

                if (item == data){
                    position1 = -1
                    return@launch
                }


                withContext(Dispatchers.Main) {
                    mAdapter1.data[position1] = data
                    val list = mAdapter1.data as MutableList<QingDanModel>
                    count1 = list.filter { it.time.isNotEmpty() }.toMutableList().size


                    mBinding?.tvListCount?.text = "${count1 + count2 + count3 + count4}/100"
                    mBinding?.tvListCount1?.text = "$count1/$totalCount1"


                    mAdapter1.notifyItemChanged(position1)
                    position1 = -1
                }


                return@launch
            }


            if (position2 != -1) {
                val item = mAdapter2.data.getOrNull(position2)

                if (item !is QingDanModel) {
                    position2 = -1
                    return@launch
                }

                val data =
                    DataBaseManager.getInstance().getDataBase()?.qingdanDao()?.getQingDan(item.id)

                if (data == null) {
                    position2 = -1
                    return@launch
                }

                if (data.time.isEmpty()) {
                    position2 = -1
                    return@launch
                }


                if (item == data){
                    position2 = -1
                    return@launch
                }

                withContext(Dispatchers.Main) {
                    mAdapter2.data[position2] = data

                    val list = mAdapter2.data as MutableList<QingDanModel>
                    count2 = list.filter { it.time.isNotEmpty() }.toMutableList().size

                    mBinding?.tvListCount?.text = "${count1 + count2 + count3 + count4}/100"
                    mBinding?.tvListCount2?.text = "$count2/$totalCount2"


                    mAdapter2.notifyItemChanged(position2)
                    position2 = -1
                }


                return@launch
            }


            if (position3 != -1) {
                val item = mAdapter3.data.getOrNull(position3)

                if (item !is QingDanModel) {
                    position3 = -1
                    return@launch
                }

                val data =
                    DataBaseManager.getInstance().getDataBase()?.qingdanDao()?.getQingDan(item.id)

                if (data == null) {
                    position3 = -1
                    return@launch
                }

                if (data.time.isEmpty()) {
                    position3 = -1
                    return@launch
                }

                if (item == data){
                    position3 = -1
                    return@launch
                }


                withContext(Dispatchers.Main) {
                    mAdapter3.data[position3] = data
                    val list = mAdapter3.data as MutableList<QingDanModel>
                    count3 = list.filter { it.time.isNotEmpty() }.toMutableList().size

                    mBinding?.tvListCount?.text = "${count1 + count2 + count3 + count4}/100"
                    mBinding?.tvListCount3?.text = "$count3/$totalCount3"


                    mAdapter3.notifyItemChanged(position3)
                    position3 = -1
                }


                return@launch
            }


            if (position4 != -1) {
                val item = mAdapter4.data.getOrNull(position4)

                if (item !is QingDanModel) {
                    position4 = -1
                    return@launch
                }

                val data =
                    DataBaseManager.getInstance().getDataBase()?.qingdanDao()?.getQingDan(item.id)

                if (data == null) {
                    position4 = -1
                    return@launch
                }

                if (data.time.isEmpty()) {
                    position4 = -1
                    return@launch
                }

                if (item == data){
                    position4 = -1
                    return@launch
                }


                withContext(Dispatchers.Main) {
                    mAdapter4.data[position4] = data
                    val list = mAdapter4.data as MutableList<QingDanModel>
                    count4 = list.filter { it.time.isNotEmpty() }.toMutableList().size

                    mBinding?.tvListCount?.text = "${count1 + count2 + count3 + count4}/100"
                    mBinding?.tvListCount4?.text = "$count4/$totalCount4"


                    mAdapter4.notifyItemChanged(position4)
                    position4 = -1
                }


            }
        }
    }


    inner class ItemQingDanBinder : QuickViewBindingItemBinder<QingDanModel, ItemQingdanBinding>() {
        override fun convert(holder: BinderVBHolder<ItemQingdanBinding>, data: QingDanModel) {
            holder.viewBinding.tvQingDanName.text = data.title
            if (data.type != "1") {

                Glide.with(this@ListFragment)
                    .load(File(data.img))
                    .dontAnimate()
                    .skipMemoryCache(false)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .error(R.drawable.p_moren_img)
                    .into(holder.viewBinding.ivQingDan)

            } else {
                val resId = ImageIdUtils.getImageId(data.img)
                holder.viewBinding.ivQingDan.setImageResource(resId)
            }
        }

        override fun onCreateViewBinding(
            layoutInflater: LayoutInflater,
            parent: ViewGroup,
            viewType: Int
        ) = ItemQingdanBinding.inflate(layoutInflater, parent, false)
    }

}