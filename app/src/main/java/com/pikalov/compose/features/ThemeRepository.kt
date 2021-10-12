package com.pikalov.compose.features

import kotlinx.coroutines.flow.StateFlow


interface ThemeRepository {

    suspend fun switchTheme()
    val currentTheme : StateFlow<Theme>

    enum class Theme(val value: Int) {
        LIGHT(LIGHT_VALUE),
        DARK(DARK_VALUE);

        companion object {

            fun fromInt(value: Int?) : Theme? = values().firstOrNull() { it.value == value }
        }
    }

    companion object {
        const val LIGHT_VALUE = 0
        const val DARK_VALUE = 1
    }
}