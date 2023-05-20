package com.tdpc.toko99.module.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.tdpc.toko99.core.di.Injection
import com.tdpc.toko99.core.domain.model.BarangModel
import com.tdpc.toko99.ui.common.ViewModelFactory
import com.tdpc.toko99.ui.theme.AppTheme


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideMealUseCase(LocalContext.current))
    ),
    navigateToDetail: (BarangModel) -> Unit
) {
    var keyword by rememberSaveable { mutableStateOf("") }

    val barang = viewModel.getBarang(keyword).collectAsLazyPagingItems()
    HomeContent(
        barang = barang,
        modifier = modifier,
        navigateToDetail = navigateToDetail
    ) {
        keyword = it
        viewModel.getBarang(it)
    }

}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    barang: LazyPagingItems<BarangModel>,
    navigateToDetail: (BarangModel) -> Unit,
    searchButton: (String) -> Unit,
) {
    var keyword by rememberSaveable { mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Row {
            TextField(
                value = keyword,
                onValueChange = {
                    keyword = it
                },
                label = { Text("Cari Barang") },
                modifier = modifier.weight(1f)
            )
            Button(onClick = {
                if (keyword.isNotEmpty()) {
                    searchButton(keyword)
                }
            }) {
                Icon(
                    tint = Color.White,
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                )
            }
        }
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
        ) {
            items(
                items = barang
            ) { data ->
                if (data != null) {
                    BarangRow(
                        barangModel = data,
                        modifier = Modifier.clickable {
                            navigateToDetail(data)
                        }
                    )
                }
            }
            when (val state = barang.loadState.refresh) {
                is LoadState.Error -> {
                    Log.e("PagingData: ", state.toString())
                }
                is LoadState.Loading -> {
                    item {
                        Column(
                            modifier = Modifier
                                .fillParentMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(8.dp),
                                text = "Refresh Loading"
                            )

                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }

                else -> {}
            }
            when (val state = barang.loadState.append) { // Pagination
                is LoadState.Error -> {
                    state.error
                }
                is LoadState.Loading -> { // Pagination Loading UI
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(text = "Pagination Loading")

                            CircularProgressIndicator(color = Color.Black)
                        }
                    }
                }

                else -> {}
            }
        }

    }
}


@Preview
@Composable
fun ProfilePreview() {
    AppTheme() {
        HomeScreen(
            navigateToDetail = {}
        )
    }
}