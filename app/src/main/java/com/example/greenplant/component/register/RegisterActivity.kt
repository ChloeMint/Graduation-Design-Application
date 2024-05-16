package com.example.greenplant.component.register

import android.content.Intent
import android.os.Handler
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.greenplant.R
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.component.login.LoginActivity
import com.example.greenplant.component.welcome.WelcomeActivity
import com.example.greenplant.databinding.ActivityRegisterBinding
import com.example.greenplant.entities.User
import com.example.greenplant.util.ActivityCollector
import com.example.greenplant.util.DefaultPreferencesUtil
import com.example.greenplant.util.SuperUiUtil
import com.example.greenplant.viewModel.RegisterViewModel
import com.example.greenplant.viewModel.SendMessageViewModel
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

class RegisterActivity : BaseViewModelActivity<ActivityRegisterBinding>() {
    private val sendMessageViewModel by lazy {
        ViewModelProvider(this)[SendMessageViewModel::class.java]
    }
    private val registerViewModel by lazy {
        ViewModelProvider(this)[RegisterViewModel::class.java]
    }

    var handler: Handler? = null
    private var countDownRunnable: Runnable? = null
    override fun initViews() {
        super.initViews()
        setToolBar(binding.toolBar, "Register")
        QMUIStatusBarHelper.translucent(this)
        if (DefaultPreferencesUtil.getLogoutStatus()){
            binding.toolBar.navigationIcon = null
        }
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
            val phone = binding.phone.text.toString()
            val code = binding.code.text.toString()
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()

            if (phone == "" || code == "" || username == "" || password == ""){
                SuperUiUtil.newToast(this, "有信息未填写")
            }else{
                val user = User(phone = phone, code = code, username = username, password = password)
                registerViewModel.setUserLiveData(user)
            }

        }


        sendMessageViewModel.sendMessageResponseLiveData.observe(this, Observer { result ->
            SuperUiUtil.newToast(this, "${result.getOrNull()}")
        })
        registerViewModel.registerResponseLiveData.observe(this, Observer {
            SuperUiUtil.newToast(this, "${it.getOrNull()}")
            if (it.getOrNull() == "注册用户成功"){
                finish()
                if (DefaultPreferencesUtil.getLogoutStatus()){
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownRunnable?.let { handler?.removeCallbacks(it) }
        countDownRunnable = null
        handler = null
    }
}