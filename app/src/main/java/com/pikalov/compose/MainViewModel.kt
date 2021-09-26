package com.pikalov.compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pikalov.compose.features.AuthRepository
import com.pikalov.compose.features.AuthState
import com.pikalov.compose.features.AuthState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _navigateTo = MutableStateFlow(Navigate.IDLE)
    val navigateTo: StateFlow<Navigate> = _navigateTo

    // Load data from a suspend fun and mutate state
    init {
        viewModelScope.launch {
            val state = authRepository.authState.firstOrNull()
            val navigate = when (state) {
                AUTHORIZED -> Navigate.CONTENT
                NON_AUTHORIZED -> Navigate.LOGIN
                null -> Navigate.IDLE
            }
            _navigateTo.value = navigate
        }
    }

    fun onLogin() {
        authRepository.onLoggedIn()
    }

    enum class Navigate {
        IDLE,
        LOGIN,
        CONTENT
    }
}