package com.example.greenplant.component.welcome

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import com.example.greenplant.MainActivity
import com.example.greenplant.R
import com.example.greenplant.activity.BaseLogicActivity
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.component.guide.GuideActivity
import com.example.greenplant.databinding.ActivityWelcomeBinding
import com.example.greenplant.util.DefaultPreferencesUtil
import com.example.greenplant.util.SuperDateUtil
import com.example.greenplant.util.SuperUiUtil
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

class WelcomeActivity : BaseViewModelActivity<ActivityWelcomeBinding>() {
    override fun initViews() {
        super.initViews()
        QMUIStatusBarHelper.translucent(this)
        if (SuperUiUtil.isDark(this)){
            QMUIStatusBarHelper.setStatusBarDarkMode(this)
        }else{
            QMUIStatusBarHelper.setStatusBarLightMode(this)
        }
    }

    override fun initDatum() {
        super.initDatum()
        binding.copyright.text = getString(R.string.version, SuperDateUtil.getCurrentYear())

        if (DefaultPreferencesUtil.isServiceAgree()){
            binding.root.postDelayed(
                { prepareToNext() },1000
            )
        }else{
            TermDialogFragment.show(supportFragmentManager
            ) {
                DefaultPreferencesUtil.serviceAgree()
                prepareToNext()
            }
        }
    }

    private fun prepareToNext(){
        if (DefaultPreferencesUtil.getToken() != ""){
            isTokenExpired()
        }else{
            startActivityAfterFinishIt(GuideActivity::class.java)
        }
    }
}