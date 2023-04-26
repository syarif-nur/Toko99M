package com.tdpc.toko99.core.data.remote.retrofit

import com.tdpc.toko99.core.data.remote.response.ListBarangResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("/api/list-barang/")
    fun getBarang(
        @Header("Authorization") bearer: String = "Bearer brBxjqBD9GLb5vBLLxMyeEcIWgvrg4tHuY8S25o6",
//        @Query("page") page: Int
    ): Call<ListBarangResponse>

}