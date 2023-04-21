package com.tdpc.toko99.core.di

import android.content.Context
import com.tdpc.toko99.core.data.StoreRepository
import com.tdpc.toko99.core.data.local.LocalDataSource
import com.tdpc.toko99.core.data.local.room.BarangDatabase
import com.tdpc.toko99.core.data.remote.RemoteDataSource
import com.tdpc.toko99.core.data.remote.retrofit.ApiConfig
import com.tdpc.toko99.core.domain.repository.IStoreRepository
import com.tdpc.toko99.core.domain.usecase.StoreInteractor
import com.tdpc.toko99.core.domain.usecase.StoreUseCase

object Injection {

    fun provideRepository(context: Context): IStoreRepository {
        val database = BarangDatabase.getInstance(context)
        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService())
        val localDataSource = LocalDataSource.getInstance(database.BarangDao())
        return StoreRepository.getInstance(remoteDataSource, localDataSource)
    }

    fun provideMealUseCase(context: Context): StoreUseCase {
        val repository = provideRepository(context)
        return StoreInteractor(repository)
    }
}