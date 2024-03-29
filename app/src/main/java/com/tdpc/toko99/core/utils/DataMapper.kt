package com.tdpc.toko99.core.utils

import androidx.paging.PagingData
import androidx.paging.map
import com.tdpc.toko99.core.data.remote.response.ItemBarang
import com.tdpc.toko99.core.domain.model.BarangModel
import com.tdpc.toko99.core.domain.model.SatuanModel

object DataMapper {
    fun mapResponseToDomain(input: PagingData<ItemBarang>): PagingData<BarangModel> {
        val result = input.map {
            BarangModel(
                it.id,
                it.namaBarang,
                it.imgUrl,
                it.satuan?.map { satuanBarang ->
                    SatuanModel(
                        satuanBarang?.id,
                        satuanBarang?.idBarang,
                        satuanBarang?.satuan,
                        satuanBarang?.harga
                    )
                }
            )
        }
        return result
    }

//    fun mapEntitiesToDomain(input: List<BarangEntity>): List<BarangModel> =
//        input.map {
//            BarangModel(
//                it.id,
//                it.name,
//                it.imageUrl,
//            )
//        }
//
//    fun mapDomainToEntity(input: BarangModel) = BarangEntity(
//        input.id,
//        input.namaBarang,
//        input.imgUrl,

}