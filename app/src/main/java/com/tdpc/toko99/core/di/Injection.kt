package com.tdpc.toko99.core.di

import android.content.Context
import com.tdpc.toko99.core.data.StoreRepository
import com.tdpc.toko99.core.data.local.LocalDataSource
import com.tdpc.toko99.core.data.local.room.BarangDatabase
import com.tdpc.toko99.core.data.remote.RemoteDataSource
import com.tdpc.toko99.core.data.remote.retrofit.ApiConfig
import com.tdpc.toko99.core.data.remote.retrofit.ApiService
import com.tdpc.toko99.core.domain.repository.IStoreRepository
import com.tdpc.toko99.core.domain.usecase.StoreInteractor
import com.tdpc.toko99.core.domain.usecase.StoreUseCase
import com.tdpc.toko99.core.utils.AppExecutors

object Injection {

    private fun provideRepository(context: Context): IStoreRepository {
        val database = BarangDatabase.getInstance(context)
        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService())
        val localDataSource = LocalDataSource.getInstance(database.BarangDao())
        val appExecutors = AppExecutors()
        return StoreRepository.getInstance(remoteDataSource, localDataSource,appExecutors)
    }

    fun provideMealUseCase(context: Context): StoreUseCase {
        val repository = provideRepository(context)
        return StoreInteractor(repository)
    }
}