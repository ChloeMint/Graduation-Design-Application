package com.example.greenplant.component.me

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivityChangeUserIntroduceBinding
import com.example.greenplant.util.SuperUiUtil
import com.example.greenplant.viewModel.ChangeUserIntroduceViewModel
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

class ChangeUserIntroduceActivity : BaseViewModelActivity<ActivityChangeUserIntroduceBinding>() {
    lateinit var introduce :String
    private val changeUserIntroduceViewModel by lazy {
        ViewModelProvider(this)[ChangeUserIntroduceViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        introduce = intent.getStringExtra("introduction").toString()
        setResult(RESULT_OK)

        changeUserIntroduceViewModel.userIntroduceLiveData.observe(this, Observer {
            val result = it.getOrNull()
            if (result != null){
                SuperUiUtil.newToast(this, result.msg)
                if (result.code == 200){
                    finish()
                }
            }
        })
    }

    override fun initViews() {
        super.initViews()
        QMUIStatusBarHelper.translucent(this)
        binding.titleLayout.apply {
            setRightButton("完成", false)
            setTitle("修改简介")
            setIcon(true)
            setTitleCenter(true)
        }
        binding.introduce.doOnTextChanged { text, _, _, _ ->
            binding.textLength.text = text?.length.toString()
        }

        binding.introduce.setText(introduce)
    }

    override fun initListener() {
        super.initListener()
        binding.titleLayout.binding.back.setOnClickListener {
            finish()
        }

        binding.titleLayout.binding.rightButton.setOnClickListener {
            if (binding.introduce.text.toString() == ""){
                SuperUiUtil.newToast(this, "您还未设置简介")
            }else{
                changeUserIntroduceViewModel.setIntroduceLiveData(binding.introduce.text.toString())
            }
        }
    }

    companion object{
        const val TAG = "ChangeUserIntroduceActivity"
        fun startChangeUserIntroduceActivity(context: Context, introduction:String): Intent{
            val intent = Intent(context, ChangeUserIntroduceActivity::class.java)
            intent.putExtra("introduction", introduction)
            return intent
        }
    }
}