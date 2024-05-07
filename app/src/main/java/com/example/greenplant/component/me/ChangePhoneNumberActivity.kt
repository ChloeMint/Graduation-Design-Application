package com.example.greenplant.component.me


import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivityChangePhoneNumberBinding
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

class ChangePhoneNumberActivity : BaseViewModelActivity<ActivityChangePhoneNumberBinding>() {

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
}