package com.example.greenplant.component.me

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivityNoteDetailBinding
import com.example.greenplant.databinding.NoteItemBinding
import com.example.greenplant.viewModel.GetNoteDetailViewModel
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import kotlin.properties.Delegates

class NoteDetailActivity : BaseViewModelActivity<ActivityNoteDetailBinding>() {
    var noteId by Delegates.notNull<Int>()

    private val getNoteDetailViewModel by lazy {
        ViewModelProvider(this)[GetNoteDetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteId = intent.getIntExtra("noteId", 0)

        getNoteDetailViewModel.noteDetailResponseLiveData.observe(this, Observer {
            val result = it.getOrNull()
            if (result != null){
                binding.title.text = result.title
                binding.content.text = result.content
            }
        })
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
            setResult(RESULT_OK)
            finish()
        }
    }

    override fun initListener() {
        super.initListener()
        binding.contentTextBox.setOnClickListener {
            val intent = EditNoteActivity.startEditNoteActivity(this, noteId, binding.title.text.toString(), binding.content.text.toString())
            launcher.launch(intent)
        }

    }

    override fun initDatum() {
        super.initDatum()
        getNoteDetailViewModel.setNoteId(noteId)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){
            getNoteDetailViewModel.setNoteId(noteId)
        }
    }

    companion object{
        fun startNoteDetail(context: Context, noteId:Int) : Intent{
            val intent = Intent(context, NoteDetailActivity::class.java)
            intent.putExtra("noteId", noteId)
            return intent
        }
    }
}