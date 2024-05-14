package com.example.greenplant.component.me

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.get
import androidx.core.widget.doOnTextChanged
import com.example.greenplant.R
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivitySendApplicationBugBinding
import com.example.greenplant.util.SuperUiUtil
import com.google.android.material.chip.Chip
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

class SendApplicationBugActivity : BaseViewModelActivity<ActivitySendApplicationBugBinding>() {
    private val selectList = mutableListOf("功能反馈", "界面设计", "性能问题", "安全问题", "用户体验", "建议与意见", "其他问题")
    private var nowSelect = 10
    override fun initViews() {
        super.initViews()
        QMUIStatusBarHelper.translucent(this)
        binding.titleLayout.apply {
            setRightButton("", true)
            setTitleCenter(true)
            setIcon(true)
            setTitle("用户反馈")
        }
        resetStyle()
    }

    @SuppressLint("ResourceAsColor")
    override fun initListener() {
        super.initListener()
        binding.titleLayout.binding.back.setOnClickListener {
            finish()
        }

        binding.chip1.setOnClickListener {
            resetStyle()
            setSelectStyle(binding.chip1)
            nowSelect = 0
        }

        binding.chip2.setOnClickListener {
            resetStyle()
            setSelectStyle(binding.chip2)
            nowSelect = 1
        }

        binding.chip3.setOnClickListener {
            resetStyle()
            setSelectStyle(binding.chip3)
            nowSelect = 2
        }

        binding.chip4.setOnClickListener {
            resetStyle()
            setSelectStyle(binding.chip4)
            nowSelect = 3
        }

        binding.chip5.setOnClickListener {
            resetStyle()
            setSelectStyle(binding.chip5)
            nowSelect = 4
        }

        binding.chip6.setOnClickListener {
            resetStyle()
            setSelectStyle(binding.chip6)
            nowSelect = 5
        }

        binding.chip7.setOnClickListener {
            resetStyle()
            setSelectStyle(binding.chip7)
            nowSelect = 6
        }

        binding.edit.doOnTextChanged { text, _, _, _ ->
            binding.textCount.text = text!!.length.toString()
        }

        binding.commit.setOnClickListener {
            if (nowSelect == 10){
                SuperUiUtil.newToast(this, "您还未选择任何标签")
            }else if (binding.edit.text.toString() == ""){
                SuperUiUtil.newToast(this, "您还未输入任何内容")
            }else{

            }
        }
    }

    private fun setSelectStyle(chip: Chip){
        chip.setChipBackgroundColorResource(R.color.meSettingValue)
        chip.setTextColor(Color.WHITE)
    }

    private fun resetStyle(){
        for (i in 0 until binding.chipGroup.childCount){
            val chip = binding.chipGroup.getChildAt(i) as Chip
            chip.setChipBackgroundColorResource(R.color.plant_item_background)
            chip.setTextColor(Color.DKGRAY)
        }
    }
}