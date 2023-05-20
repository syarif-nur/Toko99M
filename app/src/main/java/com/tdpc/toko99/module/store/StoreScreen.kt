package com.tdpc.toko99.module.store

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toFile
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.tdpc.toko99.core.di.Injection
import com.tdpc.toko99.module.store.camera.CameraCapture
import com.tdpc.toko99.ui.common.ViewModelFactory
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
) {
    var imageUri by remember { mutableStateOf(EMPTY_IMAGE_URI) }
    var nama_barang by remember { mutableStateOf("") }
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
            Spacer(modifier = Modifier.height(50.dp))
            Button(
                onClick = {
                    viewModel.uploadImage(nama_barang, imageUri.toFile())
                    navigateToHome()
                }
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
