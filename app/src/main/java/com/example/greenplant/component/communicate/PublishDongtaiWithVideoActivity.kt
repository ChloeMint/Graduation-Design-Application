package com.example.greenplant.component.communicate

import android.Manifest
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivityPublishDongtaiWithVideoBinding
import com.example.greenplant.entities.DongtaiWithVideo
import com.example.greenplant.util.SuperUiUtil
import com.example.greenplant.viewModel.PublishDongtaiWithVideoViewModel
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.permissionx.guolindev.PermissionX
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import java.io.File


class PublishDongtaiWithVideoActivity : BaseViewModelActivity<ActivityPublishDongtaiWithVideoBinding>() {
    private val videoResult = mutableListOf<LocalMedia>()
    private val publishDongtaiWithVideoViewModel by lazy {
        ViewModelProvider(this)[PublishDongtaiWithVideoViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        publishDongtaiWithVideoViewModel.publishDongtaiWithVideoLiveData.observe(this, Observer {
            val msg = it.getOrNull()
            if (msg != null){
                SuperUiUtil.newToast(this,msg)
            }
            if (msg == "发布动态成功"){
                finish()
            }
        })
    }
    override fun initViews() {
        super.initViews()
        QMUIStatusBarHelper.translucent(this)
        setCenterToolBar(binding.toolbar, "发布视频动态")
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
            val content = binding.content.text.toString()
            if (videoResult.isEmpty() && content == ""){
                SuperUiUtil.newToast(this,"您没有输入任何内容")
            }else{
                publishDongtaiWithVideoViewModel.setPublishDongtaiWithVideoLiveData(DongtaiWithVideo(content,videoResult[0]))
            }
        }

        binding.cancel.setOnClickListener {
            videoResult.removeAt(0)
            binding.videoBox.visibility = View.GONE
            binding.addImage.visibility = View.VISIBLE
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
            .openGallery(SelectMimeType.ofVideo())
            .setImageEngine(GlideEngine.createGlideEngine())
            .setMaxSelectNum(1)
            .isPreviewVideo(true)
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: ArrayList<LocalMedia>) {
                    if (result.isNotEmpty()){
                        videoResult.addAll(result)
                        binding.addImage.visibility = View.GONE
                        binding.video.setVideoURI(Uri.parse(result[0].path))
                        binding.video.seekTo(1)
                        binding.videoBox.visibility = View.VISIBLE
                    }
                }
                override fun onCancel() {
                    SuperUiUtil.newToast(this@PublishDongtaiWithVideoActivity,"取消选择")
                }
            })

    }

}