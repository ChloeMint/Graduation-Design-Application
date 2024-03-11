package com.example.greenplant.util

import android.content.Context
import android.content.res.Configuration
import android.text.method.LinkMovementMethod
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.TextView

object SuperUiUtil {
    fun isDark(context: Context):Boolean{
        return context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

    fun getScreenWidth(context:Context):Int{
        // 获取windows管理器
        val vm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        // 创建显示对象
        val outDisplayMetrics = DisplayMetrics()

        // 创建默认显示对象
        vm.defaultDisplay.getMetrics(outDisplayMetrics)

        // 返回屏幕宽度
        return outDisplayMetrics.widthPixels
    }

    fun getScreenHeight(context:Context):Int{
        // 获取windows管理器
        val vm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        // 创建显示对象
        val outDisplayMetrics = DisplayMetrics()

        // 创建默认显示对象
        vm.defaultDisplay.getMetrics(outDisplayMetrics)

        // 返回屏幕宽度
        return outDisplayMetrics.heightPixels
    }

    fun setLinkColor(view: TextView, color: Int){
        view.movementMethod = LinkMovementMethod.getInstance()
        view.setLinkTextColor(color)
    }
}