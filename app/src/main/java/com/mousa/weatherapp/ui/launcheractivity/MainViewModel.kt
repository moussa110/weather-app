package com.mousa.weatherapp.ui.launcheractivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mousa.city_input.usecases.GetLastSearchedCityNameUseCase
import com.mousa.core.domain.util.BaseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(private val getCityUseCase: GetLastSearchedCityNameUseCase) :
    ViewModel() {
        private val _cityName = MutableStateFlow<BaseState<String>>(BaseState.Loading)
        val cityName = _cityName.asStateFlow()

        fun loadStartDestination() {
            viewModelScope.launch(Dispatchers.IO) {
                when (val result = getCityUseCase()) {
                    is BaseState.Success -> {
                        _cityName.value =
                            if (!result.data.isNullOrEmpty()) {
                                BaseState.Success(result.data!!)
                            } else {
                                BaseState.Failure(null)
                            }
                    }

                    else -> _cityName.value = result as BaseState.Failure
                }
            }
        }
    }