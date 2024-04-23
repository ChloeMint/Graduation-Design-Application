package com.example.greenplant.component.me

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivityNoteBinding
import com.example.greenplant.entities.Note
import com.example.greenplant.util.SuperUiUtil
import com.example.greenplant.viewModel.DeleteNoteViewModel
import com.example.greenplant.viewModel.GetNotesViewModel
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

class NoteActivity : BaseViewModelActivity<ActivityNoteBinding>() {
    lateinit var adapter: NoteAdapter
    private val noteList = mutableListOf<Note>()

    private val getNoteViewModel by lazy {
        ViewModelProvider(this)[GetNotesViewModel::class.java]
    }

    private val deleteNoteIdViewModel by lazy {
        ViewModelProvider(this)[DeleteNoteViewModel::class.java]
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){
            noteList.clear()
            getNoteViewModel.setFlag()
            adapter.notifyDataSetChanged()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getNoteViewModel.noteResponse.observe(this, Observer {
            val result = it.getOrNull()
            if (result != null){
                noteList.addAll(result)
                adapter.notifyDataSetChanged()
            }
        })

        deleteNoteIdViewModel.deleteResponseLiveData.observe(this, Observer {
            val result = it.getOrNull()
            if (result != null){
                SuperUiUtil.newToast(this, result.msg)
                adapter.notifyDataSetChanged()
            }
        })
    }
    override fun initViews() {
        super.initViews()
        QMUIStatusBarHelper.translucent(this)
        adapter = NoteAdapter(this, noteList, deleteNoteIdViewModel, object : DeleteNote{
            override fun deleteNote(position: Int) {
                noteList.removeAt(position)
            }
        }, launcher)
        binding.noteRecycleView.adapter = adapter
        binding.noteRecycleView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun initDatum() {
        super.initDatum()
        getNoteViewModel.setFlag()
    }

    override fun initListener() {
        super.initListener()
        binding.toolBar.binding.back.setOnClickListener {
            finish()
        }
    }
}