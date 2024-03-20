package com.example.greenplant

import android.os.Bundle
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivityMainBinding
import com.example.greenplant.databinding.ItemTabBinding
import com.example.greenplant.main.MainAdapter
import com.example.greenplant.util.SuperUiUtil
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

class MainActivity : BaseViewModelActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QMUIStatusBarHelper.translucent(this)
        if (SuperUiUtil.isDark(this)){
            QMUIStatusBarHelper.setStatusBarDarkMode(this)
        }else{
            QMUIStatusBarHelper.setStatusBarLightMode(this)
        }
    }

    override fun initViews() {
        super.initViews()



        for (i in tabLayoutTitles.indices){
            ItemTabBinding.inflate(layoutInflater).apply {
                content.setText(tabLayoutTitles[i])
                icon.setImageResource(tabLayoutIcons[i])
                binding.tabLayout.addView(root) // 这里的root指的是ItemTabBinding的所有布局，就像以前设置的binding.root
            }
        }

        binding.apply {
            viewPager2.offscreenPageLimit = tabLayoutTitles.size   // 缓存多少个界面
            viewPager2.adapter = MainAdapter(this@MainActivity,tabLayoutTitles.size)
        }

        // 绑定tabLayout和viewPager2
        ViewPager2Delegate.install(binding.viewPager2,binding.tabLayout,true)
    }

    override fun initDatum() {
        super.initDatum()


    }


    companion object{
        private val tabLayoutTitles = intArrayOf(
            R.string.home,
            R.string.shequ,
            R.string.weather,
            R.string.me
        )

        private val tabLayoutIcons = intArrayOf(
            R.drawable.home_tab,
            R.drawable.shequ_tab,
            R.drawable.tianqi_tab,
            R.drawable.me_tab
        )
    }
}