package com.example.greenplant.component.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.greenplant.GreenPlantApplication
import com.example.greenplant.R
import com.example.greenplant.databinding.PlantItemBinding
import com.example.greenplant.entities.Baike

class PlantItemAdapter(val context:Context, private val dataList: List<Baike>) : RecyclerView.Adapter<PlantItemAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: PlantItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(PlantItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.absoluteAdapterPosition
            val plantItemId = dataList[position].id
            PlantBaikeItemActivity.startActivity(GreenPlantApplication.context, plantItemId)
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val baike = dataList[position]
        holder.binding.apply {
            Glide.with(image).load(baike.imageList[0]).apply {
                // 下载error时显示的图片
                error(R.drawable.load_failed)
            }.into(image)


            chineseName.text = baike.plantName
            englishName.text = baike.plantEnglishName
        }

    }
}