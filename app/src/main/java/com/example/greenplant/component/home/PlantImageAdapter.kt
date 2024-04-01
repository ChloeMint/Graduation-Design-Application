package com.example.greenplant.component.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.greenplant.R
import com.example.greenplant.databinding.PlantImageBinding

class PlantImageAdapter(val context:Context, private val imageList: List<String>) : RecyclerView.Adapter<PlantImageAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: PlantImageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PlantImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val imageList = imageList.subList(0, 3)
            val displayWidth = context.resources.displayMetrics.widthPixels
            Glide.with(plantImage).load(imageList[position]).apply {
                // 下载error时显示的图片
                error(R.drawable.load_failed)
            }.into(plantImage)

            val params = imageCard.layoutParams as ViewGroup.MarginLayoutParams
            params.width = (displayWidth - 80 - 55)/3
            params.height = (displayWidth - 80 - 55)/3
            imageCard.layoutParams = params
        }
    }
}