package com.tdpc.toko99.module.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.tdpc.toko99.core.domain.model.BarangModel
import com.tdpc.toko99.ui.theme.Shapes


@Composable
fun BarangRow(
    barangModel: BarangModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SubcomposeAsyncImage(
            model = barangModel.imgUrl,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            loading = {
                CircularProgressIndicator()
            },
            modifier = Modifier
                .size(200.dp)
                .clip(Shapes.medium)
        )

        Column(
            modifier = modifier
                .padding(start = 16.dp, top = 0.dp, bottom = 16.dp, end = 0.dp),
        ) {
            Text(
                text = barangModel.namaBarang,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.subtitle2,
                fontSize = 20.sp
            )
        }
    }
}

//@Composable
//@Preview(showBackground = true)
//fun ItemBarangPreview() {
//    Toko99Theme {
//        BarangRow(
//            BarangModel(
//                id = 1,
//                namaBarang = "Title",
//                imgUrl = "https://www.themealdb.com/images/category/beef.png",
//            )
//        )
//    }
//}