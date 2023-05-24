package com.tdpc.toko99.module.detail.add

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tdpc.toko99.core.di.Injection
import com.tdpc.toko99.core.domain.model.BarangModel
import com.tdpc.toko99.ui.common.MainViewState
import com.tdpc.toko99.ui.common.ViewModelFactory
import de.palm.composestateevents.EventEffect


@Composable
fun AddDetailScreen(
    modifier: Modifier = Modifier,
    barangModel: BarangModel,
    viewModel: AddDetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideMealUseCase(LocalContext.current))
    ),
    navigateToHome: () -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    var satuan by rememberSaveable { mutableStateOf("") }
    var harga by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val viewState: MainViewState by viewModel.viewState.collectAsStateWithLifecycle()

    EventEffect(
        event = viewState.processSuccessEvent,
        onConsumed = viewModel::setShowMessageConsumed,
    ){
        navigateToHome()
        Toast.makeText(context,"Berhasil Diupload", Toast.LENGTH_SHORT).show()
    }

    EventEffect(
        event = viewState.processSuccessWithStringEvent,
        onConsumed = viewModel::setShowMessageConsumed,
    ){
        snackbarHostState.showSnackbar(it)
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = satuan,
            onValueChange = {
                satuan = it
            },
            label = { Text(text = "Masukkan Satuan") },
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        OutlinedTextField(
            value = harga,
            onValueChange = {
                harga = it
            },
            label = { Text(text = "Masukkan Harga", ) },
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = modifier.height(8.dp))
        AnimatedVisibility(visible = viewState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
            )
        }
        Button(onClick = {
            viewModel.uploadSatuan(
                harga = harga,
                id = barangModel.id,
                satuan = satuan
            )
        },
            enabled = !viewState.isLoading
        ) {
            Text(text = "Tambah Satuan Harga", style = MaterialTheme.typography.titleMedium)
        }
    }
}
