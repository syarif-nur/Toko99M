package com.tdpc.toko99.core.domain.usecase

import androidx.lifecycle.LiveData
import com.tdpc.toko99.core.domain.model.BarangModel
import kotlinx.coroutines.flow.Flow

interface StoreUseCase {
    fun getAllBarang(): LiveData<List<BarangModel>>
}