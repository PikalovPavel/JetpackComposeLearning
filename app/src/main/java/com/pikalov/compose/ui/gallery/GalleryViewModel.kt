package com.pikalov.compose.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pikalov.compose.features.AuthRepository
import com.pikalov.compose.features.GalleryRepository
import com.pikalov.compose.features.Photo
import com.pikalov.compose.features.ThemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.lang.Exception
import javax.inject.Inject
import com.pikalov.compose.util.Result
import com.vk.sdk.api.photos.dto.PhotosPhotoSizesType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*



/**
 * UI state for the Home screen
 */
data class GalleryUiState(
    val photos: List<Photo> = emptyList(),
)


@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val galleryRepository: GalleryRepository,
    private val authRepository: AuthRepository,
    private val themeRepository: ThemeRepository
) : ViewModel() {

    init {
        getPhotos()
    }

    private val _uiState = MutableStateFlow<Result<GalleryUiState>>(Result.Loading)
    val uiState: StateFlow<Result<GalleryUiState>> = _uiState.asStateFlow()

    fun logout() {
        authRepository.logout()
    }

    fun changeTheme() {
        viewModelScope.launch {
            themeRepository.switchTheme()
        }
    }

    private fun getPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                galleryRepository.getAlbums()
                    .map { photo ->
                        Photo(
                            id = photo.id,
                            urlSmall = photo.sizes?.find { it.type == PhotosPhotoSizesType.Z }?.url,
                            urlBig = photo.sizes?.find { it.type == PhotosPhotoSizesType.W }?.url,
                            date = Date(photo.date.toLong() * 1000)
                        )
                    }
            }.onFailure {
                _uiState.value = Result.Error(it as Exception)
            }.onSuccess {
                _uiState.value = Result.Success(GalleryUiState(it))
            }
        }
    }
}
