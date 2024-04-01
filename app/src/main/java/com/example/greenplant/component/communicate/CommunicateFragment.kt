package com.example.greenplant.component.communicate

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


class CommunicateFragment : BaseViewModelFragment<FragmentCommunicateBinding>() {
    private val dongtaiViewModel by lazy {
        ViewModelProvider(this)[DongtaiViewModel::class.java]
    }
    private val likeAndCancelViewModel by lazy {
        ViewModelProvider(this)[LikeAndCancelViewModel::class.java]
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
                }
                processRefreshAndLoadMoreStatus(true,dongtaiList.isEmpty())
            }
//            Log.d("TAG", "$dataList")
        })

        likeAndCancelViewModel.likeAndCancelLiveData.observe(this, Observer {
            val result = it.getOrNull()
//            Log.d("CommunicateFragment", "$result")
            if (result !=null){
                // 不确定这里之后点赞刷新新数据列表会不会出bug
                for(i in 1..page){
                    dongtaiViewModel.setPage(page)
                }
                adapter.notifyDataSetChanged()
            }
        })

    }

    override fun initDatum() {
        super.initDatum()
        adapter = DongtaiAdapter(requireContext(), dataList, likeAndCancelViewModel)
        binding.dongtaiRecycleView.layoutManager = LinearLayoutManager(requireContext(),
            RecyclerView.VERTICAL,false)
        binding.dongtaiRecycleView.adapter = adapter
    }

    override fun initView() {
        super.initView()
        binding.customToolbar.toolBarTitle.text = "我的动态圈"
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
    }

    private fun processRefreshAndLoadMoreStatus(success:Boolean, noMore:Boolean = false){
        binding.refreshLayout.finishRefresh(500,success,false)
        // 这里的noMoreData是为loadMore方法服务的
        binding.refreshLayout.finishLoadMore(500,success,noMore)
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