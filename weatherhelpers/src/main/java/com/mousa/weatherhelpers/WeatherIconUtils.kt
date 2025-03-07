package com.mousa.weatherhelpers

enum class WeatherCondition(val iconCode: String) {
    CLEAR_SKY_DAY("01d"),
    CLEAR_SKY_NIGHT("01n"),
    FEW_CLOUDS_DAY("02d"),
    FEW_CLOUDS_NIGHT("02n"),
    SCATTERED_CLOUDS("03d"),
    BROKEN_CLOUDS("04d"),
    SHOWER_RAIN("09d"),
    RAIN("10d"),
    THUNDERSTORM("11d"),
    SNOW("13d"),
    MIST("50d");
}

fun getIconUrl(code:String): String = "https://openweathermap.org/img/wn/$code@2x.png"

fun getIconUrl(weatherCondition: WeatherCondition): String = "https://openweathermap.org/img/wn/${weatherCondition.iconCode}@2x.png"

