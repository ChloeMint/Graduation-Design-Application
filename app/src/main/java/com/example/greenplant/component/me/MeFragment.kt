package com.example.greenplant.component.me

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.greenplant.R
import com.example.greenplant.ServiceCreator
import com.example.greenplant.component.home.HomeFragment
import com.example.greenplant.databinding.FragmentMeBinding
import com.example.greenplant.fragment.BaseViewModelFragment
import com.example.greenplant.viewModel.UserInfoViewModel


class MeFragment : BaseViewModelFragment<FragmentMeBinding> (){
    private val userInfoViewModel by lazy {
        ViewModelProvider(requireActivity())[UserInfoViewModel::class.java]
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

    }
    companion object{
        fun newInstance(): MeFragment {
            val args = Bundle()

            val fragment = MeFragment()
            fragment.arguments = args
            return fragment
        }
    }

}