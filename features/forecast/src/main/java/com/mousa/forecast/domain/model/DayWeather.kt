package com.mousa.forecast.domain.model

data class DayWeather(
    val dayCardInfo: DayCardInfo,
    val dayWeatherDetails: DayWeatherDetails
)

data class DayCardInfo(
    val day: String,
    val temp: String,
    val iconUrl: String
)

data class DayWeatherDetails(
    val tempMax: String,
    val tempMin: String,
    val sunrise: String,
    val sunset: String
)