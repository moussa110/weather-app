package com.mousa.data.local

import com.mousa.core.domain.util.Resource
import com.mousa.core.data.utils.safeCall
import javax.inject.Inject

class WeatherLocalDataSource @Inject constructor(private val cityDao: CityDao) {
    suspend fun getLastSearchedCity(): Resource<String?> {
        return safeCall { cityDao.getLastSearchedCity() }
    }

    suspend fun saveLastSearchedCity(city: String): Resource<Unit> {
       return safeCall { cityDao.saveLastSearchedCity(CityEntity(city)) }
    }
}