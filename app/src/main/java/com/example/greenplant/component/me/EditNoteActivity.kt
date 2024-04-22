package com.example.greenplant.component.me

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivityEditNoteBinding
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

class EditNoteActivity : BaseViewModelActivity<ActivityEditNoteBinding>() {
    lateinit var title: String
    lateinit var content: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        content = intent.getStringExtra("title").toString()
        title = intent.getStringExtra("content").toString()
    }

    override fun initDatum() {
        super.initDatum()
        binding.title.setText(title)
        binding.content.setText(content)
    }

    override fun initViews() {
        super.initViews()
        QMUIStatusBarHelper.translucent(this)
        binding.titleLayout.apply {
            setIcon(true)
            setTitleCenter(true)
            setTitle("编辑笔记")
            setRightButton("完成", false)
        }
    }

    override fun initListener() {
        super.initListener()
        binding.titleLayout.binding.back.setOnClickListener {
            finish()
        }

        binding.titleLayout.binding.rightButton.setOnClickListener {

        }
    }

    companion object{
        fun startEditNoteActivity(context:Context, title: String, content: String){
            val intent = Intent(context, EditNoteActivity::class.java)
            intent.putExtra("title", title)
            intent.putExtra("content", content)
            context.startActivity(intent)
        }
    }
}