package com.yx.play.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseBinderAdapter
import com.chad.library.adapter.base.binder.QuickViewBindingItemBinder
import com.yx.play.R
import com.yx.play.databinding.DialogHomeContentBinding
import com.yx.play.databinding.ItemHomePopBinding
import com.yx.play.ext.addVerticalItemDecoration
import com.yx.play.ext.dpToPx
import com.yx.play.ext.getColor
import com.yx.play.ext.setOutlineProvider
import razerdp.basepopup.BasePopupWindow

class HomePopupWindow(context: Context) : BasePopupWindow(context) {

    private lateinit var mBinding: DialogHomeContentBinding

    private val mAdapter = BaseBinderAdapter()

    override fun onCreateContentView(): View {
        mBinding = DialogHomeContentBinding.inflate(LayoutInflater.from(context), null, false)
        return mBinding.root
    }

    fun setData(list: MutableList<HomeMoreItemVo>) {
        width = 220f.dpToPx()
        mBinding.rvHomeMorePop.adapter = mAdapter
        mAdapter.addItemBinder(HomePopupItemBinder())
        mBinding.rvHomeMorePop.layoutManager = LinearLayoutManager(context)
        mBinding.rvHomeMorePop.addVerticalItemDecoration(
            color = R.color.c_8F9BAF.getColor(),
            size = 0.5f.dpToPx(),
            marginLeft = 56f.dpToPx()
        )
        mBinding.rvHomeMorePop.setOutlineProvider(10f.dpToPx().toFloat())
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position)
            if (item !is HomeMoreItemVo) {
                return@setOnItemClickListener
            }
            item.action?.invoke()
            dismiss()
        }
        mAdapter.setList(list)
    }

    inner class HomePopupItemBinder :
        QuickViewBindingItemBinder<HomeMoreItemVo, ItemHomePopBinding>() {
        override fun convert(
            holder: BinderVBHolder<ItemHomePopBinding>,
            data: HomeMoreItemVo
        ) {
            holder.viewBinding.tvChatInfoMoreAction.text = data.content
        }

        override fun onCreateViewBinding(
            layoutInflater: LayoutInflater,
            parent: ViewGroup,
            viewType: Int
        ) = ItemHomePopBinding.inflate(layoutInflater, parent, false)
    }

    class HomeMoreItemVo {
        var content: String = ""
        var action: (() -> Unit)? = null
    }
}