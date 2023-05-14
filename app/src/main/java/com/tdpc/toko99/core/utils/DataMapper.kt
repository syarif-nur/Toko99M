package com.tdpc.toko99.core.utils

import androidx.paging.PagingData
import androidx.paging.map
import com.tdpc.toko99.core.data.local.entity.BarangEntity
import com.tdpc.toko99.core.data.remote.response.ItemBarang
import com.tdpc.toko99.core.domain.model.BarangModel

//object DataMapper {
//    fun mapResponseToDomain(input: PagingData<ItemBarang>): ArrayList<BarangModel> {
//        val barangList = ArrayList<BarangModel>()
//        input.map {
//            val game = BarangModel(
//                it.id,
//                it.namaBarang,
//                it.imgUrl,
//                )
//            barangList.add(game)
//        }
//        return barangList
//    }
//
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
//    )
//}