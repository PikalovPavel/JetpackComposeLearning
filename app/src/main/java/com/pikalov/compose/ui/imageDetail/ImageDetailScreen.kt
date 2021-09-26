package com.pikalov.compose.ui.imageDetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import com.pikalov.compose.ui.theme.JetpackComposeLearningTheme
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pikalov.compose.R
import com.pikalov.compose.features.Photo
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ImageDetailContent(state: ImageDetailUiState) {
    val pattern = "dd MMMMMM yyyy"
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        TopAppBar(
            title = {
                Text(text = simpleDateFormat.format(state.currentPhoto.date))
            },
            navigationIcon = {
                Image(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = null,
                    Modifier.padding(start = 12.dp)
                )
            },
            actions = {
                Image(
                    painter = painterResource(R.drawable.ic_save),
                    contentDescription = null,
                    Modifier.padding(end = 12.dp)
                        .clickable {
                    }
                )
            },
            backgroundColor = Color.White
        )
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MobileUpImage(
            imageId = state.currentPhoto.urlBig,
            size = LocalConfiguration.current.screenWidthDp
        )
    }

    Column(
        verticalArrangement = Arrangement.Bottom,
    ) {
        OtherImages(state.otherPhotos)
    }


}


@Composable
fun OtherImages(photos: List<Photo>) {
    LazyRow() {
        itemsIndexed(photos) { index, item ->
            Box(
                modifier = Modifier.padding(
                    start = if (index == 0) 2.dp else 1.dp,
                    end = if (index == photos.lastIndex) 2.dp else 1.dp
                )
            ) {
                MobileUpImage(
                    imageId = item.urlSmall,
                    size = 56
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun MobileUpImage(
    imageId: String?,
    size: Int
) {
    Image(
        painter = rememberImagePainter(imageId),
        contentDescription = null,
        modifier = Modifier
            .width(size.dp)
            .height(size.dp),
        contentScale = ContentScale.Crop,
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
@ExperimentalFoundationApi
fun Gallery() {
    JetpackComposeLearningTheme {

    }
}