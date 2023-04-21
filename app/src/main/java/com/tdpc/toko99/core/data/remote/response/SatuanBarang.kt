package com.tdpc.toko99.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class SatuanBarang(

    @field:SerializedName("harga")
    val harga: Int? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("id_barang")
    val idBarang: Int? = null,

    @field:SerializedName("satuan")
    val satuan: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("status")
    val status: Int? = null
)