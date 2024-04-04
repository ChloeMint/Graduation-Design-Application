package com.example.greenplant.component.communicate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivityPublishDongtaiBinding
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

class PublishDongTaiActivity : BaseViewModelActivity<ActivityPublishDongtaiBinding>() {

    override fun initViews() {
        super.initViews()
        QMUIStatusBarHelper.translucent(this)
        setCenterToolBar(binding.toolbar, "发布动态")
    }
}