package com.example.greenplant.component.communicate

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.greenplant.R
import com.example.greenplant.ServiceCreator
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.component.me.SelfDongTaiAdapter
import com.example.greenplant.databinding.ActivityUserDongTaiBinding
import com.example.greenplant.entities.Dongtai
import com.example.greenplant.util.DefaultPreferencesUtil
import com.example.greenplant.util.SuperUiUtil
import com.example.greenplant.viewModel.DeleteDongtaiViewModel
import com.example.greenplant.viewModel.GetUserDongtaiViewModel
import com.example.greenplant.viewModel.GetUserPersonalInfoViewModel
import com.example.greenplant.viewModel.LikeAndCancelViewModel
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import kotlin.math.abs
import kotlin.properties.Delegates

class UserDongTaiActivity : BaseViewModelActivity<ActivityUserDongTaiBinding>() {
    private val getUserPersonalInfoViewModel by lazy {
        ViewModelProvider(this)[GetUserPersonalInfoViewModel::class.java]
    }
    private val likeAndCancelViewModel by lazy {
        ViewModelProvider(this)[LikeAndCancelViewModel::class.java]
    }
    private val deleteDongtaiViewModel by lazy {
        ViewModelProvider(this)[DeleteDongtaiViewModel::class.java]
    }
    private val getUserDongtaiViewModel by lazy {
        ViewModelProvider(this)[GetUserDongtaiViewModel::class.java]
    }
    var userId by Delegates.notNull<Int>()
    private val dataList = mutableListOf<Dongtai>()
    var page = 1
    lateinit var adapter: SelfDongTaiAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = intent.getIntExtra("userId", 0)
        getUserPersonalInfoViewModel.getUserPersonalInfoLiveData.observe(this, Observer {
            val user = it.getOrNull()
            if (user != null){
                binding.username.text = user.username
                binding.introduction.text = user.introduction
                Glide.with(binding.avatar).load(ServiceCreator.BASE_URL + user.avatar).apply {
                    // 下载error时显示的图片
                    error(R.drawable.load_failed)
                }.into(binding.avatar)

                Glide.with(binding.meBackground).load(ServiceCreator.BASE_URL + user.background).apply {
                    // 下载error时显示的图片
                    error(R.drawable.load_failed)
                }.into(binding.meBackground)
            }
        })
        likeAndCancelViewModel.likeAndCancelLiveData.observe(this, Observer {
            val result = it.getOrNull()
            if (result !=null){
                refreshData()
            }
        })

        deleteDongtaiViewModel.deleteDongtaiResponseLiveData.observe(this, Observer {
            val deleteResponse = it.getOrNull()
            if (deleteResponse != null){
                SuperUiUtil.newToast(this,deleteResponse.msg)
                if (deleteResponse.code == 200){
                    dataList.clear()
                    refreshData()
                    adapter.notifyDataSetChanged()
                }
            }
        })

        getUserDongtaiViewModel.userDongtaiLiveData.observe(this, Observer {
            val dongtaiList = it.getOrNull()
            if (dongtaiList != null) {
                if (dongtaiList.isEmpty()){
                    page-=1
                }else{
                    if (page != 1){
                        dataList.addAll(dongtaiList)
                    }else{
                        dataList.clear()
                        dataList.addAll(dongtaiList)
                        processRefreshAndLoadMoreStatus(true, noMore = true)
                    }
                    adapter.notifyDataSetChanged()
                }
                processRefreshAndLoadMoreStatus(true, dongtaiList.isEmpty())
            }
        })
    }


    override fun initDatum() {
        super.initDatum()
        getUserPersonalInfoViewModel.setUserId(userId)
        getUserDongtaiViewModel.setUserId(userId,page)

        adapter = SelfDongTaiAdapter(this, dataList, DefaultPreferencesUtil.getUserId(), likeAndCancelViewModel, deleteDongtaiViewModel)
        binding.selfDongtaiRecycleView.adapter = adapter
        binding.selfDongtaiRecycleView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun initViews() {
        super.initViews()
        QMUIStatusBarHelper.translucent(this)
        binding.toolbar.setNavigationIcon(R.drawable.back_white)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun initListener() {
        super.initListener()
        binding.appBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) >= binding.appBar.totalScrollRange){
                binding.toolbar.setNavigationIcon(R.drawable.back)
            }else{
                binding.toolbar.setNavigationIcon(R.drawable.back_white)
            }
        }

        binding.refreshLayout.setOnRefreshListener {
            page = 1
            getUserDongtaiViewModel.setUserId(userId,page)
        }

        binding.refreshLayout.setOnLoadMoreListener {
            page+=1
            getUserDongtaiViewModel.setUserId(userId,page)
        }
    }

    private fun refreshData(){
        for (i in 1..page){
            getUserDongtaiViewModel.setUserId(userId,page)
        }
    }

    private fun processRefreshAndLoadMoreStatus(success:Boolean, noMore:Boolean = false){
        binding.refreshLayout.finishRefresh(500,success,false)
        // 这里的noMoreData是为loadMore方法服务的
        binding.refreshLayout.finishLoadMore(500,success,noMore)
    }

    companion object{
        fun startUserDongTai(context:Context, userId:Int){
            val intent = Intent(context, UserDongTaiActivity::class.java)
            intent.putExtra("userId", userId)
            context.startActivity(intent)
        }
    }
}