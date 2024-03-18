package com.example.greenplant.component.communicate

import android.os.Bundle
import com.example.greenplant.databinding.FragmentCommunicateBinding
import com.example.greenplant.fragment.BaseViewModelFragment


class CommunicateFragment : BaseViewModelFragment<FragmentCommunicateBinding>() {

    companion object{
        fun newInstance(): CommunicateFragment {
            val args = Bundle()

            val fragment = CommunicateFragment()
            fragment.arguments = args
            return fragment
        }
    }
}