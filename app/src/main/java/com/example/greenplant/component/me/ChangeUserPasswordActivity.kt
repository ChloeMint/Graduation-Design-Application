package com.example.greenplant.component.me

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivityChangeUserPasswordBinding
import com.example.greenplant.util.SuperUiUtil
import com.example.greenplant.viewModel.ChangeUserPasswordViewModel
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

class ChangeUserPasswordActivity : BaseViewModelActivity<ActivityChangeUserPasswordBinding>() {
    private val changeUserPasswordViewModel by lazy {
        ViewModelProvider(this)[ChangeUserPasswordViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResult(RESULT_OK)

        changeUserPasswordViewModel.changeUserPasswordLiveData.observe(this, Observer {
            val result = it.getOrNull()
            if (result != null){
                SuperUiUtil.newToast(this, result.msg)
                if (result.code == 200){
                    finish()
                }
            }
        })
    }

    override fun initViews() {
        super.initViews()
        QMUIStatusBarHelper.translucent(this)
        binding.titleLayout.apply {
            setIcon(true)
            setTitle("修改密码")
            setTitleCenter(true)
            setRightButton("", true)
        }
    }

    override fun initListener() {
        super.initListener()
        binding.titleLayout.binding.back.setOnClickListener {
            finish()
        }

        binding.change.setOnClickListener {
            val newPassword = binding.newPassword.text.toString()
            val confirmPassword = binding.confirmPassword.text.toString()
            if (newPassword == "" || confirmPassword == ""){
                SuperUiUtil.newToast(this, "有参数为空")
            }else if (newPassword.length<10 || newPassword.length>16){
                SuperUiUtil.newToast(this, "新密码长度必须要为10~16位")
            }else if (newPassword != confirmPassword){
                SuperUiUtil.newToast(this, "前后密码不一致")
            }else{
                changeUserPasswordViewModel.setPasswordLiveData(newPassword)
            }
        }
    }


}