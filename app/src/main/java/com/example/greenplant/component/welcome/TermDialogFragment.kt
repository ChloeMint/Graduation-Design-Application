package com.example.greenplant.component.welcome

import android.text.Html
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.FragmentManager
import com.example.greenplant.R
import com.example.greenplant.databinding.FragmentTermDialogBinding
import com.example.greenplant.fragment.BaseDialogViewModelFragment
import com.example.greenplant.util.SuperProgressUtil
import com.example.greenplant.util.SuperUiUtil

class TermDialogFragment : BaseDialogViewModelFragment<FragmentTermDialogBinding>() {
    lateinit var agreeClickListener : OnClickListener

    override fun initViews() {
        super.initViews()
        isCancelable = false
    }

    override fun initDatum() {
        super.initDatum()
        binding.serviceContent.text = Html.fromHtml(getString(R.string.service_content))
        SuperUiUtil.setLinkColor(binding.serviceContent, getColor(requireContext() ,R.color.gray))
    }

    override fun initListener() {
        super.initListener()
        binding.agree.setOnClickListener {
            dismiss()
            agreeClickListener.onClick(it)
        }
        binding.disagree.setOnClickListener {
            dismiss()
            SuperProgressUtil.killApp()
        }
    }


    override fun onResume() {
        super.onResume()
        val params:ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = ((SuperUiUtil.getScreenWidth(requireContext())*0.9).toInt()) // 这里的requireContext是Return the Context this fragment is currently associated with.
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    companion object{
        private const val TAG = "TermDialogFragment"

        fun show(supportFragmentManager:FragmentManager, agreeClickListener: OnClickListener){
            val termDialogFragment = TermDialogFragment()
            termDialogFragment.agreeClickListener = agreeClickListener
            termDialogFragment.show(supportFragmentManager, TAG)
        }
    }

}