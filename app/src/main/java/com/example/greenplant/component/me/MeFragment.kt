package com.example.greenplant.component.me

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.greenplant.R
import com.example.greenplant.ServiceCreator
import com.example.greenplant.component.home.HomeFragment
import com.example.greenplant.databinding.FragmentMeBinding
import com.example.greenplant.entities.MeItem
import com.example.greenplant.fragment.BaseViewModelFragment
import com.example.greenplant.util.DefaultPreferencesUtil
import com.example.greenplant.viewModel.UserInfoViewModel


class MeFragment : BaseViewModelFragment<FragmentMeBinding> (){
    private val userInfoViewModel by lazy {
        ViewModelProvider(requireActivity())[UserInfoViewModel::class.java]
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){
            userInfoViewModel.setIsGettingUserInfo()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userInfoViewModel.userInfoLiveData.observe(this, Observer {
            val user = it.getOrNull()
            if (user != null){
                Glide.with(binding.avatar).load(ServiceCreator.BASE_URL + user.avatar).apply {
                    // 下载error时显示的图片
                    error(R.drawable.load_failed)
                }.into(binding.avatar)

                binding.username.text = user.username
                binding.introduction.text = user.introduction

            }
        })
    }

    override fun initDatum() {
        super.initDatum()
//        binding.cardView.post {
//            Log.d("MeFragment_cardView", "${binding.cardView.measuredWidth/requireActivity().resources.displayMetrics.density}")
//        }
        userInfoViewModel.setIsGettingUserInfo()

        val firstAdapter = MeItemAdapter(requireContext(), MeFragment.dataList, object : ItemOnclickListener{
            override fun itemOnclickListener(position: Int) {
                when(position){
                    0 -> {
                        SelfDongtaiActivity.startSelfDongtaiActivity(requireContext(),DefaultPreferencesUtil.getUserId())
                    }
                    1 -> {
                        val intent = Intent(requireContext(), NoteActivity::class.java)
                        startActivity(intent)
                    }
                }
            }

        })

        val secondAdapter = MeItemAdapter(requireContext(), dataList2, object : ItemOnclickListener{
            override fun itemOnclickListener(position: Int) {
                when(position){
                    0->{
                        val intent = Intent(requireContext(), SettingActivity::class.java)
                        launcher.launch(intent)
                    }

                    1->{
                        Log.d(TAG, "用户反馈")
                    }
                }
            }

        })
        binding.meFirstRecycleView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.meFirstRecycleView.adapter = firstAdapter

        binding.meSecondRecycleView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.meSecondRecycleView.adapter = secondAdapter


    }
    companion object{
        const val TAG = "MeFragment"

        val dataList = listOf<MeItem>(
            MeItem(R.drawable.dongtai,"已发布动态"),
            MeItem(R.drawable.note,"植物笔记清单"))

        val dataList2 = listOf<MeItem>(
            MeItem(R.drawable.setting,"设置"),
            MeItem(R.drawable.user_feedback,"用户反馈")
        )
        fun newInstance(): MeFragment {
            val args = Bundle()

            val fragment = MeFragment()
            fragment.arguments = args
            return fragment
        }
    }

}