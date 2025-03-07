package com.mousa.weatherhelpers

import java.util.Locale

object WeatherUtils {
    fun formatTemperature(temp: Double, isCelsius: Boolean = true): String {
        val temperature = if (isCelsius) temp else temp * 9 / 5 + 32
        val unit = if (isCelsius) "°C" else "°F"
        return String.format(Locale.getDefault(), "%.1f%s", temperature, unit)
    }
}