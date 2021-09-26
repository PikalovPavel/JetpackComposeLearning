package com.pikalov.compose.util

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import timber.log.Timber
import java.lang.Exception

fun Fragment.navigateSafe(direction: NavDirections) {
    try {
        findNavController().navigate(direction)
    } catch (e: Exception) {
        Timber.e(e)
    }
}