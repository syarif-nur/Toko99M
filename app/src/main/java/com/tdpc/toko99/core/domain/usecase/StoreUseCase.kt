package com.tdpc.toko99.core.domain.usecase

import androidx.paging.PagingData
import com.tdpc.toko99.core.domain.model.BarangModel
import kotlinx.coroutines.flow.Flow

interface StoreUseCase {
    fun getAllBarang(keyword: String): Flow<PagingData<BarangModel>>


}