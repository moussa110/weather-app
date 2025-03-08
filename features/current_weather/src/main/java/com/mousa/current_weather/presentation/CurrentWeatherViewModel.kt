package com.mousa.current_weather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mousa.current_weather.domain.model.WeatherData
import com.mousa.core.domain.util.BaseState
import com.mousa.current_weather.domain.usecases.GetCurrentWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(private val useCase: GetCurrentWeatherUseCase) :
    ViewModel() {

    private val _currentWeatherState: MutableStateFlow<BaseState<WeatherData>> =
        MutableStateFlow(BaseState.Loading)
    val currentWeatherState = _currentWeatherState.asStateFlow()

    fun fetchCurrentWeather(cityName: String) {
        viewModelScope.launch {
            useCase.invoke(cityName)
                .onEach {
                    _currentWeatherState.value = it
                }.launchIn(viewModelScope)
        }
    }
}