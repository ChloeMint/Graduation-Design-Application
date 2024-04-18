package com.example.greenplant.component.me

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.CollapsibleActionView
import android.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.greenplant.R
import com.example.greenplant.ServiceCreator
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.component.communicate.GlideEngine
import com.example.greenplant.databinding.ActivitySelfDongtaiBinding
import com.example.greenplant.entities.Dongtai
import com.example.greenplant.util.SuperUiUtil
import com.example.greenplant.viewModel.*
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.permissionx.guolindev.PermissionX
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import kotlin.math.abs
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

    private val changeBackgroundViewModel by lazy {
        ViewModelProvider(this)[ChangeBackgroundViewModel::class.java]
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

        userInfoViewModel.userInfoLiveData.observe(this, Observer {
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

        changeBackgroundViewModel.changeBackgroundResponseLiveData.observe(this, Observer {
            val result = it.getOrNull()
            if (result != null){
                SuperUiUtil.newToast(this, result.msg)
            }
        })
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
        binding.refreshLayout.setOnRefreshListener {
            page = 1
            getUserDongtaiViewModel.setUserId(userId,page)
        }

        binding.refreshLayout.setOnLoadMoreListener {
            page+=1
            getUserDongtaiViewModel.setUserId(userId,page)
        }

        binding.appBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) >= binding.appBar.totalScrollRange){
                binding.toolbar.setNavigationIcon(R.drawable.back)
            }else{
                binding.toolbar.setNavigationIcon(R.drawable.back_white)
            }
        }

        binding.meBackground.setOnClickListener {
            val popupMenu = PopupMenu(this,it)
            popupMenu.menuInflater.inflate(R.menu.select_dongtai_background,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {menuItem->
                when (menuItem.itemId){
                    R.id.selectBackground ->{
                        requestPermission()
                        return@setOnMenuItemClickListener true
                    }else ->{
                    return@setOnMenuItemClickListener true
                }
                }
            }
            popupMenu.show()
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

    private fun refreshData(){
        for (i in 1..page){
            getUserDongtaiViewModel.setUserId(userId,page)
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            PermissionX.init(this).permissions(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_IMAGES,
            )
        } else {
            PermissionX.init(this).permissions(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }.request { allGranted, _, _ ->
            if (allGranted) {
                launchAlbum()
            } else {
                SuperUiUtil.newToast(this, "该功能只能授权权限后方可使用")
            }
        }
    }
    private fun launchAlbum() {
        PictureSelector.create(this)
            .openGallery(SelectMimeType.ofImage())
            .setImageEngine(GlideEngine.createGlideEngine())
            .setMaxSelectNum(1)
            .isPreviewImage(true)
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: ArrayList<LocalMedia>) {
                    if (result.isNotEmpty()){
                        val uri = Uri.parse(result[0].path)
                        binding.meBackground.setImageURI(uri)
                        changeBackgroundViewModel.setBackground(result[0])
                    }
                }
                override fun onCancel() {
                    SuperUiUtil.newToast(this@SelfDongtaiActivity,"取消选择")
                }
            })

    }

    companion object{
        const val TAG = "SelfDongtaiActivity"
        fun startSelfDongtaiActivity(context:Context, userId:Int){
            val intent = Intent(context,SelfDongtaiActivity::class.java)
            intent.putExtra("userId", userId)
            context.startActivity(intent)
        }
    }
}