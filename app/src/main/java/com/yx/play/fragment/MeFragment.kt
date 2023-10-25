package com.yx.play.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yx.play.activity.TimeActivity
import com.yx.play.databinding.FragmentListBinding
import com.yx.play.databinding.FragmentMeBinding
import com.yx.play.ext.click

/**
 * @description
 * @version
 */
class MeFragment : BaseFragment() {

    private var mBinding: FragmentMeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMeBinding.inflate(inflater, container, false)

        initView()
        fetchData()
        return mBinding?.root
    }

    private fun initView() {
        mBinding?.layoutTime?.click {
            TimeActivity.newInstance(requireContext())
        }
    }

    private fun fetchData() {

    }
}