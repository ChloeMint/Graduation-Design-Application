package com.example.greenplant.component.home


import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivityPlantDockerBinding
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

class PlantDockerActivity : BaseViewModelActivity<ActivityPlantDockerBinding>() {
    override fun initViews() {
        super.initViews()
        QMUIStatusBarHelper.translucent(this)
        setCenterToolBar(binding.customToolbar, "智能医生")
    }
}