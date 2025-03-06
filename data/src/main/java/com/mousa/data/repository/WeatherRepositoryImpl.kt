package com.mousa.data.repository

import com.mousa.core.utils.Resource
import com.mousa.data.local.CityDao
import com.mousa.data.local.WeatherLocalDataSource
import com.mousa.data.remote.WeatherApi
import com.mousa.data.remote.WeatherRemoteDataSource
import com.mousa.data.remote.model.ForecastResponse
import com.mousa.data.remote.model.WeatherResponse
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val localDataSource: WeatherLocalDataSource
) : WeatherRepository {

    override suspend fun getCurrentWeather(city: String): Resource<WeatherResponse> {
        return remoteDataSource.fetchCurrentWeather(city).also { result ->
            if (result is Resource.Success) localDataSource.saveLastSearchedCity(city)
        }
    }

    override suspend fun getWeatherForecast(city: String): Resource<ForecastResponse> {
        return remoteDataSource.fetchWeatherForecast(city)
    }

    override suspend fun getLastSearchedCity(): Resource<String?> {
        return localDataSource.getLastSearchedCity()
    }

    override suspend fun saveLastSearchedCity(city: String): Resource<Unit> {
        return localDataSource.saveLastSearchedCity(city)
    }
}