package com.tdpc.toko99.ui.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tdpc.toko99.core.di.Injection
import com.tdpc.toko99.core.domain.usecase.StoreUseCase
import com.tdpc.toko99.module.detail.add.AddDetailViewModel
import com.tdpc.toko99.module.home.HomeViewModel
import com.tdpc.toko99.module.store.StoreViewModel

class ViewModelFactory(private val storeUseCase: StoreUseCase) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideMealUseCase(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(storeUseCase) as T
            }
            modelClass.isAssignableFrom(StoreViewModel::class.java) -> {
                StoreViewModel(storeUseCase) as T
            }
            modelClass.isAssignableFrom(AddDetailViewModel::class.java) -> {
                AddDetailViewModel(storeUseCase) as T
            }

            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

}