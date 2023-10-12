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
import androidx.databinding.adapters.ImageViewBindingAdapter.setImageDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import androidx.viewpager2.adapter.FragmentStateAdapter

import com.yx.play.R
import com.yx.play.databinding.ActivityMainBinding
import com.yx.play.db.DataBaseManager
import com.yx.play.ext.bindView
import com.yx.play.ext.click
import com.yx.play.ext.gone
import com.yx.play.ext.visible
import com.yx.play.fragment.HistoryFragment
import com.yx.play.fragment.HomeFragment
import com.yx.play.fragment.RankFragment
import com.yx.play.util.DateUtil
import com.yx.play.util.IntentUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {


    private lateinit var mBinding: ActivityMainBinding

    private val PERMISSIONS_REQUEST_CODE = 1

    private val fragments = mapOf(
        0 to HomeFragment(),
        1 to RankFragment(),
        2 to HistoryFragment(),
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
        checkAndRequestPermission()
        initView()
//        fetchData()
    }


    private fun initView() {
//        mBinding.vpHome.addOnPageChangeListener(MyOnPageChangeListener())
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

//    @Keep
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//
//        val i = savedInstanceState.getInt("currentItem")
//        mBinding.vpHome.currentItem = i
//        setCurrentItem(i)
//    }

    private fun setCurrentItem(i: Int) {
        mBinding.indexTab0Img.setImageDrawable(
            ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.home_blur
            )
        )
        mBinding.indexTab1Img.setImageDrawable(
            ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.category_blur
            )
        )
        mBinding.indexTab2Img.setImageDrawable(
            ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.wo_blur
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
                        R.drawable.home
                    )
                )
                mBinding.indexTab0Text.setTextColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.c_theme
                    )
                )
            }
            1 -> {
                mBinding.indexTab1Img.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@MainActivity,
                        R.drawable.category
                    )
                )
                mBinding.indexTab1Text.setTextColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.c_theme
                    )
                )
            }
            2 -> {
                mBinding.indexTab2Img.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@MainActivity,
                        R.drawable.wo
                    )
                )
                mBinding.indexTab2Text.setTextColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.c_theme
                    )
                )
            }
            else -> {}
        }
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
        private val fragments: Map<Int, Fragment>,
    ) : FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount() = fragments.size

        override fun createFragment(position: Int): Fragment =
            fragments[position] ?: error("")

    }


}