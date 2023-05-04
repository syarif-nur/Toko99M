package com.tdpc.toko99.core.data.remote.retrofit

import com.tdpc.toko99.BuildConfig
import com.tdpc.toko99.core.data.remote.response.ListBarangResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("list-barang/{search}")
    suspend fun getBarang(
        @Header("Authorization") bearer: String = BuildConfig.API_KEY,
        @Path("search") search: String?,
        @Query("page") page: Int,
    ): ListBarangResponse

    @GET("master-barang")
    fun masterBarang(
        @Header("Authorization") bearer: String = BuildConfig.API_KEY,
        @Query("page") page: Int,
    ): Call<ListBarangResponse>

    @POST("store-barang")
    fun storeBarang(
        @Header("Authorization") bearer: String = BuildConfig.API_KEY,
    )
}