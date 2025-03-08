package com.mousa.core.ui.navigation

import com.mousa.core.utils.Constants.ARG_CITY_NAME

sealed class NavigationScreens(val route: String) {
    data object EnterCity : NavigationScreens("enter_city_screen")

    data object Weather : NavigationScreens("weather_screen/{$ARG_CITY_NAME}") {
        fun createRoute(cityName: String): String = "weather_screen/$cityName"
    }
    data object Forecast : NavigationScreens("forecast_screen/{$ARG_CITY_NAME}") {
        fun createRoute(cityName: String) = "forecast_screen/$cityName"
    }
}