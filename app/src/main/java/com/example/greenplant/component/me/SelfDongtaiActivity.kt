package com.example.greenplant.component.me

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivitySelfDongtaiBinding
import com.example.greenplant.entities.Dongtai
import com.example.greenplant.viewModel.*
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

class SelfDongtaiActivity : BaseViewModelActivity<ActivitySelfDongtaiBinding>() {
    private val getUserDongtaiViewModel by lazy {
        ViewModelProvider(this)[GetUserDongtaiViewModel::class.java]
    }

    private val likeAndCancelViewModel by lazy {
        ViewModelProvider(this)[LikeAndCancelViewModel::class.java]
    }

    private val deleteDongtaiViewModel by lazy {
        ViewModelProvider(this)[DeleteDongtaiViewModel::class.java]
    }

    private val dataList = mutableListOf<Dongtai>()

    var page = 1

    lateinit var adapter: SelfDongTaiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getUserDongtaiViewModel.userDongtaiLiveData.observe(this, Observer {
            val dongtaiList = it.getOrNull()
            if (dongtaiList != null) {
                if (dongtaiList.isNotEmpty()){
                    dataList.addAll(dongtaiList)
                    adapter.notifyDataSetChanged()
                }else{
                    page -= 1
                }
            }
        })
    }

    override fun initViews() {
        super.initViews()
        QMUIStatusBarHelper.translucent(this)
    }

    override fun initDatum() {
        super.initDatum()
        val userId = intent.getIntExtra("userId",0)
        getUserDongtaiViewModel.setUserId(userId,page)
        adapter = SelfDongTaiAdapter(this, dataList, userId, likeAndCancelViewModel, deleteDongtaiViewModel)
        binding.selfDongtaiRecycleView.adapter = adapter
        binding.selfDongtaiRecycleView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }

    fun refreshData(){

    }

    companion object{
        fun startSelfDongtaiActivity(context:Context, userId:Int){
            val intent = Intent(context,SelfDongtaiActivity::class.java)
            intent.putExtra("userId", userId)
            context.startActivity(intent)
        }
    }
}