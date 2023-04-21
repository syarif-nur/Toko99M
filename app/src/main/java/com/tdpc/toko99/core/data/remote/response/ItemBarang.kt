package com.tdpc.toko99.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class ItemBarang(

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("img_url")
    val imgUrl: String,

    @field:SerializedName("satuan")
    val satuan: List<SatuanBarang>? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("nama_barang")
    val namaBarang: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("status")
    val status: Int? = null
)