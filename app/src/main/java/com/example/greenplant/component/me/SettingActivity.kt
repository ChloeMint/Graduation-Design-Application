package com.example.greenplant.component.me

import android.Manifest
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.greenplant.R
import com.example.greenplant.ServiceCreator
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.component.communicate.GlideEngine
import com.example.greenplant.component.communicate.OnImagesRemovedListener
import com.example.greenplant.component.communicate.PublishImageAdapter
import com.example.greenplant.databinding.ActivitySettingBinding
import com.example.greenplant.util.SuperUiUtil
import com.example.greenplant.viewModel.ChangeUserAvatarViewModel
import com.example.greenplant.viewModel.UserInfoViewModel
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.permissionx.guolindev.PermissionX
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

class SettingActivity : BaseViewModelActivity<ActivitySettingBinding>() {
    private val userInfoViewModel by lazy {
        ViewModelProvider(this)[UserInfoViewModel::class.java]
    }

    private val changeUserInfoViewModel by lazy {
        ViewModelProvider(this)[ChangeUserAvatarViewModel::class.java]
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){
            userInfoViewModel.setIsGettingUserInfo()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userInfoViewModel.userInfoLiveData.observe(this, Observer {
            val result = it.getOrNull()
            if (result != null){

                Glide.with(binding.avatar).load(ServiceCreator.BASE_URL+result.avatar).apply {
                    // 下载error时显示的图片
                    error(R.drawable.load_failed)
                }.into(binding.avatar)

                binding.name.text = result.username
                binding.introduce.text = result.introduction
                binding.phoneNumber.text = result.phone
            }
        })

        changeUserInfoViewModel.changeUserAvatarLiveData.observe(this, Observer {
            val result = it.getOrNull()
            if (result != null){
                SuperUiUtil.newToast(this, result.msg)
            }
        })
    }


    override fun initViews() {
        super.initViews()
        QMUIStatusBarHelper.translucent(this)

        binding.titleLayout.apply {
            setTitle("设置")
            setRightButton("", true)
            setIcon(true)
        }
    }

    override fun initListener() {
        super.initListener()
        binding.titleLayout.binding.back.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }

        binding.changeAvatarBox.setOnClickListener {
            requestPermission()
        }

        binding.nameBox.setOnClickListener {
            val intent = ChangeUserNameActivity.startChangeUsernameActivity(this, binding.name.text.toString())
            launcher.launch(intent)
        }

        binding.introduceBox.setOnClickListener {
            val intent = ChangeUserIntroduceActivity.startChangeUserIntroduceActivity(this, binding.introduce.text.toString())
            launcher.launch(intent)
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
                        val uri = Uri.parse(result[0].path)
                        binding.avatar.setImageURI(uri)
                        changeUserInfoViewModel.setImageLocalMedia(result[0])
                    }
                }
                override fun onCancel() {
                    SuperUiUtil.newToast(this@SettingActivity,"取消选择")
                }
            })

    }
    override fun initDatum() {
        super.initDatum()
        userInfoViewModel.setIsGettingUserInfo()
    }

}