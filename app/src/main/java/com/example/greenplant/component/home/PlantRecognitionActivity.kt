package com.example.greenplant.component.home

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivityPlantRecognitionBinding
import com.example.greenplant.util.SuperUiUtil
import com.permissionx.guolindev.PermissionX
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

class PlantRecognitionActivity : BaseViewModelActivity<ActivityPlantRecognitionBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun initViews() {
        super.initViews()
        QMUIStatusBarHelper.translucent(this)
        requestPermission()
    }

    override fun initListener() {
        super.initListener()
        binding.back.setOnClickListener {
            finish()
        }
    }


    private fun requestPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            PermissionX.init(this).permissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
//                 手机存储权限在不同版本
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_IMAGES,
            )
        }else{
            PermissionX.init(this).permissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }.request { allGranted, _, _ ->
            if (allGranted){
                launchCamera()
            }else{
                SuperUiUtil.newToast(this, "该功能只能授权权限后方可使用")
                finish()
            }
        }
    }

    private val captureImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // 处理返回的 Intent 数据
            val extras = result.data?.extras
            val imageBitmap = extras?.get("data") as Bitmap?
            binding.takenPhoto.setImageBitmap(imageBitmap)
        }
    }

    // 启动相机 Intent
    private fun launchCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(packageManager) != null) {
            captureImageLauncher.launch(cameraIntent)
        }
    }



}