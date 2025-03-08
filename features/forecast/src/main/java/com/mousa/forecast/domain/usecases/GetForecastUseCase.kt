package com.mousa.forecast.domain.usecases

import com.mousa.core.domain.mapper.toBaseState
import com.mousa.core.domain.util.BaseState
import com.mousa.core.domain.util.Resource
import com.mousa.data.repository.WeatherRepository
import com.mousa.forecast.domain.mappers.mapToDayWeatherList
import com.mousa.forecast.domain.model.DayWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(private val repository: WeatherRepository) {
    operator fun invoke(city: String): Flow<BaseState<List<DayWeather>>> = flow {
        when (val result = repository.getWeatherForecast(city)) {
            is Resource.Success -> emit(BaseState.Success(result.value.mapToDayWeatherList()))
            is Resource.Failure -> emit(result.toBaseState())
        }
    }.flowOn(Dispatchers.IO)
}