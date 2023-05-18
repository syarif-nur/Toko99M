package com.tdpc.toko99.module.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.tdpc.toko99.core.domain.usecase.StoreUseCase

class HomeViewModel(private val storeUseCase: StoreUseCase) : ViewModel() {

    fun getBarang(keyword: String) = storeUseCase.getAllBarang(keyword).cachedIn(viewModelScope)
}