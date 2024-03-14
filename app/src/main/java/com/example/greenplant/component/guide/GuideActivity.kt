package com.example.greenplant.component.guide


import com.example.greenplant.R
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.component.login.LoginActivity
import com.example.greenplant.component.register.RegisterActivity
import com.example.greenplant.databinding.ActivityGuideBinding

class GuideActivity : BaseViewModelActivity<ActivityGuideBinding>() {
    private lateinit var adapter:GuideAdapter

    override fun initDatum() {
        super.initDatum()
        val imageList = mutableListOf<Int>()
        imageList.add(R.drawable.first)
        imageList.add(R.drawable.second)
        imageList.add(R.drawable.third)

        adapter = GuideAdapter(this, supportFragmentManager)
        adapter.setImageList(imageList)
        binding.viewPager.adapter = adapter

        binding.circleIndicator.setViewPager(binding.viewPager)
        adapter.registerDataSetObserver(binding.circleIndicator.dataSetObserver)


    }

    override fun initViews() {
        super.initViews()
    }

    override fun initListener() {
        super.initListener()
        binding.login.setOnClickListener {
            startTheActivity(LoginActivity::class.java)
        }
        binding.register.setOnClickListener {
            startTheActivity(RegisterActivity::class.java)
        }
    }

}