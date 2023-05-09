package com.tdpc.toko99.module.store

import android.util.Log
import androidx.lifecycle.ViewModel
import com.tdpc.toko99.BuildConfig
import com.tdpc.toko99.core.data.remote.response.ResponseBarang
import com.tdpc.toko99.core.data.remote.retrofit.ApiConfig
import com.tdpc.toko99.core.domain.usecase.StoreUseCase
import com.tdpc.toko99.core.utils.Event
import com.tdpc.toko99.core.utils.reduceFileImage
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class StoreViewModel(private val storeUseCase: StoreUseCase) : ViewModel() {

    fun uploadImage(setDescription: String, getFile: File?) {
        if (getFile != null) {
            val file = reduceFileImage(getFile)
            val description = setDescription.toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "image", file.name, requestImageFile
            )
            val service = ApiConfig.provideApiService().storeBarang(BuildConfig.API_KEY, imageMultipart, description)
            service.enqueue(object : Callback<ResponseBarang> {
                override fun onResponse(
                    call: Call<ResponseBarang>, response: Response<ResponseBarang>
                ) {
//                    _isLoading.value = true
                    if (response.isSuccessful) {
//                        _isLoading.value = false
                        val responseBody = response.body()
                        if (responseBody != null && !responseBody.error) {
//                            _toast.value = Event("Upload Success")
//                            _result.value = Event(true)
                        } else {
//                            _isLoading.value = false
                            Log.e(TAG, "onFailure: ${response.body()?.message}")
                        }
                    } else {
//                        _isLoading.value = false
//                        _toast.value = Event("Incomplete Form")
                        Log.e(TAG, "onFailure: ${response.body()?.message}")
                    }
                }

                override fun onFailure(call: Call<ResponseBarang>, t: Throwable) {
//                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
        } else {
//            _isLoading.value = false
//            _toast.value = Event("Please Insert the Picture First")
        }
    }

    companion object {
        private const val TAG = "StoreViewModel"
    }
}