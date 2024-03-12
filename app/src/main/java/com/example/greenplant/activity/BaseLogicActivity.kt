package com.example.greenplant.activity

import android.content.Intent
import androidx.appcompat.widget.Toolbar
import com.example.greenplant.R

open class BaseLogicActivity : BaseCommonActivity() {
    fun setToolBar(toolbar: Toolbar, title:String){
        setSupportActionBar(toolbar)
        supportActionBar!!.title = title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationIcon(R.drawable.back)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}