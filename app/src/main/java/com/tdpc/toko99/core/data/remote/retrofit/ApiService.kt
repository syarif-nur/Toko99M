package com.tdpc.toko99.core.data.remote.retrofit

import com.tdpc.toko99.core.data.remote.response.ListBarangResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("/api/list-barang/")
    suspend fun getBarang(
        @Header("Authorization") bearer: String = "Bearer 3ZITsXihhIMj4sIXGvcw8mI4JluCh0xmS5Onr99o",
        @Query("page") page: Int
    ): ListBarangResponse

}