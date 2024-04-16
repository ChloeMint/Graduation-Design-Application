package com.example.greenplant.component.me

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greenplant.databinding.MeItemBinding
import com.example.greenplant.entities.MeItem

class MeItemAdapter(private val context:Context, private val dataList: List<MeItem>, private val itemOnclickListener: ItemOnclickListener) : RecyclerView.Adapter<MeItemAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: MeItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meItem = dataList[position]
        holder.binding.apply {
            meItemBox.setOnClickListener {
                itemOnclickListener.itemOnclickListener(position)
            }

            icon.setImageResource(meItem.icon)
            text.text = meItem.text
        }

    }
}