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
import com.yx.play.databinding.ActivityDetalisBinding
import com.yx.play.db.DataBaseManager
import com.yx.play.db.result.KaPianResult
import com.yx.play.ext.*
import com.yx.play.util.FileUtils
import com.yx.play.util.GlideEngine
import com.yx.play.util.ImageIdUtils
import com.yx.play.util.TimePickerUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


class DetalisActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityDetalisBinding
    private var id: Long = 0L

    private var newImg = ""
    private var img = ""

    companion object {
        fun newInstance(context: Context, id: Long) {
            val intent = Intent(context, DetalisActivity::class.java)
            intent.putExtra("id", id)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityDetalisBinding.inflate(layoutInflater).bindView(this)
        id = intent.getLongExtra("id", 0)
        initView()
        fetchData()
    }


    private fun initView() {
        mBinding.ivBack.click {
            finish()
        }

        mBinding.ivSure.click {
            if (mBinding.etTitle.text.isNullOrEmpty()) {
                ToastUtils.showShort("请输入标题")
                return@click
            }

            if (mBinding.etAddress.text.isNullOrEmpty()) {
                ToastUtils.showShort("请输入地址")
                return@click
            }


            if (mBinding.etContent.text.isNullOrEmpty()) {
                ToastUtils.showShort("请输入内容")
                return@click
            }

            commit()
        }

        mBinding.ivDiary.click {
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
            val model = DataBaseManager.getInstance().getDataBase()?.kapianDao()?.getKaPian(id)

            withContext(Dispatchers.Main) {
                img = model?.img ?: ""
                mBinding.etTitle.setText(model?.title)
                mBinding.etAddress.setText(model?.address)
                mBinding.tvTime.text = model?.time
                mBinding.etContent.setText(model?.content)

                if (model?.type != "1") {

                    Glide.with(this@DetalisActivity)
                        .load(File(model?.img))
                        .dontAnimate()
                        .skipMemoryCache(false)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(mBinding.ivDiary)

                } else {
                    val resId = ImageIdUtils.getImageId(model.img)
                    mBinding.ivDiary.setImageResource(resId)
                }
            }
        }
    }

    private fun commit() {
        val model = KaPianResult()
        model.id = id
        model.title = mBinding.etTitle.text.toString()
        model.address = mBinding.etAddress.text.toString()
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
            DataBaseManager.getInstance().getDataBase()?.kapianDao()?.updateKaPian(model)

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
                        Glide.with(this@DetalisActivity)
                            .load(File(newImg))
                            .dontAnimate()
                            .skipMemoryCache(false)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(mBinding.ivDiary)
                    }
                }

                override fun onCancel() {

                }
            })
    }

}