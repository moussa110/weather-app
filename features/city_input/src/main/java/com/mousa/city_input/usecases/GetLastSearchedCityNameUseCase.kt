package com.mousa.city_input.usecases

import com.mousa.core.domain.mapper.toBaseState
import com.mousa.core.domain.util.BaseState
import com.mousa.data.repository.WeatherRepository
import javax.inject.Inject

class GetLastSearchedCityNameUseCase @Inject constructor(private val repository: WeatherRepository){
    suspend operator fun invoke(): BaseState<String?> {
        return repository.getLastSearchedCity().toBaseState()
    }
}