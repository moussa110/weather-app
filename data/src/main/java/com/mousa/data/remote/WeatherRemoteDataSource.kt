package com.mousa.data.remote

import com.mousa.core.domain.util.Resource
import com.mousa.core.data.utils.safeApiCall
import com.mousa.data.remote.model.ForecastResponse
import com.mousa.data.remote.model.WeatherResponse
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(private val api: WeatherApi) {

    suspend fun fetchCurrentWeather(city: String): Resource<WeatherResponse> {
        return safeApiCall { api.getCurrentWeather(city, apiKey = API_KEY) }
    }

    suspend fun fetchWeatherForecast(city: String): Resource<ForecastResponse> {
        return safeApiCall { api.getWeatherForecast(city, apiKey = API_KEY) }
    }

    companion object {
        private const val API_KEY = "00c2e49e1099aa6e1c154a6c7af271e0"
    }
}