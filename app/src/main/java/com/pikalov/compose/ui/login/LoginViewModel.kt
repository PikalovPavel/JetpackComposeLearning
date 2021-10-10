package com.pikalov.compose.ui.login

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.pikalov.compose.features.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
): ViewModel() {

    fun login(activity: Activity) {
        authRepository.login(activity)
    }

}