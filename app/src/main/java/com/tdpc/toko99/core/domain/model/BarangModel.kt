package com.tdpc.toko99.core.domain.model

import android.os.Parcelable
import com.tdpc.toko99.core.data.remote.response.SatuanBarang
import kotlinx.parcelize.Parcelize

@Parcelize
data class BarangModel(
    val id: Int,
    val namaBarang: String,
    val imgUrl: String,
    val satuan: List<SatuanModel?>?
) : Parcelable

@Parcelize
data class SatuanModel(
    val id: Int?,
    val idBarang: Int?,
    val satuan: String?,
    val harga: Double?,
) : Parcelable