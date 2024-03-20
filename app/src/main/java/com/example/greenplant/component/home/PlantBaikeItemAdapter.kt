package com.example.greenplant.component.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PlantBaikeItemAdapter(fragmentActivity: FragmentActivity, private val dataList: List<String>, private val plantId:Int) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> BaikeFragment.newInstance(plantId)
            else -> CultureFragment.newInstance(plantId)
        }
    }
}