package com.example.greenplant

import android.util.Log
import androidx.lifecycle.liveData
import com.example.greenplant.util.DefaultPreferencesUtil
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.w3c.dom.Text
import kotlin.coroutines.CoroutineContext

object Repository {
    fun login(requestBody: RequestBody) = fire(Dispatchers.IO) {
        val loginResponse = GreenPlantNetwork.login(requestBody)
        if (loginResponse.code != 500){
            val token = loginResponse.data
            Result.success(token)
        }else{
            Result.failure(RuntimeException(loginResponse.msg))
        }
    }

    fun getUserInfo() = fire(Dispatchers.IO){
        val userInfoResponse = GreenPlantNetwork.getUserInfo()
        if (userInfoResponse.code != 500){
            Result.success(userInfoResponse.data)
        }else{
            Result.failure(java.lang.RuntimeException(userInfoResponse.msg))
        }
    }

    fun sendMessage(requestBody: RequestBody) = fire(Dispatchers.IO){
        val registerResponse = GreenPlantNetwork.sendMessage(requestBody)
        if (registerResponse.code != 500){
            Result.success(registerResponse.msg)
        }else{
            Result.failure(java.lang.RuntimeException(registerResponse.msg))
        }
    }

    fun forgetPassword(requestBody: RequestBody) = fire(Dispatchers.IO){
        val forgetPasswordResponse = GreenPlantNetwork.forgetPassword(requestBody)
        if (forgetPasswordResponse.code != 500){
            Result.success(forgetPasswordResponse.msg)
        }else{
            Result.failure(java.lang.RuntimeException(forgetPasswordResponse.msg))
        }
    }

    fun register(requestBody: RequestBody) = fire(Dispatchers.IO){
        val registerResponse = GreenPlantNetwork.register(requestBody)
        if (registerResponse.code != 500){
            Result.success(registerResponse.msg)
        }else{
            Result.failure(java.lang.RuntimeException(registerResponse.msg))
        }
    }

    fun getBaike(page:Int) = fire(Dispatchers.IO){
        val baikeResponse = GreenPlantNetwork.getBaike(page)
        if (baikeResponse.code != 500){
            Result.success(baikeResponse.data)
        }else{
            Result.failure(java.lang.RuntimeException(baikeResponse.msg))
        }
    }

    fun getBaikeDetail(plantId:Int) = fire(Dispatchers.IO){
        val baikeDetailResponse = GreenPlantNetwork.getBaikeDetail(plantId)
        if (baikeDetailResponse.code != 500){
            Result.success(baikeDetailResponse.data)
        }else{
            Result.failure(java.lang.RuntimeException(baikeDetailResponse.msg))
        }
    }

    fun getBaikeSearch(plantName:String) = fire(Dispatchers.IO){
        val baikeSearchResponse = GreenPlantNetwork.getBaikeSearch(plantName)
        if (baikeSearchResponse.code != 500){
            Result.success(baikeSearchResponse.data)
        }else{
            Result.failure(java.lang.RuntimeException(baikeSearchResponse.msg))
        }
    }

    fun getBaiduToken()= fire(Dispatchers.IO){
        val baiDuPlatformTokenResponse = GreenPlantNetwork.getBaiduToken()
        if (baiDuPlatformTokenResponse.access_token != null){
            Result.success(baiDuPlatformTokenResponse)
        }else{
            Result.failure(java.lang.RuntimeException("token get failed"))
        }
    }

    fun getBaiduRecognition(accessToken:String, image:String) = fire(Dispatchers.IO){
        val baiduPlantRecognitionResponse = GreenPlantNetwork.getBaiduRecognition(accessToken, image)
        Log.d("Repository", "$baiduPlantRecognitionResponse")
        Result.success(baiduPlantRecognitionResponse.result)
    }

    fun getDongTai(page: Int) = fire(Dispatchers.IO){
        val dongtaiResponse = GreenPlantNetwork.getDongTai(page)
        if (dongtaiResponse.code != 500){
            Result.success(dongtaiResponse.data)
        }else{
            Result.failure(RuntimeException(dongtaiResponse.msg))
        }
    }

