package com.example.greenplant.component.home

import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

class TabLayoutViewPager2Mediator(
    private val tabLayout: TabLayout,
    private val viewPager: ViewPager2,
    private val config: ((tabLayout: TabLayout,viewPager: ViewPager2) -> Unit)? = null
) {
//    private val map = mutableMapOf<MenuItem,Int>()

    init {  // 系统自带的TabLayoutMediator是需要我们在外部自己初始化的，这里直接放到内部了
        val adapterCount:Int = DataUtil.dataList.size
        for (i in 0 until adapterCount){
            tabLayout.addTab(tabLayout.newTab().setText(DataUtil.dataList[i]),false)
        }
        tabLayout.selectTab(tabLayout.getTabAt(0))
    }

    fun attach(){
        config?.invoke(tabLayout,viewPager)     // 添加自己想要的操作，在外部传入lambada方法即可
        viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    tabLayout.selectTab(tabLayout.getTabAt(position))
                }
            }
        )

        tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val index = tab?.position
                if (viewPager.currentItem!=index){
                    viewPager.setCurrentItem(index!!,false)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

}