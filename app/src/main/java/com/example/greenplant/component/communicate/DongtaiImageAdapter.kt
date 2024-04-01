package com.example.greenplant.component.communicate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginRight
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.greenplant.R
import com.example.greenplant.databinding.DongtaiImageItemBinding
import com.example.greenplant.databinding.DongtaiItemsBinding

class DongtaiImageAdapter(private val context:Context,private val imageList: List<String>) : RecyclerView.Adapter<DongtaiImageAdapter.ViewHolder>() {
    private val displayWidth = context.resources.displayMetrics.widthPixels
    private val imageWidth = (displayWidth - 80 - 30 - 30 - 150)/3
    inner class ViewHolder(val binding:DongtaiImageItemBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DongtaiImageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val layoutParams = image.layoutParams
            layoutParams.width = imageWidth
            layoutParams.height = imageWidth
            image.layoutParams = layoutParams


            Glide.with(image).load(imageList[position]).apply {
                // 下载error时显示的图片
                error(R.drawable.load_failed)
            }.into(image)
        }
    }
}