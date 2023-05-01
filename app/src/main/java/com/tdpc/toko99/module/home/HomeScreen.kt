package com.tdpc.toko99.module.home

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tdpc.toko99.core.domain.model.BarangModel
import com.tdpc.toko99.ui.theme.Toko99Theme
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.tdpc.toko99.core.data.remote.response.ItemBarang
import com.tdpc.toko99.core.di.Injection
import com.tdpc.toko99.ui.common.ViewModelFactory


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideMealUseCase(LocalContext.current))
    ),
//    navController: NavHostController = rememberNavController(),
    ) {
    val barang = viewModel.getBarang().collectAsLazyPagingItems()
    HomeContent(
        barang = barang,
        modifier = modifier,
    )
//    Box(modifier = Modifier.fillMaxSize()) {
//        FloatingActionButton(
//            onClick = {navController.navigate("store")},
//            shape = RoundedCornerShape(16.dp),
//            backgroundColor = MaterialTheme.colors.primary,
//            modifier =  Modifier.align(Alignment.BottomEnd).padding(bottom = 10.dp, end = 10.dp)
//        ) {
//            Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add FAB")
//        }
//    }

}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    barang: LazyPagingItems<ItemBarang>,
    localContext: Context = LocalContext.current
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(
            items = barang
        ) { data ->
            if (data != null) {
                BarangRow(barangModel = data)
            }
        }
        when (val state = barang.loadState.refresh) {
            is LoadState.Error -> {
                Toast.makeText(localContext, state.toString(), Toast.LENGTH_SHORT).show()
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

                        CircularProgressIndicator(color = Color.Black)
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

@Preview
@Composable
fun ProfilePreview() {
    Toko99Theme {
        HomeScreen()
    }
}