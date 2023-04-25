package com.tdpc.toko99.core.domain.usecase

import androidx.lifecycle.LiveData
import com.tdpc.toko99.core.domain.model.BarangModel
import com.tdpc.toko99.core.domain.repository.IStoreRepository
import kotlinx.coroutines.flow.Flow

class StoreInteractor(private val storeRepository: IStoreRepository) : StoreUseCase {
    override fun getAllBarang(): LiveData<List<BarangModel>> = storeRepository.getAllBarang()
}