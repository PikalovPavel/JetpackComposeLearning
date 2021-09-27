package com.pikalov.compose.ui.theme

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.pikalov.compose.R

fun toast(
    context: Context,
    @StringRes string: Int? = null
): Toast = Toast.makeText(context, string ?: R.string.error, Toast.LENGTH_LONG)