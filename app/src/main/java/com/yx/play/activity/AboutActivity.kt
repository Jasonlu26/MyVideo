package com.yx.play.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sccdwxxyljx.com.databinding.ActivityAboutBinding
import com.yx.play.ext.bindView
import com.yx.play.ext.click

/**
 * @description
 * @version
 */
class AboutActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityAboutBinding

    companion object {
        fun newInstance(context: Context) {
            val intent = Intent(context, AboutActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAboutBinding.inflate(layoutInflater).bindView(this)
        initView()
        fetchData()
    }

    private fun initView() {
        mBinding.ivBack.click {
            finish()
        }

    }

    private fun fetchData() {

    }


}