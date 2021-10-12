package com.pikalov.compose.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.pikalov.compose.features.ThemeRepository
import com.pikalov.compose.features.ThemeRepository.Theme.*
import timber.log.Timber

//TODO(color usage by material guidelines)
private val DarkColorPalette = darkColors(
    primary = White,
    primaryVariant = Purple700,
    secondary = Black12,
)

private val LightColorPalette = lightColors(
    primary = Black12,
    primaryVariant = Purple700,
    secondary = White,
    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun JetpackComposeLearningTheme(
    theme: ThemeRepository.Theme,
    content: @Composable() () -> Unit
) {
    val colors = when(theme) {
        LIGHT -> LightColorPalette
        DARK -> DarkColorPalette
    }
    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}