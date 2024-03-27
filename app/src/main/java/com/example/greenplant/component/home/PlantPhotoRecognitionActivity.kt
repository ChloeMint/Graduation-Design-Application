package com.example.greenplant.component.home


import android.Manifest
import android.os.Build
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivityPlantPhotoRecognitionBinding
import com.example.greenplant.util.SuperUiUtil
import com.permissionx.guolindev.PermissionX


class PlantPhotoRecognitionActivity : BaseViewModelActivity<ActivityPlantPhotoRecognitionBinding>() {

    override fun initViews() {
        super.initViews()

    }

    override fun initListener() {
        super.initListener()
        binding.back.setOnClickListener {
            finish()
        }
    }

//    private fun requestPermission(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
//            PermissionX.init(this).permissions(
//                Manifest.permission.CAMERA,
//                Manifest.permission.READ_PHONE_STATE,
////                 手机存储权限在不同版本
//                Manifest.permission.READ_MEDIA_AUDIO,
//                Manifest.permission.READ_MEDIA_IMAGES,
//            )
//        }else{
//            PermissionX.init(this).permissions(
//                Manifest.permission.CAMERA,
//                Manifest.permission.READ_PHONE_STATE,
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//            )
//        }.request { allGranted, _, _ ->
//            if (allGranted){
//
//            }else{
//                SuperUiUtil.newToast(this, "该功能只能授权权限后方可使用")
//                finish()
//            }
//        }
//    }

}