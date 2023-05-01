package com.tdpc.toko99.core.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.tdpc.toko99.core.data.remote.response.ItemBarang
import com.tdpc.toko99.core.domain.model.BarangModel
import kotlinx.coroutines.flow.Flow

interface IStoreRepository {
    fun getAllBarang(): Flow<PagingData<ItemBarang>>

}