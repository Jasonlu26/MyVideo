package com.yx.play.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sccdwxxyljx.com.databinding.FragmentMeBinding
import com.yx.play.activity.AboutActivity
import com.yx.play.activity.FeedBackActivity
import com.yx.play.activity.TimeActivity
import com.yx.play.activity.XieyiActivity
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

        mBinding?.layoutAbout?.click {
            AboutActivity.newInstance(requireContext())
        }

        mBinding?.layoutPinglun?.click {
            FeedBackActivity.newInstance(requireContext())
        }


        mBinding?.layoutYinsi?.click {
            XieyiActivity.newInstance(requireContext())
        }
    }

    private fun fetchData() {

    }
}