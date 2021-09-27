package com.pikalov.compose.ui.login

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pikalov.compose.features.AuthRepository
import com.pikalov.compose.features.GalleryRepository
import com.vk.api.sdk.VK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
): ViewModel() {

    val authState = authRepository.authState

    fun login(activity: Activity) {
        authRepository.login(activity)
    }


}