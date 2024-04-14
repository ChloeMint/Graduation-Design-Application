package com.example.greenplant.component.me

import android.os.Bundle
import android.util.Log
import com.example.greenplant.component.home.HomeFragment
import com.example.greenplant.databinding.FragmentMeBinding
import com.example.greenplant.fragment.BaseViewModelFragment


class MeFragment : BaseViewModelFragment<FragmentMeBinding> (){
    override fun initDatum() {
        super.initDatum()
//        binding.cardView.post {
//            Log.d("MeFragment_cardView", "${binding.cardView.measuredWidth/requireActivity().resources.displayMetrics.density}")
//        }
    }
    companion object{
        fun newInstance(): MeFragment {
            val args = Bundle()

            val fragment = MeFragment()
            fragment.arguments = args
            return fragment
        }
    }

}