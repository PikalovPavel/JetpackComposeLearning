package com.pikalov.compose.ui.theme


import androidx.fragment.app.Fragment
import com.pikalov.compose.features.ThemeRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

//TODO(fix to better way of providing theme)
@AndroidEntryPoint
abstract class BaseThemingFragment : Fragment() {

    @Inject
    lateinit var themeRepository: ThemeRepository

    internal val theme by lazy {
        themeRepository.currentTheme
    }

}