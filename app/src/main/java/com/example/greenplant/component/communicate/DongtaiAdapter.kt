package com.example.greenplant.component.communicate

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.greenplant.R
import com.example.greenplant.ServiceCreator
import com.example.greenplant.databinding.DongtaiItemsBinding
import com.example.greenplant.entities.Dongtai
import com.example.greenplant.entities.PublishComment
import com.example.greenplant.util.DefaultPreferencesUtil
import com.example.greenplant.viewModel.DeleteDongtaiViewModel
import com.example.greenplant.viewModel.LikeAndCancelViewModel
import com.example.greenplant.viewModel.PublishCommentViewModel


class DongtaiAdapter(val context:Context, private var dataList: List<Dongtai>, private val model: LikeAndCancelViewModel, private val publishCommentViewModel: PublishCommentViewModel, private val deleteDongtaiViewModel: DeleteDongtaiViewModel) : RecyclerView.Adapter<DongtaiAdapter.ViewHolder>() {
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
            video.visibility = View.GONE
            delete.visibility = View.GONE

            time.text = dongtai.publish_time

            Glide.with(avatar).load(ServiceCreator.BASE_URL+dongtai.user.avatar).apply {
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

            delete.setOnClickListener {
                AlertDialog.Builder(context).apply {
                    setTitle("警告")
                    setMessage("您确定要删除这条动态吗？")
                    setCancelable(false)
                    setPositiveButton("确定"){ _, _ ->
                        deleteDongtaiViewModel.setDongtaiIdLiveData(dongtai.id)
                    }
                    setNegativeButton("取消"){ _, _ ->}
                }.show()
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

            if (dongtai.user_id == currentUserId){
                delete.visibility = View.VISIBLE
            }

            try {
                if (dongtai.videoUrl != null){
                    video.visibility = View.VISIBLE
                    val mediaController = MediaController(context)
                    val uri = ServiceCreator.BASE_URL + dongtai.videoUrl
                    video.setVideoURI(Uri.parse(uri))
                    video.setMediaController(mediaController)
                    mediaController.setMediaPlayer(video)
//                    video.seekTo(1)
                }
            }catch (e:Exception){
                e.printStackTrace()
            }




            imageRecycleView.adapter = DongtaiImageAdapter(context, dongtai.imageList)
            imageRecycleView.layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)

            commentRecycleView.adapter = CommentAdapter(dongtai.comments)
            commentRecycleView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            edit.doOnTextChanged { _, _, _, _ ->
                sendButton.isEnabled = edit.text.toString() != ""
            }

            sendButton.setOnClickListener {
                val publishComment = PublishComment(dongtai.id, edit.text.toString())
                publishCommentViewModel.setDongtaiIdLiveData(publishComment)
                edit.setText("")
            }
        }
    }


}