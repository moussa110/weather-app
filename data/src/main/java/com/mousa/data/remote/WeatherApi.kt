package com.mousa.data.remote

import com.mousa.data.remote.model.ForecastResponse
import com.mousa.data.remote.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String
    ): Response<WeatherResponse>

    @GET("forecast")
    suspend fun getWeatherForecast(
        @Query("q") city: String,
        @Query("cnt") days: Int = 7,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String
    ): Response<ForecastResponse>
}