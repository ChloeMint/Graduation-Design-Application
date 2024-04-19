package com.example.greenplant.component.me

import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivityNoteBinding
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

class NoteActivity : BaseViewModelActivity<ActivityNoteBinding>() {
    override fun initViews() {
        super.initViews()
        QMUIStatusBarHelper.translucent(this)

    }
}