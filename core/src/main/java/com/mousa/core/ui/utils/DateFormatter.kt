package com.mousa.core.ui.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun formatUnixDate(pattern: String = "EEEE, MMM dd", time: Long): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(time * 1000)
}

fun formatUnixTime(pattern: String = "HH:mm", time: Long): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(time * 1000)
}


fun formatDate(timestamp: Long, pattern: String = "EEEE, MMM dd"): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(Date(timestamp * 1000))
}

fun getShortDayNameFromText(dtTxt: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputFormatter = DateTimeFormatter.ofPattern("E", Locale.getDefault())

    val dateTime = LocalDateTime.parse(dtTxt, inputFormatter)
    return dateTime.format(outputFormatter)
}

fun formatTime(timestamp: Long, pattern: String = "HH:mm"): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(Date(timestamp * 1000))
}