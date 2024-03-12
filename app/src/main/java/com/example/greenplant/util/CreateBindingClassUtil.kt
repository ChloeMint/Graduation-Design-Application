// 工具类 反射
package com.example.greenplant.util

import android.view.LayoutInflater
import java.lang.reflect.ParameterizedType

object CreateBindingClassUtil {
    // 获取泛型参数对象
    fun <VB> newViewBinding(layoutInflater: LayoutInflater,clazz: Class<*>):VB?{
        return try {
            val type : ParameterizedType = try {
                clazz.genericSuperclass as ParameterizedType
            }catch (e:ClassCastException){
                clazz.superclass.genericSuperclass as ParameterizedType
            }
            // type.actualTypeArguments[0]:ViewBinding
            val classVB = type.actualTypeArguments[0] as Class<VB>

            // 获取inflate方法
            val inflateMethod = classVB.getMethod("inflate",LayoutInflater::class.java)
            inflateMethod.invoke(null,layoutInflater) as VB
        }catch (e:Exception){
            e.printStackTrace()
            throw RuntimeException(e)
        }

    }
}