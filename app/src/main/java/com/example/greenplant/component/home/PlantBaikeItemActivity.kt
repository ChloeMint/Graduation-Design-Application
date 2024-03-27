package com.example.greenplant.component.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.greenplant.R
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivityPlantBaikeItemBinding
import com.example.greenplant.viewModel.BaikeDetailViewModel
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import kotlin.properties.Delegates

class PlantBaikeItemActivity : BaseViewModelActivity<ActivityPlantBaikeItemBinding>() {
    private val baikeDetailViewModel by lazy {
        ViewModelProvider(this)[BaikeDetailViewModel::class.java]
    }
    private var plantId by Delegates.notNull<Int>()
    private lateinit var adapter:PlantBaikeItemAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QMUIStatusBarHelper.translucent(this)
        plantId = intent.getIntExtra("plantId", 0)
        baikeDetailViewModel.baikeDetailResponse.observe(this, Observer {
            val baikeDetailResponse = it.getOrNull()
            if (baikeDetailResponse != null){
                setCenterToolBar(binding.customToolbar, baikeDetailResponse.plantName)
            }
        })
    }

    override fun initDatum() {
        super.initDatum()
        baikeDetailViewModel.setPlantId(plantId)
        adapter = PlantBaikeItemAdapter(this, DataUtil.dataList, plantId)
        binding.viewPager2.adapter = adapter
        TabLayoutViewPager2Mediator(binding.tabLayout,binding.viewPager2
        ) { _, _ -> }.attach()

    }

    override fun initViews() {
        super.initViews()

    }

    companion object{
        const val TAG = "PlantBaikeItemActivity"
        fun startActivity(context: Context, plantId:Int){
            val intent = Intent(context, PlantBaikeItemActivity::class.java)
            intent.putExtra("plantId", plantId)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
}