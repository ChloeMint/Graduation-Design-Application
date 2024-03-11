package com.example.greenplant.fragment

import android.view.View
import androidx.annotation.IdRes

abstract class BaseCommonDialogFragment : BaseDialogFragment() {
    fun <T : View> findViewById(@IdRes id: Int): T {
        return requireView().findViewById(id)
    }
}