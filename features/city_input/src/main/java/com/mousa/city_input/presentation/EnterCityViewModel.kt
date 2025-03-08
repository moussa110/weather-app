package com.mousa.city_input.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mousa.city_input.state.CityInputState
import com.mousa.city_input.usecases.CacheCityUseCase
import com.mousa.core.domain.util.BaseState
import com.mousa.core.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EnterCityViewModel @Inject constructor(private val cacheCityUseCase: CacheCityUseCase) :
    ViewModel() {

    private val _cityState =
        MutableStateFlow<BaseState<CityInputState>>(BaseState.Success(CityInputState.EnterCityName()))
    val cityState: StateFlow<BaseState<CityInputState>> = _cityState.asStateFlow()

    fun updateCityName(newCity: String) {
        _cityState.value = BaseState.Success(CityInputState.EnterCityName(newCity))
    }

    fun saveCity(city: String) = viewModelScope.launch(Dispatchers.IO) {
        _cityState.value = BaseState.Loading
        cacheCityUseCase.invoke(city).let {
            _cityState.value = BaseState.Success(CityInputState.NavigateToWeatherData(city))
        }
    }
}