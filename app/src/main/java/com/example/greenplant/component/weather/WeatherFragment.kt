package com.example.greenplant.component.weather

import android.os.Bundle
import com.example.greenplant.databinding.FragmentWeatherBinding
import com.example.greenplant.fragment.BaseViewModelFragment
import com.qmuiteam.qmui.util.QMUIStatusBarHelper


class WeatherFragment : BaseViewModelFragment<FragmentWeatherBinding>() {

    companion object{
        fun newInstance(): WeatherFragment {
            val args = Bundle()

            val fragment = WeatherFragment()
            fragment.arguments = args
            return fragment
        }
    }
}