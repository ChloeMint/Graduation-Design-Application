package com.example.greenplant.component.communicate

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greenplant.databinding.FragmentCommunicateBinding
import com.example.greenplant.entities.Dongtai
import com.example.greenplant.fragment.BaseViewModelFragment
import com.example.greenplant.viewModel.DongtaiViewModel
import com.example.greenplant.viewModel.LikeAndCancelViewModel
import com.example.greenplant.viewModel.PublishCommentViewModel


class CommunicateFragment : BaseViewModelFragment<FragmentCommunicateBinding>() {
    private val dongtaiViewModel by lazy {
        ViewModelProvider(this)[DongtaiViewModel::class.java]
    }
    private val likeAndCancelViewModel by lazy {
        ViewModelProvider(this)[LikeAndCancelViewModel::class.java]
    }
    private val publishCommentViewModel by lazy {
        ViewModelProvider(this)[PublishCommentViewModel::class.java]
    }

    private  val dataList = mutableListOf<Dongtai>()
    private var page = 1
    private lateinit var adapter: DongtaiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dongtaiViewModel.dongtaiResponse.observe(this, Observer {
            val dongtaiList = it.getOrNull()
            if (dongtaiList != null){
                if (dongtaiList.isNotEmpty()){
                    if (page != 1){
                        dataList.addAll(dongtaiList)
                    }else{
                        dataList.clear()
                        dataList.addAll(dongtaiList)
                        processRefreshAndLoadMoreStatus(true, noMore = false)
                    }
                    adapter.notifyDataSetChanged()
                }else{
                    page -= 1   // 这里可能还会出现一种bug，就是新发布的不满10条，然后最后几条会重新添加，应该不会有这个问题
                }
                processRefreshAndLoadMoreStatus(true,dongtaiList.isEmpty())
            }
//            Log.d("TAG", "$dataList")
        })

        likeAndCancelViewModel.likeAndCancelLiveData.observe(this, Observer {
            val result = it.getOrNull()
//            Log.d("CommunicateFragment", "$result")
            if (result !=null){
                refreshData()
            }
        })

        publishCommentViewModel.publishCommentLiveData.observe(this, Observer {
            val publishCommentResponse = it.getOrNull()
            if (publishCommentResponse != null){
                if (publishCommentResponse.code == 200){
                    refreshData()
                }
            }
        })

    }

    override fun initDatum() {
        super.initDatum()
        adapter = DongtaiAdapter(requireContext(), dataList, likeAndCancelViewModel, publishCommentViewModel)
        binding.dongtaiRecycleView.layoutManager = LinearLayoutManager(requireContext(),
            RecyclerView.VERTICAL,false)
        binding.dongtaiRecycleView.adapter = adapter
    }

    override fun initView() {
        super.initView()
        binding.customToolbar.toolBarTitle.text = "动态圈"
        dongtaiViewModel.setPage(1)
    }

    override fun initListener() {
        super.initListener()

        binding.refreshLayout.setOnRefreshListener{
            page = 1
            dongtaiViewModel.setPage(1)

        }

        binding.refreshLayout.setOnLoadMoreListener {
            page+=1
            dongtaiViewModel.setPage(page)
        }

        binding.customToolbar.publish.setOnClickListener {
            val intent = Intent(requireContext(), PublishDongTaiActivity::class.java)
            startActivity(intent)
        }
    }

    private fun processRefreshAndLoadMoreStatus(success:Boolean, noMore:Boolean = false){
        binding.refreshLayout.finishRefresh(500,success,false)
        // 这里的noMoreData是为loadMore方法服务的
        binding.refreshLayout.finishLoadMore(500,success,noMore)
    }

    private fun refreshData(){
        for(i in 1..page){
            dongtaiViewModel.setPage(page)
        }
    }

    companion object{
        fun newInstance(): CommunicateFragment {
            val args = Bundle()

            val fragment = CommunicateFragment()
            fragment.arguments = args
            return fragment
        }
    }
}