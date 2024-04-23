package com.example.greenplant.component.me

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivityCreateNoteBinding
import com.example.greenplant.util.SuperUiUtil
import com.example.greenplant.viewModel.CreateNoteViewModel
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

class CreateNoteActivity : BaseViewModelActivity<ActivityCreateNoteBinding>() {
    private val createNoteViewModel by lazy {
        ViewModelProvider(this)[CreateNoteViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNoteViewModel.createNoteLiveData.observe(this, Observer {
            val result = it.getOrNull()
            if (result != null){
                SuperUiUtil.newToast(this, result.msg)
                if (result.code == 200){
                    setResult(RESULT_OK)
                    finish()
                }
            }
        })
    }

    override fun initViews() {
        super.initViews()
        QMUIStatusBarHelper.translucent(this)

        binding.titleLayout.apply {
            setIcon(true)
            setTitleCenter(true)
            setTitle("创建植物笔记清单")
            setRightButton("发表", false)
        }
    }

    override fun initListener() {
        super.initListener()
        binding.titleLayout.binding.apply {
            back.setOnClickListener {
                finish()
            }

            rightButton.setOnClickListener {
                val title = binding.title.text.toString()
                val content = binding.content.text.toString()

                if (title == "" || content == ""){
                    SuperUiUtil.newToast(this@CreateNoteActivity, "标题或内容为空")
                }else{
                    createNoteViewModel.setNoteLiveData(title, content)
                }
            }
        }
    }

}