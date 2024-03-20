package com.example.greenplant.component.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.greenplant.databinding.FragmentBaikeBinding
import com.example.greenplant.entities.Baike
import com.example.greenplant.fragment.BaseViewModelFragment


private const val ARG_PARAM1 = "baiKe"


class BaikeFragment : BaseViewModelFragment<FragmentBaikeBinding>() {
    private var param1: Int? = null

    override fun initDatum() {
        super.initDatum()
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
    }


    companion object {

        fun newInstance(param1: Int) =
            BaikeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}