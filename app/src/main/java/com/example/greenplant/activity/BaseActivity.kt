package com.example.greenplant.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    protected open fun initViews(){

    }

    protected open fun initDatum(){

    }

    protected open fun initListener(){

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initViews()
        initDatum()
        initListener()
    }

}