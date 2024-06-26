package com.example.greenplant.component.communicate

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivityPublishDongtaiBinding
import com.example.greenplant.entities.DongtaiWithImage
import com.example.greenplant.util.SuperUiUtil
import com.example.greenplant.viewModel.PublishDongtaiImageViewModel
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.permissionx.guolindev.PermissionX
import com.qmuiteam.qmui.util.QMUIStatusBarHelper


class PublishDongTaiActivity : BaseViewModelActivity<ActivityPublishDongtaiBinding>() {
    private val imageResult = mutableListOf<LocalMedia>()
    private val publishDongtaiImageViewModel by lazy {
        ViewModelProvider(this)[PublishDongtaiImageViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        publishDongtaiImageViewModel.publishDongtaiImageLiveData.observe(this, Observer {
            val msg = it.getOrNull()
            if (msg != ""){
                SuperUiUtil.newToast(this,"$msg")
                if (msg == "发布动态成功"){
                    finish()
                }
            }
        })
    }
    override fun initViews() {
        super.initViews()
        QMUIStatusBarHelper.translucent(this)
        setCenterToolBar(binding.toolbar, "发布图片动态")
    }

    override fun initListener() {
        super.initListener()
        binding.content.doOnTextChanged { text, _, _, _ ->
            binding.textCount.text = "${text?.length}" + "/100"
        }

        binding.addImage.setOnClickListener {
            requestPermission()
        }

        binding.publish.setOnClickListener {
            val text = binding.content.text.toString()
            if (text!="" || imageResult.isNotEmpty()){
                publishDongtaiImageViewModel.setDongtaiImageLiveData(DongtaiWithImage(text,imageResult))
            }else{
                SuperUiUtil.newToast(this,"您没有输入任何内容")
            }
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            PermissionX.init(this).permissions(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_IMAGES,
            )
        } else {
            PermissionX.init(this).permissions(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }.request { allGranted, _, _ ->
            if (allGranted) {
                launchAlbum()
            } else {
                SuperUiUtil.newToast(this, "该功能只能授权权限后方可使用")
            }
        }
    }
    private fun launchAlbum() {
        PictureSelector.create(this)
            .openGallery(SelectMimeType.ofImage())
            .setImageEngine(GlideEngine.createGlideEngine())
            .setMaxSelectNum(9)
            .isPreviewImage(true)
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: ArrayList<LocalMedia>) {
                    if (result.isNotEmpty()){
                        imageResult.addAll(result)
                        binding.addImage.visibility = View.GONE
                        binding.imageRecycleView.visibility = View.VISIBLE
                        binding.imageRecycleView.layoutManager = GridLayoutManager(this@PublishDongTaiActivity,4, GridLayoutManager.VERTICAL, false)
                        binding.imageRecycleView.adapter = PublishImageAdapter(this@PublishDongTaiActivity,
                            imageResult as ArrayList<LocalMedia>, object : OnImagesRemovedListener{
                            override fun onImagesRemoved() {
                                binding.addImage.visibility = View.VISIBLE
                                binding.imageRecycleView.visibility = View.GONE
                            }
                        })
                    }
                }
                override fun onCancel() {
                    SuperUiUtil.newToast(this@PublishDongTaiActivity,"取消选择")
                }
            })

    }

}