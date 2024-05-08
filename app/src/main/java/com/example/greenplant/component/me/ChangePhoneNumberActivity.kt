package com.example.greenplant.component.me


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.greenplant.R
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivityChangePhoneNumberBinding
import com.example.greenplant.util.SuperUiUtil
import com.example.greenplant.viewModel.ChangeUserPhoneNumberViewModel
import com.example.greenplant.viewModel.SendMessageViewModel
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

class ChangePhoneNumberActivity : BaseViewModelActivity<ActivityChangePhoneNumberBinding>() {
    var handler: Handler? = null
    private var countDownRunnable: Runnable? = null
    private val sendMessageViewModel by lazy {
        ViewModelProvider(this)[SendMessageViewModel::class.java]
    }
    private val changeUserPhoneNumberViewModel by lazy {
        ViewModelProvider(this)[ChangeUserPhoneNumberViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResult(RESULT_OK)

        sendMessageViewModel.sendMessageResponseLiveData.observe(this, Observer {
            val result = it.getOrNull()
            if (result != null){
                SuperUiUtil.newToast(this, result)
            }
        })

        changeUserPhoneNumberViewModel.changeUserPhoneNumberLiveData.observe(this, Observer {
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
            setTitle("修改手机号")
            setTitleCenter(true)
            setIcon(true)
            setRightButton("", true)
        }

    }

    @SuppressLint("ResourceAsColor")
    override fun initListener() {
        super.initListener()
        binding.titleLayout.binding.back.setOnClickListener {
            finish()
        }
        binding.phoneNumber.doOnTextChanged { text, _, _, _ ->
            if (text?.length == 11){
                binding.getCodeButton.setTextColor(Color.BLUE)
                binding.getCodeButton.isEnabled = true
            }else{
                binding.getCodeButton.setTextColor(Color.LTGRAY)
                binding.getCodeButton.isEnabled = false
            }
        }
        binding.getCodeButton.setOnClickListener {
            // 调用接口代码
            sendMessageViewModel.setPhoneLiveData(binding.phoneNumber.text.toString())
            // 下面这是定时器
            var countDown = 5
            it.isEnabled = false

            handler = Handler()

            countDownRunnable = object : Runnable{
                override fun run() {
                    if (countDown > 0){
                        binding.getCodeButton.text = "$countDown"
                        binding.getCodeButton.setTextColor(Color.LTGRAY)
                        countDown--
                        handler?.postDelayed(this, 1000)
                    }else{
                        it.isEnabled = true
                        binding.getCodeButton.text = getString(R.string.get_code)
                        binding.getCodeButton.setTextColor(Color.BLUE)
                        if (binding.phoneNumber.length() != 11){
                            binding.getCodeButton.setTextColor(Color.LTGRAY)
                            it.isEnabled = false
                        }
                        handler?.removeCallbacks(this)
                    }
                }
            }
            handler?.post(countDownRunnable!!)
        }

        binding.confirm.setOnClickListener {
            if (binding.phoneNumber.text.toString() == ""){
                SuperUiUtil.newToast(this, "手机号为空")
            }else if (binding.phoneNumber.text.toString().length != 11){
                SuperUiUtil.newToast(this, "手机号格式错误")
            }else if (binding.code.text.toString() == ""){
                SuperUiUtil.newToast(this, "验证码为空")
            }else{
                changeUserPhoneNumberViewModel.setPhoneAndCode(binding.phoneNumber.text.toString(), binding.code.text.toString())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownRunnable?.let { handler?.removeCallbacks(it) }
        countDownRunnable = null
        handler = null
    }
}