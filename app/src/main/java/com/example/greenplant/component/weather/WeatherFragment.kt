package com.example.greenplant.component.weather

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.greenplant.databinding.FragmentWeatherBinding
import com.example.greenplant.entities.Position
import com.example.greenplant.fragment.BaseViewModelFragment
import com.example.greenplant.util.SuperUiUtil
import com.example.greenplant.viewModel.ProvinceViewModel
import com.permissionx.guolindev.PermissionX
import com.qmuiteam.qmui.util.QMUIStatusBarHelper


class WeatherFragment : BaseViewModelFragment<FragmentWeatherBinding>() {
    lateinit var locationManager:LocationManager
    lateinit var locationListener: LocationListener
    private val provinceViewModel by lazy {
        ViewModelProvider(requireActivity())[ProvinceViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        provinceViewModel.getProvinceResponseLiveData.observe(this, Observer {
            val provinceResponse = it.getOrNull()
            if (provinceResponse != null){
                binding.where.text = provinceResponse.data.city
            }
        })
    }
    override fun initDatum() {
        super.initDatum()
        requestPermission()
    }

    private fun requestPermission() {
        PermissionX.init(this).permissions(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ).request { allGranted, _, _ ->
            if (allGranted) {
                preNext()
            } else {
                SuperUiUtil.newToast(requireContext(), "该功能只能授权权限后方可使用")
            }
        }
    }

    private fun preNext(){
        locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                // 处理位置变化，例如更新UI
                val latitude = location.latitude
                val longitude = location.longitude
                // 使用latitude和longitude
//                provinceViewModel.setPositionLiveData(Position(latitude,longitude))   // 根据定位获取地区，毕设展示的时候需要打开注解

            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                // 可以选择性地实现此方法以处理状态变化
            }

            override fun onProviderEnabled(provider: String) {
                // 可以选择性地实现此方法以处理位置提供程序的启用
            }

            override fun onProviderDisabled(provider: String) {
                // 可以选择性地实现此方法以处理位置提供程序的禁用
            }
        }

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                return
            }

            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                86400000L,
                100f,
                locationListener
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 停止接收位置更新
        locationManager.removeUpdates(locationListener)
    }

    companion object{
        const val TAG = "WeatherFragment"
        fun newInstance(): WeatherFragment {
            val args = Bundle()

            val fragment = WeatherFragment()
            fragment.arguments = args
            return fragment
        }
    }
}