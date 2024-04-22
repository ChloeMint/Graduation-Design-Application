package com.example.greenplant.component.me

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivityNoteDetailBinding
import com.example.greenplant.databinding.NoteItemBinding
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import kotlin.properties.Delegates

class NoteDetailActivity : BaseViewModelActivity<ActivityNoteDetailBinding>() {
    var noteId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteId = intent.getIntExtra("noteId", 0)


    }



    override fun initViews() {
        super.initViews()
        QMUIStatusBarHelper.translucent(this)
        binding.titleLayout.apply {
            setIcon(true)
            setRightButton("", true)
            setTitleCenter(true)
            setTitle("笔记详情")
        }
        binding.titleLayout.binding.back.setOnClickListener {
            finish()
        }
    }

    override fun initListener() {
        super.initListener()
    }

    override fun initDatum() {
        super.initDatum()

    }

    companion object{
        fun startNoteDetail(context: Context, noteId:Int){
            val intent = Intent(context, NoteDetailActivity::class.java)
            intent.putExtra("noteId", noteId)
            context.startActivity(intent)
        }
    }
}