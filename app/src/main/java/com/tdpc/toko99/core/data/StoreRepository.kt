package com.tdpc.toko99.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.tdpc.toko99.core.data.local.LocalDataSource
import com.tdpc.toko99.core.data.remote.RemoteDataSource
import com.tdpc.toko99.core.domain.model.BarangModel
import com.tdpc.toko99.core.domain.repository.IStoreRepository
import com.tdpc.toko99.core.utils.AppExecutors
import com.tdpc.toko99.core.utils.DataMapper

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


    override fun getAllBarang(): LiveData<List<BarangModel>> {
        return remoteDataSource.getAllBarang().map {
            DataMapper.mapResponseToDomain(it)
        }
    }
}
