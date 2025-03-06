package com.mousa.data.remote.model


import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("cod") val cod: String,
    @SerializedName("message") val message: Int,
    @SerializedName("cnt") val cnt: Int,
    @SerializedName("list") val forecastList: List<WeatherForecast>,
    @SerializedName("city") val city: CityInfo
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