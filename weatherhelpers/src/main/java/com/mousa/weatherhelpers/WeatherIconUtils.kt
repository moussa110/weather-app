package com.mousa.weatherhelpers

object WeatherIconUtils {
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

    enum class IconSize(val iconSize: String) {
        XX("@2x"),
        XXXX("@4x"),
    }

    fun getIconUrl(code:String,size: IconSize): String = "https://openweathermap.org/img/wn/$code${size.iconSize}.png"

    fun getIconUrl(weatherCondition: WeatherCondition,size: IconSize): String = "https://openweathermap.org/img/wn/${weatherCondition.iconCode}${size.iconSize}.png"
}

