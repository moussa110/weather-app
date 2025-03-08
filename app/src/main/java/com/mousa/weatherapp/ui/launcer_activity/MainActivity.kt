package com.mousa.weatherapp.ui.launcer_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mousa.city_input.presentation.EnterCityScreen
import com.mousa.core.domain.util.BaseState
import com.mousa.core.ui.navigation.NavigationScreens
import com.mousa.core.utils.Constants.ARG_CITY_NAME
import com.mousa.current_weather.presentation.CurrentWeatherScreen
import com.mousa.forecast.presentation.screen.ForecastScreen
import com.mousa.weatherapp.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                val viewModel: MainViewModel = hiltViewModel()
                val startDestination = viewModel.cityName.collectAsState()

                LaunchedEffect(true) {
                    viewModel.loadStartDestination()
                }

                startDestination.value.let {
                    when (it) {
                        is BaseState.Loading -> Loading()
                        is BaseState.Failure -> WeatherApp(cityName = null)
                        is BaseState.Success -> WeatherApp(cityName = it.data)
                    }

                }
            }
        }
    }

    @Composable
    fun Loading() {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    @Composable
    fun WeatherApp(modifier: Modifier = Modifier, cityName: String?) {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = if (cityName == null) NavigationScreens.EnterCity.route else NavigationScreens.Weather.route
        ) {
            composable(NavigationScreens.EnterCity.route) {
                EnterCityScreen(modifier, navController)
            }
            composable(NavigationScreens.Weather.route) {
                val city = it.arguments?.getString(ARG_CITY_NAME) ?: cityName
                CurrentWeatherScreen(navController = navController, cityName = city ?: "Unknown")
            }
            composable(NavigationScreens.Forecast.route) { backStackEntry ->
                val city = backStackEntry.arguments?.getString(ARG_CITY_NAME) ?: cityName
                ForecastScreen(city = city ?: "Unknown")
            }
        }
    }

}

