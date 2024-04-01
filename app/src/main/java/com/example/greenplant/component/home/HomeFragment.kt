package com.example.greenplant.component.home

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.greenplant.R
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

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 放到onViewCreated分开的其他方法中可能无法通过点击事件正常监听，所以放到onCreate的时候是最好的
        baikeViewModel.baikeResponseLiveData.observe(requireActivity(), Observer {
            val baikeResponse = it.getOrNull()
            if (baikeResponse!!.isNotEmpty()) {
                dataList.clear()
                dataList.addAll(baikeResponse)
                adapter.notifyDataSetChanged()
            }else{
                // 一个小bug，在到最后一页数据的时候需要点两下才能切换回第一页
                baikeViewModel.setPage(1)
                page = 1
            }
        })
    }

    override fun initView() {
        super.initView()

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
    }

    override fun initListener() {
        super.initListener()
        binding.changePlant.setOnClickListener {
            page+=1
            baikeViewModel.setPage(page)
        }

        binding.searchBox.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }

        binding.plantRecognition.setOnClickListener {
            val intent = Intent(requireContext(), PlantRecognitionActivity::class.java)
            startActivity(intent)
        }

        binding.photoRecognition.setOnClickListener {
            val intent = Intent(requireContext(), PlantPhotoRecognitionActivity::class.java)
            startActivity(intent)
        }

        binding.plantDocker.setOnClickListener {
            val intent = Intent(requireContext(), PlantDockerActivity::class.java)
            startActivity(intent)
        }
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