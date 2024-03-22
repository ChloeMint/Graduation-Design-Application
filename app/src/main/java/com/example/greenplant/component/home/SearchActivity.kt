package com.example.greenplant.component.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivitySearchBinding
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

class SearchActivity : BaseViewModelActivity<ActivitySearchBinding>() {
    override fun initViews() {
        super.initViews()
        QMUIStatusBarHelper.translucent(this)
    }
}