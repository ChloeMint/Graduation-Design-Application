package com.example.greenplant.activity

import android.content.Intent
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.greenplant.MainActivity
import com.example.greenplant.R
import com.example.greenplant.component.login.LoginActivity
import com.example.greenplant.util.DefaultPreferencesUtil
import com.example.greenplant.util.SuperUiUtil
import com.example.greenplant.viewModel.UserInfoViewModel

open class BaseLogicActivity : BaseCommonActivity() {
    private val userInfoViewModel by lazy {
        ViewModelProvider(this)[UserInfoViewModel::class.java]
    }
    fun setToolBar(toolbar: Toolbar, title:String){
        setSupportActionBar(toolbar)
        supportActionBar!!.title = title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationIcon(R.drawable.back)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    fun isTokenExpired() {
        userInfoViewModel.setIsGettingUserInfo()
        userInfoViewModel.userInfoLiveData.observe(this, Observer {
            val user = it.getOrNull()
            if (user?.username == null){
                SuperUiUtil.newToast(this, "登录信息已过期，请重新登录")
                startActivityAfterFinishIt(LoginActivity::class.java)
            }else{
                startActivityAfterFinishIt(MainActivity::class.java)
            }
        })
    }
}