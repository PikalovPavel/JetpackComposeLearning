package com.pikalov.compose.ui.imageDetail

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.ImagePainter
import coil.request.ImageRequest
import com.pikalov.compose.R
import com.pikalov.compose.features.Photo
import com.pikalov.compose.ui.theme.toast
import timber.log.Timber
import java.net.URL
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.time.LocalDate

import java.time.format.FormatStyle

import java.time.format.DateTimeFormatter
import java.time.ZoneId


@Composable
fun ImageDetailContent(
    state: ImageDetailUiState,
    onBackPressed: () -> Unit,
    onImageClick: () -> Unit
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
                    painter = painterResource(R.drawable.ic_save),
                    contentDescription = null,
                    Modifier
                        .padding(end = 12.dp)
                        .clickable {
                            onImageClick.invoke()
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
            size = LocalConfiguration.current.screenWidthDp + 1,
            LocalContext.current
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
                    size = 56,
                    LocalContext.current
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun MobileUpImage(
    imageId: String?,
    size: Int,
    context: Context
) {
    Image(
        painter = rememberImagePainter(
            data = imageId,
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
        modifier = Modifier
            .width(size.dp)
            .height(size.dp),
        contentScale = ContentScale.Crop,
    )
}

fun Date.toFormatString(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        DateTimeFormatter.ofPattern("dd MMM yyyy")
            .withLocale(Locale("ru"))
            .format(this.toLocalDate())
    } else {
        SimpleDateFormat("MMMM dd yyyy", Locale.US)
            .format(this)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun Date.toLocalDate(): LocalDate = toInstant()
    .atZone(ZoneId.systemDefault())
    .toLocalDate()


