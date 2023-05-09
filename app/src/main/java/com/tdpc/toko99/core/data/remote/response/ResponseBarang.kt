package com.tdpc.toko99.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseBarang(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("error")
    val error: Boolean,
)