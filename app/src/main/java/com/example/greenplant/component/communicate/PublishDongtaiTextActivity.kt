package com.example.greenplant.component.communicate

import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivityPublishDongtaiTextBinding
import com.example.greenplant.util.SuperUiUtil
import com.example.greenplant.viewModel.PublishDongtaiTextViewModel
import com.qmuiteam.qmui.util.QMUIStatusBarHelper


class PublishDongtaiTextActivity : BaseViewModelActivity<ActivityPublishDongtaiTextBinding>() {
    private val publishDongtaiTextViewModel by lazy {
        ViewModelProvider(this)[PublishDongtaiTextViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        publishDongtaiTextViewModel.publishDongtaiTextLiveData.observe(this, Observer {
            val msg = it.getOrNull()
            if (msg != null){
                SuperUiUtil.newToast(this,msg)
            }
            if (msg == "发布动态成功"){
                finish()
            }
        })
    }
    override fun initViews() {
        super.initViews()
        QMUIStatusBarHelper.translucent(this)
        setCenterToolBar(binding.toolbar, "发布文字动态")
    }

    override fun initListener() {
        super.initListener()
        binding.content.doOnTextChanged { text, _, _, _ ->
            binding.textCount.text = "${text?.length}" + "/100"
        }
        binding.publish.setOnClickListener {
            val text = binding.content.text.toString()
            if (text == ""){
                SuperUiUtil.newToast(this,"您没有输入任何内容")
            }else{
                publishDongtaiTextViewModel.setDongtaiLiveData(text)
            }
        }
    }

}