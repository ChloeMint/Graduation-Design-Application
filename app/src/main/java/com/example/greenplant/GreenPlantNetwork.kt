package com.example.greenplant

import com.example.greenplant.network.UserService
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object GreenPlantNetwork {
    private val userService = ServiceCreator.create<UserService>()

    suspend fun login(requestBody: RequestBody) = userService.login(requestBody).await()

    suspend fun getUserInfo() = userService.getUserInfo().await()

    suspend fun sendMessage(requestBody: RequestBody) = userService.sendMessage(requestBody).await()

    suspend fun forgetPassword(requestBody: RequestBody) = userService.forgetPassword(requestBody).await()

    suspend fun register(requestBody: RequestBody) = userService.register(requestBody).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine {
            enqueue(object : Callback<T>{
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) it.resume(body)
                    else it.resumeWithException(
                        RuntimeException("response body is null"))
                }
                override fun onFailure(call: Call<T>, t: Throwable) {
                    it.resumeWithException(t)
                }

            })
        }
    }
}