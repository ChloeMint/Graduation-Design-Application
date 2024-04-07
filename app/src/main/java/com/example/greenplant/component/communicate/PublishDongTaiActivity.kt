package com.example.greenplant.component.communicate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.doOnTextChanged
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivityPublishDongtaiBinding
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

class PublishDongTaiActivity : BaseViewModelActivity<ActivityPublishDongtaiBinding>() {

    override fun initViews() {
        super.initViews()
        QMUIStatusBarHelper.translucent(this)
        setCenterToolBar(binding.toolbar, "发布动态")
    }

    override fun initListener() {
        super.initListener()
        binding.content.doOnTextChanged { text, start, before, count ->
            binding.textCount.text = "${text?.length}" + "/100"
        }
    }
}