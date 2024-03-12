package com.example.greenplant.activity

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.example.greenplant.util.CreateBindingClassUtil

open class BaseViewModelActivity<VB:ViewBinding>() : BaseLogicActivity(){
    private var _binding:VB? = null	// 如果是在Activity中这里可以简化处理
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = CreateBindingClassUtil.newViewBinding(layoutInflater,javaClass)
        setContentView(binding.root)
    }

}