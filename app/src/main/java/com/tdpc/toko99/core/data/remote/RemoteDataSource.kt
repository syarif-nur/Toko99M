package com.tdpc.toko99.core.data.remote

import android.content.ClipData.Item
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tdpc.toko99.core.data.remote.response.ItemBarang
import com.tdpc.toko99.core.data.remote.response.ListBarangResponse
import com.tdpc.toko99.core.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiService: ApiService){

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }
    }

    fun getAllBarang(): LiveData<List<ItemBarang>> {
        val resultData = MutableLiveData<List<ItemBarang>>()

        val client = apiService.getBarang()

        client.enqueue(object : Callback<ListBarangResponse> {
            override fun onResponse(
                call: Call<ListBarangResponse>,
                response: Response<ListBarangResponse>
            ) {
                val dataArray = response.body()
                if (dataArray != null) {
                    resultData.value =  dataArray.data
                }
            }

            override fun onFailure(call: Call<ListBarangResponse>, t: Throwable) {
                Log.e("RemoteDataSource", t.message.toString())
            }
        })

        return resultData
    }
}