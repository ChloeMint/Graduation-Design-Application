package com.example.greenplant.component.me

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivityChangeUserNameBinding
import com.example.greenplant.util.SuperUiUtil
import com.example.greenplant.viewModel.ChangeUsernameViewModel
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

class ChangeUserNameActivity : BaseViewModelActivity<ActivityChangeUserNameBinding>() {
    lateinit var username:String
    private val changeUsernameViewModel by lazy {
        ViewModelProvider(this)[ChangeUsernameViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        username = intent.getStringExtra("username").toString()
        setResult(RESULT_OK)

        changeUsernameViewModel.changeUsernameResponseLiveData.observe(this, Observer {
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
            setTitle("修改名称")
            setTitleCenter(true)
            setRightButton("完成", false)
        }

    }

    override fun initDatum() {
        super.initDatum()
        binding.username.setText(username)
    }

    override fun initListener() {
        super.initListener()
        binding.titleLayout.binding.back.setOnClickListener {
            finish()
        }

        binding.titleLayout.binding.rightButton.setOnClickListener {
            when (val username = binding.username.text.toString()) {
                "" -> {
                    SuperUiUtil.newToast(this, "您没有进行任何输入")
                }
                this.username -> {
                    SuperUiUtil.newToast(this, "您没有进行任何更改")
                }
                else -> {
                    changeUsernameViewModel.setUsernameLiveData(username)
                }
            }
        }
    }

    companion object{
        const val TAG = "ChangeUserNameActivity"

        fun startChangeUsernameActivity(context:Context, name:String) : Intent{
            val intent = Intent(context, ChangeUserNameActivity::class.java)
            intent.putExtra("username", name)
            return intent
        }
    }
}