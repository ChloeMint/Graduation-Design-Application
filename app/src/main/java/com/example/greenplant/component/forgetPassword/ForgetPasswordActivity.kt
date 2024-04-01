package com.example.greenplant.component.forgetPassword

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.greenplant.R
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.component.login.LoginActivity
import com.example.greenplant.component.register.RegisterActivity
import com.example.greenplant.databinding.ActivityForgetPasswordBinding
import com.example.greenplant.entities.User
import com.example.greenplant.util.SuperUiUtil
import com.example.greenplant.viewModel.ForgetPasswordViewModel
import com.example.greenplant.viewModel.SendMessageViewModel

class ForgetPasswordActivity : BaseViewModelActivity<ActivityForgetPasswordBinding>() {
    private val sendMessageViewModel by lazy {
        ViewModelProvider(this)[SendMessageViewModel::class.java]
    }
    private val forgetPasswordViewModel by lazy {
        ViewModelProvider(this)[ForgetPasswordViewModel::class.java]
    }
    var handler: Handler ?= null
    private var countDownRunnable: Runnable ?= null
    override fun initViews() {
        super.initViews()
        setToolBar(binding.toolBar, "ForgetPassword")
    }

    override fun initListener() {
        super.initListener()


        binding.sendMessage.setOnClickListener {
            val phone = binding.phone.text.toString()
            if (phone == ""){
                SuperUiUtil.newToast(this, "手机号为空")

            }else if(phone.length != 11){
                SuperUiUtil.newToast(this, "手机号长度应为11位")
            }else{
                sendMessageViewModel.setPhoneLiveData(phone)
                var countDown = 5
                it.isEnabled = false

                handler = Handler()

                countDownRunnable = object : Runnable{
                    override fun run() {
                        if (countDown > 0){
                            binding.sendMessage.text = "$countDown"
                            countDown--
                            handler?.postDelayed(this, 1000)
                        }else{
                            it.isEnabled = true
                            binding.sendMessage.text = getString(R.string.get_code)
                            handler?.removeCallbacks(this)
                        }
                    }
                }
                handler?.post(countDownRunnable!!)
            }



        }
        binding.commit.setOnClickListener {
            val code = binding.code.text.toString()
            val password = binding.password.text.toString()
            val phone = binding.phone.text.toString()

            if (code == "" || password == "" || phone == ""){
                SuperUiUtil.newToast(this, "手机号，验证码，密码中有空值")
            }else{
                val user = User(code = code, password = password, phone = phone)
                forgetPasswordViewModel.setForgetPasswordLiveData(user)
            }
        }
        binding.backToLogin.setOnClickListener {
            startActivityAfterFinishIt(LoginActivity::class.java)
        }
        binding.toRegister.setOnClickListener {
            startActivityAfterFinishIt(RegisterActivity::class.java)
        }

        sendMessageViewModel.sendMessageResponseLiveData.observe(this, Observer { result ->
            SuperUiUtil.newToast(this, "${result.getOrNull()}")
        })

        forgetPasswordViewModel.forgetPasswordResLiveData.observe(this, Observer {
            SuperUiUtil.newToast(this, "${it.getOrNull()}")
            if (it.getOrNull() == "密码修改成功"){
                finish()
            }
        })
    }



    override fun onDestroy() {  // 清理资源，防止内存泄露
        super.onDestroy()
        handler?.removeCallbacks(countDownRunnable!!)
        countDownRunnable = null
        handler = null
    }

}