package com.example.greenplant.component.home


import android.os.Bundle
import android.util.Log
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivityPlantDockerBinding
import com.example.greenplant.entities.Msg
import com.example.greenplant.util.DefaultPreferencesUtil
import com.example.greenplant.util.SuperUiUtil
import com.example.greenplant.viewModel.AskDoctorViewModel
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

class PlantDockerActivity : BaseViewModelActivity<ActivityPlantDockerBinding>() {
    private val msgList = mutableListOf<Msg>()
    private val askDoctorViewModel by lazy {
        ViewModelProvider(this)[AskDoctorViewModel::class.java]
    }
    lateinit var adapter: MsgAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        askDoctorViewModel.askQuestionResponseLiveData.observe(this, Observer {
            val result = it.getOrNull()
            if (result != null && result.code == 200){
                msgList.add(Msg(result.data, Msg.RECEIVE))
                adapter.notifyDataSetChanged()
            }else{
                SuperUiUtil.newToast(this, "请检查网络后，再次询问")
            }
        })
    }

    override fun initViews() {
        super.initViews()
        QMUIStatusBarHelper.translucent(this)
        setCenterToolBar(binding.customToolbar, "智能医生")
    }

    override fun initDatum() {
        super.initDatum()
        adapter = MsgAdapter(this, msgList, DefaultPreferencesUtil.getUserAvatarUrl())
        binding.chatRecycleView.adapter = adapter
        binding.chatRecycleView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
    }

    override fun initListener() {
        super.initListener()
        binding.msgEdit.doOnTextChanged { text, _, _, _ ->
            binding.send.isEnabled = text?.length != 0
        }
        binding.send.setOnClickListener {
            val content = binding.msgEdit.text.toString()
            msgList.add(Msg(content, Msg.SEND))
            binding.msgEdit.setText("")
            adapter.notifyDataSetChanged()
            askDoctorViewModel.setQuestion(content)
        }
    }
}