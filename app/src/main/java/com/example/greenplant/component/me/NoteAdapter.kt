package com.example.greenplant.component.me

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greenplant.MainActivity
import com.example.greenplant.databinding.NoteItemBinding
import com.example.greenplant.entities.Note
import com.example.greenplant.viewModel.DeleteNoteViewModel

class NoteAdapter(val context:Context, val dataList: List<Note>, private val deleteNoteViewModel: DeleteNoteViewModel, private val deleteNote: DeleteNote) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = dataList[position]
        holder.binding.apply {
            title.text = note.title
            content.text = note.content

            cardView.setOnClickListener {
                NoteDetailActivity.startNoteDetail(context, note.id)
            }

            delete.setOnClickListener {
                AlertDialog.Builder(context).apply {
                    setTitle("警告")
                    setMessage("您确定要删除这条笔记吗？")
                    setCancelable(false)
                    setPositiveButton("确定"){ _, _ ->
                        deleteNoteViewModel.setNoteId(note.id)
                        deleteNote.deleteNote(position)
                    }
                    setNegativeButton("取消"){ _, _ ->}
                }.show()

            }
        }
    }
}