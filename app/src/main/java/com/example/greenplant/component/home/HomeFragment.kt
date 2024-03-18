package com.example.greenplant.component.home

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.greenplant.R
import com.example.greenplant.Repository
import com.example.greenplant.databinding.FragmentHomeBinding
import com.example.greenplant.entities.Baike
import com.example.greenplant.fragment.BaseViewModelFragment
import com.example.greenplant.viewModel.GetBaikeViewModel
import com.qmuiteam.qmui.util.QMUIStatusBarHelper


class HomeFragment : BaseViewModelFragment<FragmentHomeBinding>() {
    private var page = 1
    private val dataList = mutableListOf<Baike>()
    lateinit var adapter: PlantItemAdapter
    private val baikeViewModel by lazy {
        ViewModelProvider(this)[GetBaikeViewModel::class.java]
    }

    override fun initViews() {
        super.initViews()
        QMUIStatusBarHelper.setStatusBarDarkMode(requireActivity())

        // 设置topBar的背景图片
        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.plant)
        drawable!!.colorFilter =
            PorterDuffColorFilter(
                Color.argb(20, 255, 255, 255),
                PorterDuff.Mode.SRC_ATOP
            ) // 设置50%透明度

        binding.topBar.background = drawable
    }

    override fun initDatum() {
        super.initDatum()
        baikeViewModel.setPage(page)

        binding.recycleView.layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
        adapter = PlantItemAdapter(requireContext(),dataList)
        binding.recycleView.adapter = adapter
        baikeViewModel.baikeResponseLiveData.observe(this , Observer {
            val baikeResponse = it.getOrNull()
            if (baikeResponse != null) {
                dataList.clear()
                dataList.addAll(baikeResponse)
                adapter.notifyDataSetChanged()
            }
        })

    }

    companion object{
        fun newInstance(): HomeFragment {
            val args = Bundle()

            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }


}