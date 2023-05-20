package com.tdpc.toko99.module.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.tdpc.toko99.core.domain.model.BarangModel
import com.tdpc.toko99.core.domain.model.SatuanModel
import com.tdpc.toko99.ui.theme.AppTheme
import com.tdpc.toko99.ui.theme.Shapes
import java.text.NumberFormat
import java.util.Locale

@Composable
fun DetailScreen(
    barangModel: BarangModel,
    modifier: Modifier = Modifier,
    navigateToAddDetail: (BarangModel) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SubcomposeAsyncImage(
            model = barangModel.imgUrl,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            loading = {
                CircularProgressIndicator()
            },
            modifier = Modifier
                .size(350.dp)
                .clip(Shapes.medium)
        )
        Text(
            text = barangModel.namaBarang,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
        )
        Column(
            modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            DetailSatuanTable(
                satuans = barangModel.satuan,
                modifier = modifier
            )
        }
        Button(onClick = { navigateToAddDetail(barangModel) }, modifier.size(width = 200.dp, height = 50.dp)) {
            Text(text = "Tambah Satuan Barang", fontSize = 14.sp)
        }
    }
}

@Composable
fun DetailSatuanTable(
    satuans: List<SatuanModel?>?,
    modifier: Modifier
) {
    val localID = Locale("in", "ID")
    val numberFormat = NumberFormat.getCurrencyInstance(localID)
    Column(modifier = Modifier.size(350.dp, 200.dp)) {
        Row(
            modifier = modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "SATUAN",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "HARGA",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        if (satuans != null) {
            for (satuan in satuans) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${satuan?.satuan}",
                        fontSize = 20.sp,
                        modifier = modifier
                            .width(200.dp)
                            .padding(start = 30.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = numberFormat.format(satuan?.harga),
                        fontSize = 20.sp,
                        modifier = modifier.width(150.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SatuanPreview() {
    AppTheme() {
        DetailSatuanTable(
            satuans = listOf(
                SatuanModel(
                    1, 1, "pcs", 2000.0,
                ),
                SatuanModel(
                    2, 1, "boom", 20000.0,
                )
            ),
            modifier = Modifier
        )
    }
}