package com.pikalov.compose.ui.imageDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.pikalov.compose.features.Photo
import com.pikalov.compose.features.Photos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * UI state for the Image details screen
 */
data class ImageDetailUiState(
    val currentPhoto: Photo,
    val otherPhotos: List<Photo>
)


@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {


    private val _uiState: MutableStateFlow<ImageDetailUiState>

    init {
        //SafeArgs working only with ui components, so TODO() fix NPE signs
        val currentPhoto = savedStateHandle.get<Photo>(CURRENT_PHOTO)!!
        val otherPhotos = savedStateHandle.get<Photos>(OTHER_PHOTOS)!!
        _uiState = MutableStateFlow(ImageDetailUiState(currentPhoto, otherPhotos.photos))
    }

    fun changeUiState(newImageId: Int) {
        val currentState = uiState.value
        val newPhoto = currentState.otherPhotos.find { it.id == newImageId } ?: return
        _uiState.value = currentState.copy(currentPhoto = newPhoto)
    }

    val uiState: StateFlow<ImageDetailUiState> = _uiState.asStateFlow()

    companion object {
        const val CURRENT_PHOTO = "currentPhoto"
        const val OTHER_PHOTOS = "otherPhotos"
    }

}
