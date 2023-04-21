package com.tdpc.toko99.core.domain.usecase

import com.tdpc.toko99.core.data.Resource
import com.tdpc.toko99.core.domain.model.BarangModel
import kotlinx.coroutines.flow.Flow

interface StoreUseCase {
    fun getAllBarang(): Flow<Resource<List<BarangModel>>>
}