    fun likeAndCancel(dongtaiId:Int) = fire(Dispatchers.IO){
        val likeAndCancelResponse = GreenPlantNetwork.likeAndCancel(dongtaiId)
        if (likeAndCancelResponse.code != 500){
            Result.success(likeAndCancelResponse.msg)
        }else{
            Result.failure(RuntimeException(likeAndCancelResponse.msg))
        }
    }

    fun publishComment(dongtaiId: Int, requestBody: RequestBody) = fire(Dispatchers.IO){
        val publishCommentResponse = GreenPlantNetwork.publishComment(dongtaiId, requestBody)
        if (publishCommentResponse.code != 500){
            Result.success(publishCommentResponse)
        }else{
            Result.failure(RuntimeException(publishCommentResponse.msg))
        }
    }

    fun publishDongtaiImage(text:RequestBody, images:List<MultipartBody.Part>) = fire(Dispatchers.IO){
        val  publishDongtaiImageResponse = GreenPlantNetwork.publishDongtaiImage(text, images)
        if (publishDongtaiImageResponse.code != 500){
            Result.success(publishDongtaiImageResponse.msg)
        }else{
            Result.failure(RuntimeException(publishDongtaiImageResponse.msg))
        }
    }

    fun publishDongtaiWithVideo(text: RequestBody, video:MultipartBody.Part) = fire(Dispatchers.IO){
        val publishDongtaiWithVideoResponse = GreenPlantNetwork.publishDongtaiWithVideo(text, video)
        if (publishDongtaiWithVideoResponse.code != 500){
            Result.success(publishDongtaiWithVideoResponse.msg)
        }else{
            Result.failure(RuntimeException(publishDongtaiWithVideoResponse.msg))
        }
    }

    fun getProvince(lat:Double,lng:Double) = fire(Dispatchers.IO){
        val provinceResponse = GreenPlantNetwork.getProvince(lat, lng)

        if (provinceResponse.code != 500){
            Result.success(provinceResponse)
        }else{
            Result.failure(RuntimeException(provinceResponse.message))
        }
    }

    fun publishDongtaiText(text: RequestBody) = fire(Dispatchers.IO){
        val publishDongtaiTextResponse = GreenPlantNetwork.publishDongtaiText(text)
        if (publishDongtaiTextResponse.code != 500){
            Result.success(publishDongtaiTextResponse.msg)
        }else{
            Result.failure(RuntimeException(publishDongtaiTextResponse.msg))
        }
    }

    fun getRealtime(token:String, lng: Double, lat: Double) = fire(Dispatchers.IO){
        val realtimeResponse = GreenPlantNetwork.getRealtime(token, lng, lat)
        if (realtimeResponse.status == "ok"){
            Result.success(realtimeResponse)
        }else{
            Result.failure(RuntimeException(realtimeResponse.status))
        }
    }

    fun getNearByDay(token:String, lng: Double, lat: Double, dailysteps:Int) = fire(Dispatchers.IO){
        val nearByDayResponse = GreenPlantNetwork.getNearByDay(token, lng, lat, dailysteps)
        if (nearByDayResponse.status == "ok"){
            Result.success(nearByDayResponse)
        }else{
            Result.failure(RuntimeException(nearByDayResponse.status))
        }
    }

    fun deleteDongtai(dongtaiId: Int) = fire(Dispatchers.IO){
        val deleteDongtaiResponse = GreenPlantNetwork.deleteDongtai(dongtaiId)
        if (deleteDongtaiResponse.code != 500){
            Result.success(deleteDongtaiResponse)
        }else{
            Result.failure(RuntimeException(deleteDongtaiResponse.status))
        }
    }

    fun getUserDongtai(userId:Int, page: Int) = fire(Dispatchers.IO){
        val getUserDongtaiResponse = GreenPlantNetwork.getUserDongtai(userId,page)
        if (getUserDongtaiResponse.code != 500){
            Result.success(getUserDongtaiResponse.data)
        }else{
            Result.failure(RuntimeException(getUserDongtaiResponse.status))
        }
    }

