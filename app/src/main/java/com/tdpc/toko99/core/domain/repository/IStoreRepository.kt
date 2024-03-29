package com.tdpc.toko99.core.domain.repository

import androidx.paging.PagingData
import com.tdpc.toko99.core.domain.model.BarangModel
import kotlinx.coroutines.flow.Flow

interface IStoreRepository {
    fun getAllBarang(keyword: String): Flow<PagingData<BarangModel>>

}