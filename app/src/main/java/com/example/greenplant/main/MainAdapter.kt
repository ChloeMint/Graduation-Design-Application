package com.example.greenplant.main


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.greenplant.component.communicate.CommunicateFragment
import com.example.greenplant.component.home.HomeFragment
import com.example.greenplant.component.me.MeFragment
import com.example.greenplant.component.weather.WeatherFragment


class MainAdapter(fragmentActivity: FragmentActivity, private val count:Int) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return count
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return HomeFragment.newInstance()
            1 -> return CommunicateFragment.newInstance()
            2 -> return WeatherFragment.newInstance()
        }
        return MeFragment.newInstance()
    }

}