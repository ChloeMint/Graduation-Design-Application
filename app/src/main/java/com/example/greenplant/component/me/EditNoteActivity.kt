package com.example.greenplant.component.me

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivityEditNoteBinding
import com.example.greenplant.util.SuperUiUtil
import com.example.greenplant.viewModel.EditNoteViewModel
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import kotlin.properties.Delegates

class EditNoteActivity : BaseViewModelActivity<ActivityEditNoteBinding>() {
    var noteId by Delegates.notNull<Int>()
    lateinit var title: String
    lateinit var content: String

    private val editNoteViewModel by lazy {
        ViewModelProvider(this)[EditNoteViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteId = intent.getIntExtra("noteId", 0)
        title = intent.getStringExtra("title").toString()
        content = intent.getStringExtra("content").toString()

        editNoteViewModel.editResponseLiveData.observe(this, Observer {
            val result = it.getOrNull()
            if (result != null){
                SuperUiUtil.newToast(this, result.msg)
                if (result.msg == "笔记修改成功"){
                    finish()
                }
            }
        })
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
            val title = binding.title.text.toString()
            val content = binding.content.text.toString()
            if (title == "" || content == ""){
                SuperUiUtil.newToast(this, "标题或内容为空")
            }else{
                editNoteViewModel.setNoteLiveData(noteId, title, content)
            }
        }
    }

    companion object{
        fun startEditNoteActivity(context:Context, noteId:Int, title: String, content: String){
            val intent = Intent(context, EditNoteActivity::class.java)
            intent.putExtra("noteId", noteId)
            intent.putExtra("title", title)
            intent.putExtra("content", content)
            context.startActivity(intent)
        }
    }
}