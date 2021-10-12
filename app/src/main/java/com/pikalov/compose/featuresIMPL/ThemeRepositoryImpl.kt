package com.pikalov.compose.featuresIMPL

import android.content.Context
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.pikalov.compose.features.ThemeRepository
import com.pikalov.compose.features.ThemeRepository.Companion.DARK_VALUE
import com.pikalov.compose.features.ThemeRepository.Companion.LIGHT_VALUE
import com.pikalov.compose.features.ThemeRepository.Theme
import com.pikalov.compose.features.ThemeRepository.Theme.*
import com.pikalov.compose.util.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context
) : ThemeRepository {

    private val _currentTheme: Flow<Theme> = context.dataStore.data.map { preference ->
        Theme.fromInt(preference[THEME]) ?: context.systemTheme()
    }

    override suspend fun switchTheme() {
        context.dataStore.edit { preference ->
            preference[THEME] = when(preference[THEME]) {
                LIGHT_VALUE -> DARK_VALUE
                else -> LIGHT_VALUE
            }
        }
    }

    /**
     * Here usage of global scope, because theming is singleton that's scoped to all app
     */
    @DelicateCoroutinesApi
    override val currentTheme: StateFlow<Theme> = _currentTheme.stateIn(GlobalScope, SharingStarted.Lazily, context.systemTheme())


    companion object {
        val THEME = intPreferencesKey("THEME")
        fun Context.systemTheme() : Theme {
            return if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES) {
                DARK
            } else {
                LIGHT
            }
        }
    }

}