package com.example.greenplant.component.guide

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class GuideAdapter(val context: Context, fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    private val imageList = mutableListOf<Int>()

    fun setImageList(imageList: MutableList<Int>){
        this.imageList.clear()
        this.imageList.addAll(imageList)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return imageList.size
    }

    override fun getItem(position: Int): Fragment {
        return GuideFragment.newInstance(imageList[position])
    }

}

