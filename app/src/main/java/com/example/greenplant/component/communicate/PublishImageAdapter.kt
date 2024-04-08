package com.example.greenplant.component.communicate

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greenplant.databinding.PublishImageItemBinding
import com.luck.picture.lib.entity.LocalMedia

class PublishImageAdapter(private val context: Context, private val imageList: ArrayList<LocalMedia>, private val onImagesRemovedListener: OnImagesRemovedListener) : RecyclerView.Adapter<PublishImageAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: PublishImageItemBinding) : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PublishImageItemBinding.inflate(
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
            val imageUrl = imageList[position]
            val uri = Uri.parse(imageUrl.path)
            image.setImageURI(uri)

            cancel.setOnClickListener {
                imageList.removeAt(position)
                notifyDataSetChanged()

                if (imageList.isEmpty()){
                    onImagesRemovedListener.onImagesRemoved()
                }
            }
        }
    }


}