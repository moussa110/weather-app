package com.mousa.weatherhelpers

import java.util.Locale

object WeatherUtils {
    fun formatTemperature(temp: Double, isCelsius: Boolean = true): String {
        val temperature = if (isCelsius) temp else temp * 9 / 5 + 32
        val unit = if (isCelsius) "°C" else "°F"
        return String.format(Locale.getDefault(), "%.1f%s", temperature, unit)
    }

    fun getWindDirection(deg: Int): String {
        return when (deg) {
            in 0..22 -> "North"
            in 23..67 -> "North East"
            in 68..112 -> "East"
            in 113..157 -> "South East"
            in 158..202 -> "South"
            in 203..247 -> "South West"
            in 248..292 -> "West"
            in 293..337 -> "North West"
            else -> "North"
        }
    }
}