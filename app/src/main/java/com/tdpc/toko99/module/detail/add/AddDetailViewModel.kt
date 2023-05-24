package com.tdpc.toko99.module.detail.add

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tdpc.toko99.BuildConfig
import com.tdpc.toko99.core.data.remote.response.ResponseBarang
import com.tdpc.toko99.core.data.remote.retrofit.ApiConfig
import com.tdpc.toko99.core.domain.usecase.StoreUseCase
import com.tdpc.toko99.ui.common.MainViewState
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddDetailViewModel(private val storeUseCase: StoreUseCase) : ViewModel() {

    private val _viewState = MutableStateFlow(MainViewState())
    val viewState = _viewState.asStateFlow()

    fun uploadSatuan(id: Int, satuan: String, harga: String) {
        viewModelScope.launch {
            _viewState.update { currentState ->
                currentState.copy(
                    isLoading = true
                )
            }
            if (satuan != "" && harga != "") {
                val service = ApiConfig.provideApiService()
                    .storeSatuan(
                        harga = harga.toDouble(),
                        bearer = BuildConfig.API_KEY,
                        id = id,
                        satuan = satuan
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