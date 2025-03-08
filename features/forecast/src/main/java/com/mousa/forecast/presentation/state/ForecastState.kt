package com.mousa.forecast.presentation.state

import com.mousa.core.domain.util.BaseState
import com.mousa.forecast.domain.model.DayCardInfo
import com.mousa.forecast.domain.model.DayWeatherDetails

sealed class ForecastState {
    data object Loading : ForecastState()
    data class Error(val message: String?) : ForecastState()
    data class Success(
        val forecastList: List<DayCardInfo>,
        val selectedDetails: DayWeatherDetails? = null
    ) : ForecastState()

    fun mapItToBaseState(): BaseState<Pair<List<DayCardInfo>, DayWeatherDetails?>> {
        return when (this) {
            is Loading -> BaseState.Loading
            is Error -> BaseState.Failure(this.message)
            is Success -> BaseState.Success(Pair(this.forecastList, this.selectedDetails))
        }
    }
}