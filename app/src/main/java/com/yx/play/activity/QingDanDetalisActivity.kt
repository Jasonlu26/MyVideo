package com.yx.play.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.sccdwxxyljx.com.databinding.ActivityQingdanDetalisBinding
import com.yx.play.db.DataBaseManager
import com.yx.play.db.result.QingDanResult
import com.yx.play.ext.bindView
import com.yx.play.ext.click
import com.yx.play.util.FileUtils
import com.yx.play.util.GlideEngine
import com.yx.play.util.ImageIdUtils
import com.yx.play.util.TimePickerUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


class QingDanDetalisActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityQingdanDetalisBinding
    private var id: Long = 0L

    private var newImg = ""
    private var img = ""

    companion object {
        fun newInstance(context: Context, id: Long) {
            val intent = Intent(context, QingDanDetalisActivity::class.java)
            intent.putExtra("id", id)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityQingdanDetalisBinding.inflate(layoutInflater).bindView(this)
        id = intent.getLongExtra("id", 0)
        initView()
        fetchData()
    }


    private fun initView() {
        mBinding.ivBack.click {
            finish()
        }

        mBinding.ivSure.click {
//            if (newImg.isEmpty()) {
//                ToastUtils.showShort("请选择图片")
//                return@click
//            }

            if (mBinding.tvTime.text.isNullOrEmpty()) {
                ToastUtils.showShort("请选择时间")
                return@click
            }


            if (mBinding.etContent.text.isNullOrEmpty()) {
                ToastUtils.showShort("请写一句关于这段回忆的话吧")
                return@click
            }

            commit()
        }

        mBinding.ivQingDan.click {
            selectPicture()
        }

        mBinding.tvTime.click {
            TimePickerUtil.showDatePick(this)
                .onComplete { year, month, day ->
                    mBinding.tvTime.text = "$year-$month-$day"
                }
        }

    }

    private fun fetchData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val model = DataBaseManager.getInstance().getDataBase()?.qingdanDao()?.getQingDan(id)

            withContext(Dispatchers.Main) {

                mBinding.tvTitle.setText(model?.title)
                mBinding.tvTime.text = model?.time
                mBinding.etContent.setText(model?.content)

                if (model?.type != "1") {
                    newImg = model?.img ?: ""

                    Glide.with(this@QingDanDetalisActivity)
                        .load(File(model?.img))
                        .dontAnimate()
                        .skipMemoryCache(false)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(mBinding.ivQingDan)

                } else {
                    img = model.img
                    val resId = ImageIdUtils.getImageId(model.img)
                    mBinding.ivQingDan.setImageResource(resId)
                }
            }
        }
    }

    private fun commit() {
        val model = QingDanResult()
        model.id = id
        model.title = mBinding.tvTitle.text.toString()
        model.time = mBinding.tvTime.text.toString()
        model.content = mBinding.etContent.text.toString()
        if (newImg.isEmpty()) {
            model.img = img
            model.type = "1"
        } else {
            model.img = newImg
            model.type = "2"
        }

        lifecycleScope.launch(Dispatchers.IO) {
            DataBaseManager.getInstance().getDataBase()?.qingdanDao()?.updateQingDan(model)

            withContext(Dispatchers.Main) {
                finish()
            }
        }
    }

    private fun selectPicture() {
        PictureSelector.create(this)
            .openGallery(SelectMimeType.ofImage())
            .setMaxSelectNum(1)
            .setImageEngine(GlideEngine.createGlideEngine())
            .forResult(object : OnResultCallbackListener<LocalMedia?> {
                override fun onResult(result: ArrayList<LocalMedia?>?) {
                    if (result.isNullOrEmpty()) return
                    val media = result.elementAtOrNull(0) ?: return

                    val path = media.realPath
                    val format = com.blankj.utilcode.util.FileUtils.getFileExtension(path)
                    val originalCacheFilePath =
                        FileUtils.getFileCacheDirectory() + System.currentTimeMillis() + "." + format
                    val isCopy =
                        com.blankj.utilcode.util.FileUtils.copy(path, originalCacheFilePath)

                    if (isCopy) {
                        newImg = originalCacheFilePath
                        Glide.with(this@QingDanDetalisActivity)
                            .load(File(newImg))
                            .dontAnimate()
                            .skipMemoryCache(false)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(mBinding.ivQingDan)
                    }
                }

                override fun onCancel() {

                }
            })
    }

}