    fun changeBackground(file: MultipartBody.Part) = fire(Dispatchers.IO){
        val changeBackgroundResponse = GreenPlantNetwork.changeBackground(file)
        if (changeBackgroundResponse.code != 500){
            Result.success(changeBackgroundResponse)
        }else{
            Result.failure(RuntimeException(changeBackgroundResponse.status))
        }
    }

    fun getNotes() = fire(Dispatchers.IO){
        val notesResponse = GreenPlantNetwork.getNotes()
        if (notesResponse.code != 500){
            Result.success(notesResponse.data)
        }else{
            Result.failure(RuntimeException(notesResponse.status))
        }
    }

    fun deleteNote(noteId: Int) = fire(Dispatchers.IO){
        val deleteResponse = GreenPlantNetwork.deleteNote(noteId)
        if (deleteResponse.code != 500){
            Result.success(deleteResponse)
        }else{
            Result.failure(RuntimeException(deleteResponse.status))
        }
    }

    fun getNoteDetail(noteId: Int) = fire(Dispatchers.IO){
        val noteResponse = GreenPlantNetwork.getNoteDetail(noteId)
        if (noteResponse.code != 500){
            Result.success(noteResponse.data)
        }else{
            Result.failure(RuntimeException(noteResponse.status))
        }
    }

    fun editNote(noteId: Int, requestBody: RequestBody) = fire(Dispatchers.IO){
        val editResponse = GreenPlantNetwork.editNote(noteId, requestBody)
        if (editResponse.code != 500){
            Result.success(editResponse)
        }else{
            Result.failure(RuntimeException(editResponse.status))
        }
    }

    fun createNote(requestBody: RequestBody) = fire(Dispatchers.IO){
        val createNoteResponse = GreenPlantNetwork.createNote(requestBody)
        if (createNoteResponse.code != 500){
            Result.success(createNoteResponse)
        }else{
            Result.failure(RuntimeException(createNoteResponse.status))
        }
    }

    fun changeUserAvatar(image: MultipartBody.Part) = fire(Dispatchers.IO){
        val changeUserAvatarResponse = GreenPlantNetwork.changeUserAvatar(image)
        if (changeUserAvatarResponse.code != 500){
            Result.success(changeUserAvatarResponse)
        }else{
            Result.failure(RuntimeException(changeUserAvatarResponse.status))
        }
    }

    fun changeUsername(requestBody: RequestBody) = fire(Dispatchers.IO){
        val changeUsernameResponse = GreenPlantNetwork.changeUsername(requestBody)
        if (changeUsernameResponse.code != 500){
            Result.success(changeUsernameResponse)
        }else{
            Result.failure(RuntimeException(changeUsernameResponse.status))
        }
    }

    fun changeUserIntroduce(requestBody: RequestBody) = fire(Dispatchers.IO){
        val changeUserIntroduceResponse = GreenPlantNetwork.changeUserIntroduce(requestBody)
        if (changeUserIntroduceResponse.code != 500){
            Result.success(changeUserIntroduceResponse)
        }else{
            Result.failure(RuntimeException(changeUserIntroduceResponse.status))
        }
    }

    fun changeUserPhoneNUmber(requestBody: RequestBody) = fire(Dispatchers.IO){
        val changeUserPhoneNumberResponse = GreenPlantNetwork.changeUserPhoneNumber(requestBody)
        if (changeUserPhoneNumberResponse.code != 500){
            Result.success(changeUserPhoneNumberResponse)
        }else{
            Result.failure(RuntimeException(changeUserPhoneNumberResponse.status))
        }
    }

    fun changeUserPassword(requestBody: RequestBody) = fire(Dispatchers.IO){
        val changeUserPasswordResponse = GreenPlantNetwork.changeUserPassword(requestBody)
        if (changeUserPasswordResponse.code != 500){
            Result.success(changeUserPasswordResponse)
        }else{
            Result.failure(RuntimeException(changeUserPasswordResponse.status))
        }
    }

    fun askDoctor(question:String) = fire(Dispatchers.IO){
        val askDoctorResponse = GreenPlantNetwork.askDoctor(question)
        if (askDoctorResponse.code == 200){
            Result.success(askDoctorResponse)
        }else{
            Result.failure(RuntimeException(askDoctorResponse.status))
        }
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}
