package com.tdpc.toko99.core.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.tdpc.toko99.core.data.local.entity.BarangEntity
import com.tdpc.toko99.core.data.local.room.BarangDatabase
import com.tdpc.toko99.core.data.remote.response.ItemBarang
import com.tdpc.toko99.core.data.remote.retrofit.ApiService

@OptIn(ExperimentalPagingApi::class)
class StoreRemoteMediator(
    private val database: BarangDatabase,
    private val apiService: ApiService,
) : RemoteMediator<Int, BarangEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, BarangEntity>): MediatorResult {
        val page = INITIAL_PAGE_INDEX
        try {
            val responseData = apiService.getBarang("", "", page, state.pages.size)

            val endOfPaginationReached = responseData.data.isEmpty()
            with(database) {
                if (loadType == LoadType.REFRESH) {
                    database.barangDao().insertBarang(responseData.data.map { itemBarang ->
                        BarangEntity(
                            itemBarang.id,
                            itemBarang.namaBarang,
                            itemBarang.imgUrl
                        )
                    })
                }
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }

    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }


}