package com.pikalov.compose.featuresIMPL

import com.pikalov.compose.features.GalleryRepository
import com.vk.api.sdk.VK
import com.vk.dto.common.id.UserId
import com.vk.sdk.api.photos.PhotosService
import com.vk.sdk.api.photos.dto.PhotosPhoto
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(

): GalleryRepository {


    override suspend fun getAlbums(): List<PhotosPhoto> {
        return VK.executeSync(
            PhotosService().photosGet(
                ownerId = UserId(GROUP_ID),
                albumId = ALBUM_ID,
                photoSizes = true
            )
        ).items
    }

    companion object {
        const val GROUP_ID = -128666765L
        const val ALBUM_ID = "266276915"
    }

}