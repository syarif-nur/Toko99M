package com.tdpc.toko99.core.data.remote.retrofit

import com.tdpc.toko99.core.data.remote.response.ListBarangResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("api/list-barang/")
    suspend fun getBarang(
        @Header("Authorization") bearer: String = "Bearer lhNyeGcDFV0AHlNz46hbN9kIX7Wnnzx6FuzCKfnF",
    ): ListBarangResponse

}