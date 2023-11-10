package com.yx.play.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.SpanUtils
import com.sccdwxxyljx.com.R
import com.sccdwxxyljx.com.databinding.ActivityTimeBinding
import com.yx.play.ext.bindView
import com.yx.play.ext.click
import com.yx.play.ext.dpToPx
import com.yx.play.ext.getColor
import com.yx.play.util.DateUtil
import com.yx.play.util.TimePickerUtil
import kotlin.random.Random

/**
 * @description
 * @version
 */
class TimeActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityTimeBinding

    companion object {
        fun newInstance(context: Context) {
            val intent = Intent(context, TimeActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityTimeBinding.inflate(layoutInflater).bindView(this)
        initView()
        fetchData()
    }

    private fun initView() {
        mBinding.ivBack.click {
            finish()
        }

        mBinding.tvTime.click {
            TimePickerUtil.showDatePick(this)
                .onComplete { year, month, day ->
                    val time = "$year-$month-$day"
                    SPUtils.getInstance().put("yxTime", time)
                    val days = DateUtil.getBetweenDays(time)

                    mBinding.tvTime.text = SpanUtils().append("我们已经在一起")
                        .append("$days")
                        .setForegroundColor(R.color.c_FF4500.getColor())
                        .append("天了")
                        .create()
                }
        }
    }

    private fun fetchData() {
        getTime()
        getGeYan()
    }

    private fun getTime() {
        val time = SPUtils.getInstance().getString("yxTime")
        if (time.isEmpty()) return

        val days = DateUtil.getBetweenDays(time)

        mBinding.tvTime.text = SpanUtils().append("我们已经在一起")
            .append(" $days ")
            .setFontSize(24f.dpToPx())
            .setForegroundColor(R.color.c_FF4500.getColor())
            .append("天了")
            .create()
    }

    private fun getGeYan() {
        val datas = SPUtils.getInstance().getStringSet("geyan").toMutableSet()

        if (datas.isEmpty()) return

        val position = Random.nextInt(0, datas.size)

        val str = datas.elementAtOrNull(position)

        if (str.isNullOrEmpty()) return

        mBinding.tvGeyan.text = str
    }
}