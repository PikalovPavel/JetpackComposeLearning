package com.pikalov.compose.features

import android.app.Activity
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun login(context: Activity)
    fun logout()

    val authState: Flow<AuthState>

    fun onTokenExpired()
    fun onLoggedIn()

}