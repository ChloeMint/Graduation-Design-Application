package com.example.greenplant.component.communicate

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.greenplant.GreenPlantApplication
import com.example.greenplant.GreenPlantNetwork
import com.example.greenplant.R
import com.example.greenplant.databinding.DongtaiItemsBinding
import com.example.greenplant.entities.Dongtai
import com.example.greenplant.util.DefaultPreferencesUtil
import com.example.greenplant.viewModel.LikeAndCancelViewModel

class DongtaiAdapter(val context:Context, private var dataList: List<Dongtai>, private val model: LikeAndCancelViewModel) : RecyclerView.Adapter<DongtaiAdapter.ViewHolder>() {
    private val currentUserId = DefaultPreferencesUtil.getUserId()
    inner class ViewHolder (val binding: DongtaiItemsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DongtaiItemsBinding.inflate(
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
        val dongtai = dataList[position]
        holder.binding.apply {
            content.visibility = View.GONE
            imageRecycleView.visibility = View.GONE
            commentRecycleView.visibility =  View.GONE

            time.text = dongtai.publish_time

            Glide.with(avatar).load(dongtai.user.avatar).apply {
                // 下载error时显示的图片
                error(R.drawable.load_failed)
            }.into(avatar)

            username.text = dongtai.user.username
            likeNum.text = dongtai.like.size.toString()

            like.setImageResource(R.drawable.like_un)

            for (i in dongtai.like){
                if (i.user_id == currentUserId){
                    like.setImageResource(R.drawable.like)
                }
            }

            like.setOnClickListener {
                model.setDongtaiLiveData(dongtai.id)
            }

            if (dongtai.article_text != ""){
                content.visibility = View.VISIBLE
                content.text = dongtai.article_text
            }
            if (dongtai.imageList.isNotEmpty()){
                imageRecycleView.visibility = View.VISIBLE
            }

            if (dongtai.comments.isNotEmpty()){
                commentRecycleView.visibility = View.VISIBLE
            }

            imageRecycleView.adapter = DongtaiImageAdapter(context, dongtai.imageList)
            imageRecycleView.layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        }
    }


}