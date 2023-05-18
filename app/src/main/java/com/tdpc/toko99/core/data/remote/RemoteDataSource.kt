package com.tdpc.toko99.core.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.tdpc.toko99.core.data.remote.retrofit.ApiService
import com.tdpc.toko99.module.home.BarangPagingSource

class RemoteDataSource private constructor(private val apiService: ApiService){

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }
    }

    fun getAllBarang(keyword: String) = Pager(
        config = PagingConfig(
            pageSize = 10,
        ),
        pagingSourceFactory = {
            BarangPagingSource(apiService,keyword)
        }
    ).flow


}