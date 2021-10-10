package com.pikalov.compose.ui.imageDetail

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
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
import coil.compose.rememberImagePainter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import com.pikalov.compose.R
import com.pikalov.compose.features.Photo
import com.pikalov.compose.ui.theme.toast
import com.pikalov.compose.util.toFormatString
import timber.log.Timber


@Composable
fun ImageDetailContent(
    state: ImageDetailUiState,
    onBackPressed: () -> Unit,
    onShareClick: () -> Unit,
    onSmallImageClick: (imageId: Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        TopAppBar(
            title = {
                Text(text = state.currentPhoto.date.toFormatString())
            },
            navigationIcon = {
                Image(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = null,
                    Modifier.padding(start = 12.dp)
                        .clickable {
                            onBackPressed.invoke()
                        }
                )
            },
            actions = {
                Image(
                    painter = painterResource(R.drawable.ic_share),
                    contentDescription = null,
                    Modifier
                        .padding(end = 12.dp)
                        .clickable {
                            onShareClick.invoke()
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
            id = state.currentPhoto.id,
            imageUrl = state.currentPhoto.urlBig,
            size = LocalConfiguration.current.screenWidthDp + 1,
            LocalContext.current
        )
    }

    Column(
        verticalArrangement = Arrangement.Bottom,
    ) {
        OtherImages(state.otherPhotos, onSmallImageClick)
    }
}


@Composable
fun OtherImages(
    photos: List<Photo>,
    onSmallImageClick: (imageId: Int) -> Unit
) {
    LazyRow() {
        itemsIndexed(photos) { index, item ->
            Box(
                modifier = Modifier.padding(
                    start = if (index == 0) 2.dp else 1.dp,
                    end = if (index == photos.lastIndex) 2.dp else 1.dp
                )
            ) {
                MobileUpImage(
                    id = item.id,
                    imageUrl = item.urlSmall,
                    size = 56,
                    LocalContext.current,
                    imageClicker = onSmallImageClick
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun MobileUpImage(
    id: Int,
    imageUrl: String?,
    size: Int,
    context: Context,
    imageClicker: ((imageId: Int) -> Unit)? = null
) {
    var modifier = Modifier
        .width(size.dp)
        .height(size.dp)
    if (imageClicker != null) {
        modifier = modifier.clickable { imageClicker.invoke(id) }
    }
    Image(
        painter = rememberImagePainter(
            data = imageUrl,
            builder = {
                listener(
                    onError = { _: ImageRequest, throwable: Throwable ->
                        Timber.wtf(throwable)
                        toast(context).show()
                    }
                )
            }
        ),
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop,
    )
}



