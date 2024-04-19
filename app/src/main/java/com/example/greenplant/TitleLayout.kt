package com.example.greenplant

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.greenplant.databinding.TitleLayoutBinding

class TitleLayout(context: Context, attr:AttributeSet) : ConstraintLayout(context, attr) {
    var binding: TitleLayoutBinding
    init {
        binding = TitleLayoutBinding.inflate(LayoutInflater.from(context),this,true)
    }

    fun setTitleCenter(value:Boolean){
        if (value){
            val titleLayoutParams = binding.titleToolbar.layoutParams as LayoutParams
            titleLayoutParams.leftToLeft = LayoutParams.PARENT_ID
            titleLayoutParams.rightToRight = LayoutParams.PARENT_ID
            binding.titleToolbar.layoutParams = titleLayoutParams
        }else{

        }
    }

    fun setTitle(text:String){
        binding.titleToolbar.text = text
    }

    fun setIcon(hide: Boolean){
        if (hide){
            binding.iconToolbar.visibility = View.GONE
        }else{
            binding.iconToolbar.visibility = View.VISIBLE

        }
    }

    fun setIcon(icon:Int){
        binding.iconToolbar.setImageResource(icon)
    }

    fun setRightButton(text:String,hide:Boolean){
        if (hide){
            binding.rightButton.visibility = View.GONE
        }else{
            binding.rightButton.visibility = View.VISIBLE
            binding.rightButton.text = text
        }
    }
}