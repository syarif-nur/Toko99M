package com.tdpc.toko99.core.domain.usecase

import com.tdpc.toko99.core.data.Resource
import com.tdpc.toko99.core.domain.model.BarangModel
import com.tdpc.toko99.core.domain.repository.IStoreRepository
import kotlinx.coroutines.flow.Flow

class StoreInteractor(private val storeRepository: IStoreRepository) : StoreUseCase {
    override fun getAllBarang(): Flow<Resource<List<BarangModel>>> = storeRepository.getAllBarang()
}