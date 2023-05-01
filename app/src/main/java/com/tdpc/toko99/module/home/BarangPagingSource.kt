package com.tdpc.toko99.module.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tdpc.toko99.core.data.remote.response.ItemBarang
import com.tdpc.toko99.core.data.remote.retrofit.ApiService

class BarangPagingSource(private val apiService: ApiService): PagingSource<Int,ItemBarang>() {

    override fun getRefreshKey(state: PagingState<Int, ItemBarang>): Int? {
        return state.anchorPosition?.let { anchorPosition->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemBarang> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getBarang(page = page)
            LoadResult.Page(
                data = response.data,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.data.isEmpty()) null else page.plus(1)
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }

}