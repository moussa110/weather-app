package com.mousa.forecast.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mousa.core.domain.util.BaseState
import com.mousa.forecast.domain.model.DayCardInfo
import com.mousa.forecast.domain.model.DayWeather
import com.mousa.forecast.domain.usecases.GetForecastUseCase
import com.mousa.forecast.presentation.intent.ForecastIntent
import com.mousa.forecast.presentation.state.ForecastState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val getForecastUseCase: GetForecastUseCase
) : ViewModel() {

    private lateinit var daysWeather: List<DayWeather>

    private val _state = MutableStateFlow<ForecastState>(ForecastState.Loading)
    val state: StateFlow<ForecastState> = _state.asStateFlow()


    fun processIntent(intent: ForecastIntent) {
        when (intent) {
            is ForecastIntent.FetchForecast -> fetchForecast(intent.city)
            is ForecastIntent.SelectDay -> selectDay(intent.day)
        }
    }

    private fun fetchForecast(city: String) {
        viewModelScope.launch {
            getForecastUseCase(city).onEach { result ->
                _state.value = when (result) {
                    is BaseState.Loading -> ForecastState.Loading
                    is BaseState.Success -> {
                        daysWeather = result.data
                        ForecastState.Success(daysWeather.map { it.dayCardInfo },daysWeather.first().dayWeatherDetails)
                    }

                    is BaseState.Failure -> ForecastState.Error(result.errorMessage)
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun selectDay(day: DayCardInfo) {
        daysWeather.find { it.dayCardInfo == day }?.also { weather ->
            _state.value = ForecastState.Success(daysWeather.map { it.dayCardInfo },weather.dayWeatherDetails)
        }
    }
}