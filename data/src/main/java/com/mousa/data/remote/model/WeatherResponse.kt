package com.mousa.data.remote.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("coord") val coord: Coordinates,
    @SerializedName("weather") val weather: List<WeatherCondition>,
    @SerializedName("base") val base: String,
    @SerializedName("main") val main: MainWeatherData,
    @SerializedName("visibility") val visibility: Int,
    @SerializedName("wind") val wind: WindInfo,
    @SerializedName("clouds") val clouds: CloudInfo,
    @SerializedName("dt") val timestamp: Long,
    @SerializedName("sys") val sys: SysInfo,
    @SerializedName("timezone") val timezone: Int,
    @SerializedName("id") val cityId: Int,
    @SerializedName("name") val cityName: String,
    @SerializedName("cod") val cod: Int
)
