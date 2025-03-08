package com.mousa.current_weather.domain.usecases

import com.mousa.core.domain.mapper.toBaseState
import com.mousa.core.domain.util.BaseState
import com.mousa.core.domain.util.Resource
import com.mousa.current_weather.domain.mappers.mapToWeatherData
import com.mousa.current_weather.domain.model.WeatherData
import com.mousa.data.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(private val repository: WeatherRepository) {
    operator fun invoke(city: String): Flow<BaseState<WeatherData>> = flow {
        when (val result = repository.getCurrentWeather(city)) {
            is Resource.Success -> emit(BaseState.Success(result.value.mapToWeatherData()))
            is Resource.Failure -> emit(result.toBaseState())
        }
    }.flowOn(Dispatchers.IO)
}