package com.mousa.data.remote.model

import com.google.gson.annotations.SerializedName

data class Coordinates(
    @SerializedName("lon") val lon: Double,
    @SerializedName("lat") val lat: Double
)

data class WeatherCondition(
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)

data class MainWeatherData(
    @SerializedName("temp") val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("sea_level") val seaLevel: Int? = null,
    @SerializedName("grnd_level") val groundLevel: Int? = null
)

data class WindInfo(
    @SerializedName("speed") val speed: Double,
    @SerializedName("deg") val deg: Int,
    @SerializedName("gust") val gust: Double? = null
)

data class CloudInfo(
    @SerializedName("all") val cloudiness: Int
)

data class SysInfo(
    @SerializedName("pod") val pod: String? = null,
    @SerializedName("country") val country: String? = null,
    @SerializedName("sunrise") val sunrise: Long? = null,
    @SerializedName("sunset") val sunset: Long? = null
)

data class CityInfo(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("name") var name: String = "",
    @SerializedName("coord") var coord: Coordinates? = null,
    @SerializedName("country") var country: String = "",
    @SerializedName("population") var population: Int = 0,
    @SerializedName("timezone") var timezone: Int = 0,
    @SerializedName("sunrise") var sunrise: Long = 0,
    @SerializedName("sunset") var sunset: Long = 0
)