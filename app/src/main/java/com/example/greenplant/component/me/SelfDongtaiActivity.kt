package com.example.greenplant.component.me

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.greenplant.R
import com.example.greenplant.ServiceCreator
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivitySelfDongtaiBinding
import com.example.greenplant.entities.Dongtai
import com.example.greenplant.viewModel.*
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import kotlin.properties.Delegates

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

    private val userInfoViewModel by lazy {
        ViewModelProvider(this)[UserInfoViewModel::class.java]
    }

    private val dataList = mutableListOf<Dongtai>()

    var page = 1

    lateinit var adapter: SelfDongTaiAdapter

    private var userId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getUserDongtaiViewModel.userDongtaiLiveData.observe(this, Observer {
            val dongtaiList = it.getOrNull()
            if (dongtaiList != null) {
                if (dongtaiList.isNotEmpty()){
                    if (page == 1){
                        dataList.clear()
                    }
                    dataList.addAll(dongtaiList)
                    adapter.notifyDataSetChanged()
                    processRefreshAndLoadMoreStatus(true)
                }else{
                    page -= 1
                    processRefreshAndLoadMoreStatus(success = true, noMore = true)
                }
            }
        })

        userInfoViewModel.userInfoLiveData.observe(this, Observer {
            val user = it.getOrNull()
            if (user != null){
                binding.username.text = user.username
                binding.introduction.text = user.introduction
                Glide.with(binding.avatar).load(ServiceCreator.BASE_URL + user.avatar).apply {
                    // 下载error时显示的图片
                    error(R.drawable.load_failed)
                }.into(binding.avatar)
            }
        })
    }

    override fun initViews() {
        super.initViews()
        QMUIStatusBarHelper.translucent(this)
    }

    override fun initListener() {
        super.initListener()
        binding.refreshLayout.setOnRefreshListener {
            page = 1
            getUserDongtaiViewModel.setUserId(userId,page)
        }

        binding.refreshLayout.setOnLoadMoreListener {
            page+=1
            getUserDongtaiViewModel.setUserId(userId,page)
        }
    }

    override fun initDatum() {
        super.initDatum()
        userId = intent.getIntExtra("userId",0)
        getUserDongtaiViewModel.setUserId(userId,page)
        adapter = SelfDongTaiAdapter(this, dataList, userId, likeAndCancelViewModel, deleteDongtaiViewModel)
        binding.selfDongtaiRecycleView.adapter = adapter
        binding.selfDongtaiRecycleView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        userInfoViewModel.setIsGettingUserInfo()
    }

    private fun processRefreshAndLoadMoreStatus(success:Boolean, noMore:Boolean = false){
        binding.refreshLayout.finishRefresh(500,success,false)
        // 这里的noMoreData是为loadMore方法服务的
        binding.refreshLayout.finishLoadMore(500,success,noMore)
    }

    companion object{
        fun startSelfDongtaiActivity(context:Context, userId:Int){
            val intent = Intent(context,SelfDongtaiActivity::class.java)
            intent.putExtra("userId", userId)
            context.startActivity(intent)
        }
    }
}