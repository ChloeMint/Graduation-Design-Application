package com.example.greenplant.component.welcome

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.FragmentManager
import com.example.greenplant.R
import com.example.greenplant.fragment.BaseCommonDialogFragment
import com.example.greenplant.fragment.BaseDialogFragment
import com.example.greenplant.util.SuperProgressUtil
import com.example.greenplant.util.SuperUiUtil

class TermDialogFragment : BaseCommonDialogFragment() {
    lateinit var serviceTextView: TextView
    lateinit var agreeClickListener: OnClickListener
    lateinit var agreeButton: Button
    lateinit var disagreeButton: Button

    override fun initViews() {
        super.initViews()
        isCancelable = false
        serviceTextView = findViewById(R.id.serviceContent)
        agreeButton = findViewById(R.id.agree)
        disagreeButton = findViewById(R.id.disagree)

    }

    override fun initDatum() {
        super.initDatum()
        serviceTextView.text = Html.fromHtml(getString(R.string.service_content))
        SuperUiUtil.setLinkColor(serviceTextView, getColor(requireContext() ,R.color.gray))
    }

    override fun initListener() {
        super.initListener()
        agreeButton.setOnClickListener {
            dismiss()
            agreeClickListener.onClick(it)
        }
        disagreeButton.setOnClickListener {
            dismiss()
            SuperProgressUtil.killApp()
        }
    }

    override fun getLayoutView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_term_dialog, container, false)
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