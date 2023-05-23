package com.tdpc.toko99.module.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.tdpc.toko99.core.domain.model.BarangModel
import com.tdpc.toko99.ui.theme.AppTheme
import com.tdpc.toko99.ui.theme.Shapes


@Composable
fun BarangRow(
    barangModel: BarangModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        SubcomposeAsyncImage(
            model = barangModel.imgUrl,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            loading = {
                CircularProgressIndicator()
            },
            modifier = Modifier
                .size(150.dp)
                .clip(Shapes.medium)
        )

        Column(
            modifier = modifier,
        ) {
            Text(
                text = barangModel.namaBarang,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ItemBarangPreview() {
    AppTheme() {
        BarangRow(
            BarangModel(
                id = 1,
                namaBarang = "Title",
                imgUrl = "https://www.themealdb.com/images/category/beef.png",
                satuan = null
            )
        )
    }
}