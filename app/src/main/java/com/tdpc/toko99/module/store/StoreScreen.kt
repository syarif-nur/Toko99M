package com.tdpc.toko99.module.store

import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toFile
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.tdpc.toko99.core.di.Injection
import com.tdpc.toko99.module.store.camera.CameraCapture
import com.tdpc.toko99.ui.common.MainViewState
import com.tdpc.toko99.ui.common.ViewModelFactory
import com.tdpc.toko99.ui.theme.AppTheme
import de.palm.composestateevents.EventEffect
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoilApi
@ExperimentalCoroutinesApi
@ExperimentalPermissionsApi
@Composable
fun StoreScreen(
    modifier: Modifier = Modifier,
    viewModel: StoreViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideMealUseCase(LocalContext.current))
    ),
    navigateToHome: () -> Unit,
    isLoading: Boolean = false,
    snackbarHostState: SnackbarHostState
) {
    var imageUri by remember { mutableStateOf(EMPTY_IMAGE_URI) }
    var nama_barang by remember { mutableStateOf("") }
    val viewState: MainViewState by viewModel.viewState.collectAsStateWithLifecycle()
    EventEffect(
        event = viewState.processSuccessEvent,
        onConsumed = viewModel::setShowMessageConsumed,
    ){
        snackbarHostState.showSnackbar("Berhasil")
        navigateToHome()
    }

    EventEffect(
        event = viewState.processSuccessWithStringEvent,
        onConsumed = viewModel::setShowMessageConsumed,
    ){
        snackbarHostState.showSnackbar(it)
    }

    if (imageUri != EMPTY_IMAGE_URI) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = modifier.size(width = 500.dp, height = 450.dp)) {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 15.dp)
                        .align(Alignment.TopCenter),
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = "Captured image"
                )
            }
            Button(onClick = { imageUri = EMPTY_IMAGE_URI }) {
                Text("Ambil ulang gambar")
            }
            OutlinedTextField(
                value = nama_barang,
                onValueChange = {
                    nama_barang = it
                },
                label = { Text("Masukkan Nama Barang.") },
            )
            AnimatedVisibility(visible = viewState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
                )
            }
            Spacer(modifier = Modifier.padding(bottom = 8.dp))
            Button(
                onClick = {
                    viewModel.startProcess(imageUri.toFile(),nama_barang)
                },
                enabled = !viewState.isLoading
            ) {
                Text("Upload Barang", style = MaterialTheme.typography.titleMedium)
            }
        }
    } else {
        Box(modifier = modifier) {
            CameraCapture(
                modifier = modifier,
                onImageFile = { file ->
                    imageUri = file.toUri()
                }
            )
        }
    }
}

val EMPTY_IMAGE_URI: Uri = Uri.parse("file://dev/null")
