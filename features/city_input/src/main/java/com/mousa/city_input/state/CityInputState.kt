package com.mousa.city_input.state

sealed class CityInputState {
    data class EnterCityName(var cityName: String = ""): CityInputState()
    data class NavigateToWeatherData(val cityName: String): CityInputState()
}