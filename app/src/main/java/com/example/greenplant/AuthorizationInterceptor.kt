package com.example.greenplant

import com.example.greenplant.util.DefaultPreferencesUtil
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = DefaultPreferencesUtil.getToken()

        val requestBuilder = originalRequest.newBuilder().header("Authorization",token).header("appCode","q1ktbQZJai15ebB3AB9DZWs59WntJ79l")
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
