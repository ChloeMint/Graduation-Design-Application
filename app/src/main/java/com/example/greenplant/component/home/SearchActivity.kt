package com.example.greenplant.component.home

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivitySearchBinding
import com.example.greenplant.entities.Baike
import com.example.greenplant.entities.BaikeResponse
import com.example.greenplant.viewModel.BaikeSearchViewModel
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

class SearchActivity : BaseViewModelActivity<ActivitySearchBinding>() {
    private val baikeSearchViewModel by lazy {
        ViewModelProvider(this)[BaikeSearchViewModel::class.java]
    }
    private val baikeList = mutableListOf<Baike>()
    lateinit var adapter: PlantItemAdapter
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = PlantItemAdapter(this,baikeList)
        binding.searchRecycleView.layoutManager = GridLayoutManager(this, 2)
        binding.searchRecycleView.adapter = adapter
        baikeSearchViewModel.plantSearchResponseLiveData.observe(this, Observer {
            val baikeResponse = it.getOrNull()
            if (baikeResponse != null) {
//                Log.d(TAG, "onCreate: $baikeResponse")
                baikeList.clear()
                if (baikeResponse.isNotEmpty()){
                    baikeList.addAll(baikeResponse)
                    binding.noMessageImage.visibility = View.INVISIBLE
                    binding.noMessageText.visibility = View.INVISIBLE
                    binding.searchRecycleView.visibility = View.VISIBLE
                }
                adapter.notifyDataSetChanged()
            }else{
                    binding.noMessageImage.visibility = View.VISIBLE
                    binding.noMessageText.visibility = View.VISIBLE
                binding.searchRecycleView.visibility = View.INVISIBLE
            }
        })

    }
    override fun initViews() {
        super.initViews()
        QMUIStatusBarHelper.translucent(this)


    }


    override fun initListener() {
        super.initListener()
        binding.includeSearchToolBarView.editView.doOnTextChanged { text, start, before, count ->
            if (text?.length != 0){
                binding.includeSearchToolBarView.cancelImage.visibility = View.VISIBLE
                baikeSearchViewModel.setPlantName(text.toString())
            }else{
                binding.includeSearchToolBarView.cancelImage.visibility = View.INVISIBLE
                binding.noMessageImage.visibility = View.VISIBLE
                binding.noMessageText.visibility = View.VISIBLE
                binding.searchRecycleView.visibility = View.INVISIBLE
            }
        }

        binding.includeSearchToolBarView.cancel.setOnClickListener {
            finish()
        }

        binding.includeSearchToolBarView.cancelImage.setOnClickListener {
            binding.includeSearchToolBarView.editView.setText("")
            binding.includeSearchToolBarView.cancelImage.visibility = View.INVISIBLE
            binding.noMessageImage.visibility = View.VISIBLE
            binding.searchRecycleView.visibility = View.INVISIBLE
            binding.noMessageText.visibility = View.VISIBLE
        }
    }
    companion object{
        const val TAG = "SearchActivity"
    }
}