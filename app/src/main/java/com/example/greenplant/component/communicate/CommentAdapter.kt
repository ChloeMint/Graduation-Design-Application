package com.example.greenplant.component.communicate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greenplant.databinding.CommentItemBinding
import com.example.greenplant.databinding.DongtaiItemsBinding
import com.example.greenplant.entities.Comment

class CommentAdapter(private val dataList: List<Comment>) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CommentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = dataList[position]
        holder.binding.apply {
            commentText.text = comment.user.username + ":" +comment.comment_text
        }
    }
}