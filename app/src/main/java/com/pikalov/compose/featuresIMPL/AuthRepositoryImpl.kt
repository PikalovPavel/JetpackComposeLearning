package com.pikalov.compose.featuresIMPL

import android.app.Activity
import android.content.Context
import com.pikalov.compose.features.AuthRepository
import com.pikalov.compose.features.AuthState
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
) : AuthRepository {

    private val _authState = MutableStateFlow(getCurrentAuthState())

    override fun login(context: Activity) {
        VK.login(context, arrayListOf(VKScope.WALL, VKScope.PHOTOS, VKScope.GROUPS))
    }

    override fun logout() {
        VK.logout()
    }

    override val authState: Flow<AuthState> = _authState.asStateFlow()

    override fun onTokenExpired() {
       _authState.value = AuthState.NON_AUTHORIZED
    }

    override fun onLoggedIn() {
       _authState.value = AuthState.AUTHORIZED
    }

    private fun getCurrentAuthState(): AuthState {
        return if (VK.isLoggedIn()) AuthState.AUTHORIZED else AuthState.NON_AUTHORIZED
    }
}