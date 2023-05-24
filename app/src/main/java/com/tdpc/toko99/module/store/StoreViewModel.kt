package com.tdpc.toko99.module.store

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tdpc.toko99.BuildConfig
import com.tdpc.toko99.core.data.remote.response.ResponseBarang
import com.tdpc.toko99.core.data.remote.retrofit.ApiConfig
import com.tdpc.toko99.core.domain.usecase.StoreUseCase
import com.tdpc.toko99.core.utils.reduceFileImage
import com.tdpc.toko99.core.utils.rotateFile
import com.tdpc.toko99.ui.common.MainViewState
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    private val _viewState = MutableStateFlow(MainViewState())
    val viewState = _viewState.asStateFlow()


    fun startProcess(getFile: File?, setDescription: String) {
        viewModelScope.launch {
            _viewState.update { currentState ->
                currentState.copy(
                    isLoading = true
                )
            }
            if (getFile != null && setDescription != "") {
                val bitmap = rotateFile(getFile)
                val file = reduceFileImage(bitmap)
                val description = setDescription.toRequestBody("text/plain".toMediaType())
                val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "image", file.name, requestImageFile
                )
                val service = ApiConfig.provideApiService().storeBarang(
                    BuildConfig.API_KEY,
                    imageMultipart,
                    description
                )
                service.enqueue(object : Callback<ResponseBarang> {
                    override fun onResponse(
                        call: Call<ResponseBarang>, response: Response<ResponseBarang>
                    ) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null && !responseBody.error) {
                                _viewState.update { currentState ->
                                    currentState.copy(
                                        processSuccessEvent = triggered,
                                        isLoading = false
                                    )
                                }
                            } else {
                                _viewState.update { currentState ->
                                    currentState.copy(
                                        processSuccessWithStringEvent = triggered(response.body()?.message.toString()),
                                        isLoading = false
                                    )
                                }
                                Log.e(TAG, "onFailure: ${response.body()?.message}")
                            }
                        } else {
                            _viewState.update { currentState ->
                                currentState.copy(
                                    processSuccessWithStringEvent = triggered(response.body()?.message.toString()),
                                    isLoading = false
                                )
                            }
                            Log.e(TAG, "onFailure: ${response.body()?.message}")
                        }
                    }

                    override fun onFailure(call: Call<ResponseBarang>, t: Throwable) {
                        _viewState.update { currentState ->
                            currentState.copy(
                                processSuccessWithStringEvent = triggered(t.message.toString()),
                                isLoading = false
                            )
                        }
                        Log.e(TAG, "onFailure: ${t.message}")
                    }
                })
            } else {
                _viewState.update { currentState ->
                    currentState.copy(
                        processSuccessWithStringEvent = triggered("Lengkapi Form Terlebih Dahulu"),
                        isLoading = false
                    )
                }
            }
        }
    }

    fun setShowMessageConsumed() {
        _viewState.update { currentState ->
            currentState.copy(
                processSuccessEvent = consumed,
                processSuccessWithStringEvent = consumed(),
            )
        }
    }

    companion object {
        private const val TAG = "StoreViewModel"
    }
}