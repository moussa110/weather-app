package com.mousa.data.repository

import com.mousa.core.utils.Resource
import com.mousa.data.remote.model.ForecastResponse
import com.mousa.data.remote.model.WeatherResponse

interface WeatherRepository {
    suspend fun getCurrentWeather(city: String): Resource<WeatherResponse>
    suspend fun getWeatherForecast(city: String): Resource<ForecastResponse>
    suspend fun getLastSearchedCity(): Resource<String?>
    suspend fun saveLastSearchedCity(city: String): Resource<Unit>
}
