package com.example.greenplant.component.communicate

import android.os.Bundle
import com.example.greenplant.databinding.FragmentCommunicateBinding
import com.example.greenplant.fragment.BaseViewModelFragment
import com.qmuiteam.qmui.util.QMUIStatusBarHelper


class CommunicateFragment : BaseViewModelFragment<FragmentCommunicateBinding>() {
    override fun initView() {
        super.initView()
        binding.customToolbar.toolBarTitle.text = "我的动态圈"
    }

    override fun initListener() {
        super.initListener()
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