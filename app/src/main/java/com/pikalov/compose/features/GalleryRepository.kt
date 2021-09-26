package com.pikalov.compose.features

import com.vk.sdk.api.photos.dto.PhotosPhoto

interface GalleryRepository {

    /**
     * We should use UImodel, but later
     */
    suspend fun getAlbums() : List<PhotosPhoto>

}