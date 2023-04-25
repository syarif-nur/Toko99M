package com.tdpc.toko99.core.domain.repository

import androidx.lifecycle.LiveData
import com.tdpc.toko99.core.domain.model.BarangModel
import kotlinx.coroutines.flow.Flow

interface IStoreRepository {
    fun getAllBarang(): LiveData<List<BarangModel>>

}