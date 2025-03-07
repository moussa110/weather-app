package com.mousa.data.remote.model


import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("cod") var cod: String="",
    @SerializedName("message") var message: Int=0,
    @SerializedName("cnt") var cnt: Int = 0,
    @SerializedName("list") var forecastList: List<WeatherForecast> = listOf(),
    @SerializedName("city") var city: CityInfo?=null
)

data class WeatherForecast(
    @SerializedName("dt") val timestamp: Long,
    @SerializedName("main") val main: MainWeatherData,
    @SerializedName("weather") val weather: List<WeatherCondition>,
    @SerializedName("clouds") val clouds: CloudInfo,
    @SerializedName("wind") val wind: WindInfo,
    @SerializedName("visibility") val visibility: Int,
    @SerializedName("pop") val pop: Double,
    @SerializedName("sys") val sys: SysInfo,
    @SerializedName("dt_txt") val dateTime: String
)