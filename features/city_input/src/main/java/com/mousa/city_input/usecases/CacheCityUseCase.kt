package com.mousa.city_input.usecases

import com.mousa.data.repository.WeatherRepository
import javax.inject.Inject

class CacheCityUseCase @Inject constructor(private val repository: WeatherRepository) {
    suspend operator fun invoke(city: String) = repository.saveLastSearchedCity(city)
}