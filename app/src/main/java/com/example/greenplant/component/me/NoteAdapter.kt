package com.example.greenplant.component.me

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greenplant.databinding.NoteItemBinding
import com.example.greenplant.entities.Note

class NoteAdapter(val context:Context, val dataList: List<Note>) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
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
            delete.setOnClickListener {
                Log.d("NoteAdapter", "delete: $position")
            }
        }

        holder.itemView.setOnClickListener {
            Log.d("NoteAdapter", "itemView: $position")
        }

    }
}