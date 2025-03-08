package com.mousa.current_weather.domain.model

data class WeatherData(
    val icon: String,
    val degree:String,
    val status:String,
    val windSpeed:String,
    val windDirection:String,
    val tempMax:String,
    val tempMin:String,
    val city: String,
    val country: String,
    val sunrise: String,
    val sunset: String,
    val latitude: Double,
    val longitude: Double,
)
