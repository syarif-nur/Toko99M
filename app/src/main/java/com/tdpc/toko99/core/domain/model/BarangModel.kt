package com.tdpc.toko99.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BarangModel(
    val id: Int,
    val namaBarang: String,
    val imgUrl: String,
) : Parcelable