package com.tdpc.toko99.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.tdpc.toko99.core.data.remote.response.ItemBarang
import com.tdpc.toko99.core.domain.model.BarangModel
import com.tdpc.toko99.core.domain.repository.IStoreRepository
import kotlinx.coroutines.flow.Flow

class StoreInteractor(private val storeRepository: IStoreRepository) : StoreUseCase {
    override fun getAllBarang(keyword: String): Flow<PagingData<BarangModel>> = storeRepository.getAllBarang(keyword)
}