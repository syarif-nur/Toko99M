package com.tdpc.toko99.core.utils

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