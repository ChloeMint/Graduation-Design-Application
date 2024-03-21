package com.example.greenplant.component.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.greenplant.databinding.FragmentBaikeBinding
import com.example.greenplant.databinding.FragmentCultureBinding
import com.example.greenplant.entities.Baike
import com.example.greenplant.fragment.BaseViewModelFragment
import com.example.greenplant.viewModel.BaikeDetailViewModel


private const val ARG_PARAM1 = "plantId"


class CultureFragment : BaseViewModelFragment<FragmentCultureBinding>() {
    private var param1: Int? = null
    private val baikeDetailViewModel by lazy {
        ViewModelProvider(this)[BaikeDetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baikeDetailViewModel.baikeDetailResponse.observe(this, Observer {
            val baikeDetailResponse = it.getOrNull()
            if (baikeDetailResponse != null){
                binding.plantCulture.text = baikeDetailResponse.plantCulture
                binding.legend.text = baikeDetailResponse.legendStory
            }
        })
    }

    override fun initDatum() {
        super.initDatum()
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
        param1?.let { baikeDetailViewModel.setPlantId(it) }
    }


    companion object {

        fun newInstance(plantId: Int) =
            CultureFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, plantId)
                }
            }
    }
}