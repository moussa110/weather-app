package com.mousa.data.remote.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("coord") var coord: Coordinates? = null,
    @SerializedName("weather") var weather: List<WeatherCondition>? = null,
    @SerializedName("base") var base: String = "",
    @SerializedName("main") var main: MainWeatherData? = null,
    @SerializedName("visibility") var visibility: Int = 0,
    @SerializedName("wind") var wind: WindInfo? = null,
    @SerializedName("clouds") var clouds: CloudInfo? = null,
    @SerializedName("dt") var timestamp: Long = 0,
    @SerializedName("sys") var sys: SysInfo? = null,
    @SerializedName("timezone") var timezone: Int = 0,
    @SerializedName("id") var cityId: Int = 0,
    @SerializedName("name") var cityName: String,
    @SerializedName("cod") var cod: Int = 0
)
