package com.example.greenplant.component.home

import android.content.Context
import android.content.Intent
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivityPlantBaikeItemBinding

class PlantBaikeItemActivity : BaseViewModelActivity<ActivityPlantBaikeItemBinding>() {
    override fun initDatum() {
        super.initDatum()
    }

    companion object{
        fun startActivity(context: Context, plantId:Int){
            val intent = Intent(context, PlantBaikeItemActivity::class.java)
            intent.putExtra("plantId", plantId)
            context.startActivity(intent)
        }
    }
}