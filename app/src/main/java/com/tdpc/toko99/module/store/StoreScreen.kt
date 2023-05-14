package com.tdpc.toko99.module.store

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.core.net.toFile
import androidx.core.net.toUri
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.tdpc.toko99.core.utils.Permission
import com.tdpc.toko99.module.store.camera.CameraCapture
import com.tdpc.toko99.module.store.camera.CameraPreview
import com.tdpc.toko99.module.store.camera.CapturePictureButton
import com.tdpc.toko99.module.store.camera.executor
import com.tdpc.toko99.module.store.camera.getCameraProvider
import com.tdpc.toko99.module.store.camera.takePicture
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.io.File
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tdpc.toko99.core.di.Injection
import com.tdpc.toko99.core.utils.rotateFile
import com.tdpc.toko99.ui.common.ViewModelFactory

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
            Spacer(modifier = Modifier.height(50.dp))
            TextField(
                value = nama_barang,
                onValueChange = {
                    nama_barang = it
                },
                label = { Text("Masukkan Nama Barang.") },
            )
            Button(
                onClick = {
                    viewModel.uploadImage(nama_barang, imageUri.toFile())
                    navigateToHome()
                }
            ) {
                Text("Upload Barang")
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
