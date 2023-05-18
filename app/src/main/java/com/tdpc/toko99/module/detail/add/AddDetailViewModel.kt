package com.tdpc.toko99.module.detail.add

import android.util.Log
import androidx.lifecycle.ViewModel
import com.tdpc.toko99.BuildConfig
import com.tdpc.toko99.core.data.remote.response.ResponseBarang
import com.tdpc.toko99.core.data.remote.retrofit.ApiConfig
import com.tdpc.toko99.core.domain.usecase.StoreUseCase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddDetailViewModel(private val storeUseCase: StoreUseCase) : ViewModel() {

    fun uploadSatuan(id: Int, satuan: String, harga: Double) {
        if (true) {
//            val bitmap  = rotateFile(getFile)
//            val file = reduceFileImage(bitmap)
//            val description = setDescription.toRequestBody("text/plain".toMediaType())
//            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
//            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
//                "image", file.name, requestImageFile
//            )
            val service = ApiConfig.provideApiService()
                .storeSatuan(
                    harga = harga,
                    bearer = BuildConfig.API_KEY,
                    id = id,
                    satuan = satuan
                )
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