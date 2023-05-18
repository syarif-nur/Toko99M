package com.tdpc.toko99.core.data.local

import androidx.lifecycle.LiveData
import com.tdpc.toko99.core.data.local.entity.BarangEntity
import com.tdpc.toko99.core.data.local.room.BarangDao

class LocalDataSource(private val barangDao: BarangDao) {
    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(barangDao: BarangDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(barangDao)
            }
    }

    fun getAllBarang(): LiveData<List<BarangEntity>> = barangDao.getAllBarang()

    suspend fun insertBarang(barangList: List<BarangEntity>) = barangDao.insertBarang(barangList)

}