package com.pikalov.compose.ui.gallery

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import com.pikalov.compose.ui.theme.JetpackComposeLearningTheme
import com.pikalov.compose.util.Result
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.pikalov.compose.features.Photo

@Composable
@ExperimentalFoundationApi
fun GalleryContent(
    state: Result<GalleryUiState>,
    onClickListener: (photo: Photo) -> Unit
) {
    when (state) {
        is Result.Error -> CircularProgressIndicator()
        Result.Loading -> CircularProgressIndicator()
        is Result.Success -> GalleryContentLoaded(state.data.photos, onClickListener)
    }
}

@ExperimentalFoundationApi
@Composable
fun GalleryContentLoaded(
    photos: List<Photo>,
    onClickListener: (photo: Photo) -> Unit
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2)
    ) {
        itemsIndexed(photos) { index, item ->
            Box(
                modifier = Modifier.padding(
                    start = if (index % 2 == 0) 0.dp else 1.dp,
                    end = if (index % 2 == 0) 1.dp else 0.dp,
                    bottom = 1.dp,
                    top = if (index == 0 || index == 1) 0.dp else 1.dp
                )
            ) {
                GalleryImage(
                    photo = item,
                    onClickListener = onClickListener
                )
            }
        }
    }
}


@Composable
fun GalleryImage(
    photo: Photo,
    onClickListener: (photo: Photo) -> Unit
) {
    val size = (LocalConfiguration.current.screenWidthDp / 2)
    Image(
        painter = rememberImagePainter(photo.urlSmall),
        contentDescription = null,
        modifier = Modifier
            .width(size.dp)
            .height(size.dp)
            .clickable {
                onClickListener.invoke(photo)
            },
        contentScale = ContentScale.Crop,
    )
}
