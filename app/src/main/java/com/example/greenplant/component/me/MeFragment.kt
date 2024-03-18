package com.example.greenplant.component.me

import android.os.Bundle
import com.example.greenplant.component.home.HomeFragment
import com.example.greenplant.databinding.FragmentMeBinding
import com.example.greenplant.fragment.BaseViewModelFragment


class MeFragment : BaseViewModelFragment<FragmentMeBinding> (){

    companion object{
        fun newInstance(): MeFragment {
            val args = Bundle()

            val fragment = MeFragment()
            fragment.arguments = args
            return fragment
        }
    }

}