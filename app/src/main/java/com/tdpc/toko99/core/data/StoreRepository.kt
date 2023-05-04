package com.tdpc.toko99.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.tdpc.toko99.core.data.local.LocalDataSource
import com.tdpc.toko99.core.data.remote.RemoteDataSource
import com.tdpc.toko99.core.data.remote.response.ItemBarang
import com.tdpc.toko99.core.data.remote.retrofit.ApiService
import com.tdpc.toko99.core.domain.model.BarangModel
import com.tdpc.toko99.core.domain.repository.IStoreRepository
import com.tdpc.toko99.core.utils.AppExecutors
import com.tdpc.toko99.core.utils.DataMapper
import com.tdpc.toko99.module.home.BarangPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
) : IStoreRepository {

    companion object {
        @Volatile
        private var instance: StoreRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors,
        ): StoreRepository =
            instance ?: synchronized(this) {
                instance ?: StoreRepository(remoteData, localData, appExecutors)
            }
    }


    override fun getAllBarang(keyword: String): Flow<PagingData<BarangModel>> {
        return remoteDataSource.getAllBarang(keyword).map { pagingData ->
            pagingData.map { itemBarang ->
                BarangModel(
                    itemBarang.id,
                    itemBarang.namaBarang,
                    itemBarang.imgUrl)
            }
        }
    }
}
