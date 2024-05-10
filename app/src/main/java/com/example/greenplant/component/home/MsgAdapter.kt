package com.example.greenplant.component.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.greenplant.R
import com.example.greenplant.ServiceCreator
import com.example.greenplant.databinding.ActivityChangeUserPasswordBinding
import com.example.greenplant.databinding.ActivityPlantDockerBinding
import com.example.greenplant.databinding.DockerChatBinding
import com.example.greenplant.databinding.MeChatBinding
import com.example.greenplant.databinding.PlantImageBinding
import com.example.greenplant.entities.Msg

class MsgAdapter(private val context:Context, private val msgList: MutableList<Msg>, private val userAvatar:String) : RecyclerView.Adapter<MsgAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return msgList[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == Msg.RECEIVE){
            return ViewHolder(
                DockerChatBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
        return ViewHolder(
            MeChatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return msgList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val msg = msgList[position]
        if (msg.type == Msg.RECEIVE){
            val  binding = holder.binding as DockerChatBinding
            binding.content.text = msg.msg
        }else{
            val binding = holder.binding as MeChatBinding
            Glide.with(binding.userAvatar).load(ServiceCreator.BASE_URL + userAvatar).apply {
                // 下载error时显示的图片
                error(R.drawable.load_failed)
            }.into(binding.userAvatar)
            binding.content.text = msg.msg
        }
    }
}