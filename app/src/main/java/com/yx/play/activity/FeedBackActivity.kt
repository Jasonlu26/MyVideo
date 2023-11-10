package com.yx.play.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ToastUtils
import com.sccdwxxyljx.com.databinding.ActivityFeedbackBinding
import com.yx.play.ext.bindView
import com.yx.play.ext.click

/**
 * @description
 * @version
 */
class FeedBackActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityFeedbackBinding

    companion object {
        fun newInstance(context: Context) {
            val intent = Intent(context, FeedBackActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityFeedbackBinding.inflate(layoutInflater).bindView(this)
        initView()
        fetchData()
    }

    private fun initView() {
        mBinding.ivBack.click {
            finish()
        }

        mBinding.tvSure.click {
            if (mBinding.feedbackEdit.text?.toString().isNullOrEmpty()) {
                ToastUtils.showShort("请输入评论")
                return@click
            }

            ToastUtils.showShort("谢谢您的反馈意见")
            finish()
        }
    }

    private fun fetchData() {

    }


}