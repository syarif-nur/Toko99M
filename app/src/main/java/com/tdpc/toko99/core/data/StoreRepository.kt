package com.tdpc.toko99.core.data

import androidx.paging.PagingData
import androidx.paging.map
import com.tdpc.toko99.core.data.local.LocalDataSource
import com.tdpc.toko99.core.data.remote.RemoteDataSource
import com.tdpc.toko99.core.domain.model.BarangModel
import com.tdpc.toko99.core.domain.model.SatuanModel
import com.tdpc.toko99.core.domain.repository.IStoreRepository
import com.tdpc.toko99.core.utils.AppExecutors
import com.tdpc.toko99.core.utils.DataMapper
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
        return remoteDataSource.getAllBarang(keyword).map {
            DataMapper.mapResponseToDomain(it)
        }
    }
}


