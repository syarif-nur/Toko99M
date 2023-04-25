package com.tdpc.toko99.module.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tdpc.toko99.core.domain.model.BarangModel
import com.tdpc.toko99.ui.theme.Toko99Theme
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.academy.themealsapp.ui.view.EmptyView
import com.dicoding.academy.themealsapp.ui.view.LoadingView
import com.tdpc.toko99.core.di.Injection
import com.tdpc.toko99.ui.common.ViewModelFactory


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideMealUseCase(LocalContext.current))
    )
) {
    val barang = viewModel.getBarang().observeAsState().value
    barang?.let {
//        if (data != null) {
//            when (data) {
//                is Resource.Loading -> LoadingView()
//                is Resource.Success -> {
//                    HomeContent(
//                        barang = data.data!!,
//                        modifier = modifier,
//                    )
//                }
//                is Resource.Error -> {
//                    EmptyView()
//                }
//            }
//        }
        if (!it.data.isNullOrEmpty()) {
            HomeContent(
                barang = it.data,
                modifier = modifier,
            )
        } else if (it.data != null) {
            LoadingView()
        } else {
            EmptyView()
            viewModel.getBarang()
        }
    }

}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    barang: List<BarangModel>
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(barang) { data ->
            BarangRow(barangModel = data)
        }

    }
}

@Preview
@Composable
fun ProfilePreview() {
    Toko99Theme {
        HomeScreen()
    }
}