package com.example.greenplant.component.home

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.greenplant.GreenPlantApplication
import com.example.greenplant.databinding.FragmentBaikeBinding
import com.example.greenplant.fragment.BaseViewModelFragment
import com.example.greenplant.viewModel.BaikeDetailViewModel
import kotlin.properties.Delegates


private const val ARG_PARAM1 = "plantId"


class BaikeFragment : BaseViewModelFragment<FragmentBaikeBinding>() {
    private var param1 by Delegates.notNull<Int>()   // plantId
    lateinit var adapter: PlantImageAdapter

    private val baikeDetailViewModel by lazy {
        ViewModelProvider(this)[BaikeDetailViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baikeDetailViewModel.baikeDetailResponse.observe(this, Observer {
            val baikeDetailResponse = it.getOrNull()
            if (baikeDetailResponse != null){
                binding.plantName.text = baikeDetailResponse.plantName
                binding.englishName.text = baikeDetailResponse.plantEnglishName
                binding.introduction.text = baikeDetailResponse.introduction
                binding.careKnowledge.text = baikeDetailResponse.careKnowledge
                binding.area.text = baikeDetailResponse.area
                adapter = PlantImageAdapter(GreenPlantApplication.context, baikeDetailResponse.imageList)
                binding.imageRecycleView.layoutManager = GridLayoutManager(GreenPlantApplication.context, 3)
                binding.imageRecycleView.adapter = adapter
            }
        })
    }


    override fun initDatum() {
        super.initDatum()
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }

        baikeDetailViewModel.setPlantId(param1)
    }






    companion object {
        fun newInstance(plantId: Int) =
            BaikeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, plantId)
                }
            }
    }
}