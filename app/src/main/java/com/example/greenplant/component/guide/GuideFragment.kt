package com.example.greenplant.component.guide


import android.os.Bundle
import com.example.greenplant.Constant
import com.example.greenplant.databinding.FragmentGuideBinding
import com.example.greenplant.fragment.BaseViewModelFragment


class GuideFragment : BaseViewModelFragment<FragmentGuideBinding>() {
    override fun initDatum() {
        super.initDatum()
        val data = requireArguments().getInt(Constant.ID)
        binding.imageView.setImageResource(data)
    }


    companion object{
        fun newInstance(data:Int):GuideFragment{
            val args = Bundle()   // 传递数据的键值对集合
            args.putInt(Constant.ID,data)

            val fragment = GuideFragment()
            fragment.arguments = args
            return fragment
        }
    }


}