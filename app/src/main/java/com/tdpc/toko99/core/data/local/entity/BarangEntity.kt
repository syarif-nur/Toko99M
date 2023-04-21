package com.tdpc.toko99.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "barang_list")
data class BarangEntity(

    @PrimaryKey
    @ColumnInfo(name = "barangId")
    var id: Int,

    @ColumnInfo(name = "nama_barang")
    var name: String,

    @ColumnInfo(name = "image_url")
    var imageUrl: String,

)