package com.example.greenplant.component.welcome

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import com.example.greenplant.R
import com.example.greenplant.activity.BaseLogicActivity
import com.example.greenplant.databinding.ActivityWelcomeBinding
import com.example.greenplant.util.DefaultPreferencesUtil
import com.example.greenplant.util.SuperDateUtil
import com.example.greenplant.util.SuperUiUtil
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

class WelcomeActivity : BaseLogicActivity() {
    lateinit var binding:ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

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

        }else{
            TermDialogFragment.show(supportFragmentManager
            ) {
                DefaultPreferencesUtil.serviceAgree()
            }
        }


    }
}