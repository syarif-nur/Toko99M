package com.tdpc.toko99.core.data

import com.tdpc.toko99.core.data.local.LocalDataSource
import com.tdpc.toko99.core.data.remote.RemoteDataSource
import com.tdpc.toko99.core.data.remote.network.ApiResponse
import com.tdpc.toko99.core.data.remote.response.ItemBarang
import com.tdpc.toko99.core.domain.model.BarangModel
import com.tdpc.toko99.core.domain.repository.IStoreRepository
import com.tdpc.toko99.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreRepository(
private val remoteDataSource: RemoteDataSource,
private val localDataSource: LocalDataSource
): IStoreRepository {

    companion object {
        @Volatile
        private var instance: StoreRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
        ): StoreRepository =
            instance ?: synchronized(this) {
                instance ?: StoreRepository(remoteData, localData)
            }
    }


    override fun getAllBarang(): Flow<Resource<List<BarangModel>>> {
        return object : NetworkBoundResource<List<BarangModel>, List<ItemBarang>>() {
            override fun loadFromDB(): Flow<List<BarangModel>> {
                return localDataSource.getAllGame().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ItemBarang>>> =
                remoteDataSource.getAllBarang()

            override suspend fun saveCallResult(data: List<ItemBarang>) {
                val gameList = DataMapper.mapResponseToEntities(data)
                localDataSource.insertGame(gameList)
            }

            override fun shouldFetch(data: List<BarangModel>?): Boolean =
                data == null || data.isEmpty()

        }.asFlow()
    }

}