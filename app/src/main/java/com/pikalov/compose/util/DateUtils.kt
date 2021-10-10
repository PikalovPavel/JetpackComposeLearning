package com.pikalov.compose.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

fun Date.toFormatString(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        DateTimeFormatter.ofPattern("dd MMM yyyy")
            .withLocale(Locale("ru"))
            .format(this.toLocalDate())
    } else {
        SimpleDateFormat("MMMM dd yyyy", Locale.US)
            .format(this)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun Date.toLocalDate(): LocalDate = toInstant()
    .atZone(ZoneId.systemDefault())
    .toLocalDate()