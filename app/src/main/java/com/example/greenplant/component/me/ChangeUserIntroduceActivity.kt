package com.example.greenplant.component.me

import android.content.Context
import android.content.Intent
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.databinding.ActivityChangeUserIntroduceBinding

class ChangeUserIntroduceActivity : BaseViewModelActivity<ActivityChangeUserIntroduceBinding>() {

    companion object{
        fun startChangeUserIntroduceActivity(context: Context, introduction:String): Intent{
            val intent = Intent(context, ChangeUserIntroduceActivity::class.java)
            intent.putExtra("introduction", introduction)
            return intent
        }
    }
}