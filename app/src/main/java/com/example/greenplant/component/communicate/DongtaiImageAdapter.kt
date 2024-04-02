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
import kotlin.math.roundToInt

class DongtaiImageAdapter(private val context:Context,private val imageList: List<String>) : RecyclerView.Adapter<DongtaiImageAdapter.ViewHolder>() {


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

            Glide.with(image).load(imageList[position]).apply {
                // 下载error时显示的图片
                error(R.drawable.load_failed)
            }.into(image)
        }
    }
}