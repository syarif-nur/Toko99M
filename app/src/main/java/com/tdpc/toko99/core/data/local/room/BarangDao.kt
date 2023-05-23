package com.tdpc.toko99.core.data.local.room

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tdpc.toko99.core.data.local.entity.BarangEntity
import com.tdpc.toko99.core.data.remote.response.ItemBarang
import com.tdpc.toko99.core.domain.model.BarangModel


@Dao
interface BarangDao {

    @Query("SELECT * FROM barang_list")
    fun getAllBarang(): PagingSource<Int,BarangEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBarang(game: List<BarangEntity>)
}