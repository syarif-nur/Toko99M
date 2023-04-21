package com.tdpc.toko99.core.domain.repository

import com.tdpc.toko99.core.data.Resource
import com.tdpc.toko99.core.domain.model.BarangModel
import kotlinx.coroutines.flow.Flow

interface IStoreRepository {
    fun getAllBarang(): Flow<Resource<List<BarangModel>>>
}