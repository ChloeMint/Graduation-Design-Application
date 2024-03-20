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

    override fun onCreate(savedInstanceState: Bundle?) {
        plantId = intent.getIntExtra("plantId", 0)


        super.onCreate(savedInstanceState)
        baikeDetailViewModel.baikeDetailResponse.observe(this, Observer {
            val baikeDetailResponse = it.getOrNull()
            if (baikeDetailResponse != null){

//                setSupportActionBar(binding.customToolbar.toolBar)
//                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//                supportActionBar!!.title=""
//                binding.customToolbar.toolBar.setNavigationIcon(R.drawable.back)
//                binding.customToolbar.toolBar.setNavigationOnClickListener {
//                    finish()
//                }
//                binding.customToolbar.toolBarTitle.text = baikeDetailResponse.plantName

                setCenterToolBar(binding.customToolbar, baikeDetailResponse.plantName)


            }
        })
    }

    override fun initDatum() {
        super.initDatum()
        baikeDetailViewModel.setPlantId(plantId)
        QMUIStatusBarHelper.translucent(this)
    }

    override fun initViews() {
        super.initViews()

    }

    companion object{
        const val TAG = "PlantBaikeItemActivity"
        fun startActivity(context: Context, plantId:Int){
            val intent = Intent(context, PlantBaikeItemActivity::class.java)
            intent.putExtra("plantId", plantId)
            context.startActivity(intent)
        }
    }
}