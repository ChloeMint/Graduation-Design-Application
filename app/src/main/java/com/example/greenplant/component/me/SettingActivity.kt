package com.example.greenplant.component.me

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.greenplant.R
import com.example.greenplant.ServiceCreator
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivitySettingBinding
import com.example.greenplant.viewModel.UserInfoViewModel
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

class SettingActivity : BaseViewModelActivity<ActivitySettingBinding>() {
    private val userInfoViewModel by lazy {
        ViewModelProvider(this)[UserInfoViewModel::class.java]
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
    }


    override fun initViews() {
        super.initViews()
        QMUIStatusBarHelper.translucent(this)

        binding.titleLayout.apply {
            setTitle("设置")
            setRightButton("", true)
            setIcon(true)
        }

        binding.titleLayout.binding.back.setOnClickListener {
            finish()
        }
    }

    override fun initDatum() {
        super.initDatum()
        userInfoViewModel.setIsGettingUserInfo()
    }

}