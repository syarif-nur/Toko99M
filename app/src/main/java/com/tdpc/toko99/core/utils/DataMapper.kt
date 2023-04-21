package com.tdpc.toko99.core.utils

import com.tdpc.toko99.core.data.local.entity.BarangEntity
import com.tdpc.toko99.core.data.remote.response.ItemBarang
import com.tdpc.toko99.core.domain.model.BarangModel

object DataMapper {
    fun mapResponseToEntities(input: List<ItemBarang>): List<BarangEntity> {
        val barangList = ArrayList<BarangEntity>()
        input.map {
            val game = BarangEntity(
                it.id,
                it.namaBarang,
                it.imgUrl,

                )
            barangList.add(game)
        }
        return barangList
    }

    fun mapEntitiesToDomain(input: List<BarangEntity>): List<BarangModel> =
        input.map {
            BarangModel(
                it.id,
                it.name,
                it.imageUrl,
            )
        }

    fun mapDomainToEntity(input: BarangModel) = BarangEntity(
        input.id,
        input.namaBarang,
        input.imgUrl,
    )
}