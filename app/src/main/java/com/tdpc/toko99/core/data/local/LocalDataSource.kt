package com.tdpc.toko99.core.data.local

import com.tdpc.toko99.core.data.local.entity.BarangEntity
import com.tdpc.toko99.core.data.local.room.BarangDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val barangDao: BarangDao) {
    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(barangDao: BarangDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(barangDao)
            }
    }

    fun getAllGame(): Flow<List<BarangEntity>> = barangDao.getAllBarang()

    suspend fun insertGame(barangList: List<BarangEntity>) = barangDao.insertBarang(barangList)

}