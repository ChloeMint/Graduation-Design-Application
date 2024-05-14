package com.example.greenplant.component.home


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.greenplant.GreenPlantApplication
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivityPlantPhotoRecognitionBinding
import com.example.greenplant.util.Base64Util
import com.example.greenplant.util.FileUtil
import com.example.greenplant.util.SuperUiUtil
import com.example.greenplant.viewModel.BaiduRecognitionViewModel
import com.permissionx.guolindev.PermissionX
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import java.io.File
import java.io.FileOutputStream


class PlantPhotoRecognitionActivity :
    BaseViewModelActivity<ActivityPlantPhotoRecognitionBinding>() {
    private val fromAlbum = 2
    private val baiduRecognitionViewModel by lazy {
        ViewModelProvider(this)[BaiduRecognitionViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baiduRecognitionViewModel.baiduPlantRecognitionResponse.observe(this, Observer {
            val baiduPlantRecognitionResponse = it.getOrNull()
            if (baiduPlantRecognitionResponse != null){
                val result = baiduPlantRecognitionResponse[0]
                binding.plantName.text = result.name
                binding.truth.text = "可信度: ${result.score * 100} %"
                binding.progress.visibility = View.GONE
            }
        })
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

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            PermissionX.init(this).permissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
//                 手机存储权限在不同版本
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_IMAGES,
            )
        } else {
            PermissionX.init(this).permissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }.request { allGranted, _, _ ->
            if (allGranted) {
                launchAlbum()
            } else {
                SuperUiUtil.newToast(this, "该功能只能授权权限后方可使用")
                finish()
            }
        }
    }

    private fun launchAlbum() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        // 指定只显示图片
        intent.type = "image/*"
        startActivityForResult(intent, fromAlbum)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            fromAlbum -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    data.data?.let { uri ->
                        // 将选择的图片显示
                        val bitmap = getBitmapFromUri(uri)
                        binding.takenPhoto.setImageBitmap(bitmap)

                        val directory = GreenPlantApplication.context.filesDir
                        // 创建文件名
                        val fileName = "my_image.jpg"
                        // 创建完整的文件路径
                        val filePath = File(directory, fileName).absolutePath
                        Log.d("TAG", "$filePath")
                        // 创建一个文件输出流来写入文件
                        val fos: FileOutputStream? = try {
                            FileOutputStream(filePath)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            null
                        }

                        // 如果文件输出流创建成功，则将Bitmap保存为JPG文件
                        if (fos != null) {
                            try {
                                // 压缩Bitmap为JPG格式并写入文件，这里使用了90的压缩质量
                                bitmap?.compress(Bitmap.CompressFormat.JPEG, 90, fos)
                                fos.flush()
                                fos.close()
                                // 文件保存成功
                            } catch (e: Exception) {
                                e.printStackTrace()
                                // 处理文件保存失败的情况
                            }
                        } else {
                            // 处理文件输出流创建失败的情况
                        }


                        val imgData = FileUtil.readFileByBytes(filePath)
                        val imgStr = Base64Util.encode(imgData)
                        baiduRecognitionViewModel.setBase64Image(imgStr)
                    }
                }
            }
        }
    }

    private fun getBitmapFromUri(uri: Uri) = contentResolver
        .openFileDescriptor(uri, "r")?.use {
            BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
        }

}