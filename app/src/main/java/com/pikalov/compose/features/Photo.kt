package com.pikalov.compose.features

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Photo(
    val id: Int,
    val urlSmall: String?,
    val urlBig: String?,
    val date: Date
) : Parcelable

@Parcelize
data class Photos(
    val photos: List<Photo>
): Parcelable

fun List<Photo>.toPhotos() : Photos = Photos(this)