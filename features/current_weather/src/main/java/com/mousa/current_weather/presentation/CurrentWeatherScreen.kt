package com.mousa.current_weather.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mousa.current_weather.domain.model.WeatherData
import com.mousa.core.ui.componenets.HandleState
import com.mousa.core.ui.componenets.WeatherDetailsCards
import com.mousa.core.ui.componenets.WeatherAsyncImage
import com.mousa.core.ui.navigation.NavigationScreens


@Composable
fun CurrentWeatherScreen(
    navController: NavController,
    cityName: String,
    modifier: Modifier = Modifier,
    viewModel: CurrentWeatherViewModel = hiltViewModel()
) {
    val state = viewModel.currentWeatherState.collectAsState()
    LaunchedEffect(cityName) {
        viewModel.fetchCurrentWeather(cityName)
    }
    HandleState(state = state.value, onRetry = {
        viewModel.fetchCurrentWeather(cityName)
    }, content = { weather ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SelectedCityData(currentWeather = weather) {
                navController.navigate(NavigationScreens.EnterCity.route)
            }

            Spacer(Modifier.height(4.dp))

            CurrentWeatherItem(currentWeather = weather)

            WeatherDetailsCards(modifier = Modifier.padding(vertical = 16.dp),
                maxTemp = weather.tempMax,
                minTemp = weather.tempMin,
                sunrise = weather.sunrise,
                sunset = weather.sunset)

            Text(
                text = "7-Day Forecast",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .clickable {
                        navController.navigate(NavigationScreens.Forecast.createRoute(weather.city))
                    }
                    .padding(8.dp)
            )
        }
    })
}

@Composable
fun SelectedCityData(
    modifier: Modifier = Modifier,
    currentWeather: WeatherData,
    onclick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onclick()
            }, horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = Icons.Rounded.LocationOn,
            tint =  MaterialTheme.colorScheme.onBackground,
            contentDescription = null
        )

        Spacer(modifier.width(8.dp))

        Text(
            text = "${currentWeather.city}, ${currentWeather.country}",
            style = MaterialTheme.typography.titleMedium,
        )

    }
}

@Composable
fun CurrentWeatherItem(
    modifier: Modifier = Modifier,
    currentWeather: WeatherData
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        WeatherAsyncImage(Modifier.size(120.dp), currentWeather.icon)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = currentWeather.degree,
            style = MaterialTheme.typography.displayMedium,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Weather Status:${currentWeather.status}",
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Wind speed:${currentWeather.windSpeed} ${currentWeather.windDirection} ",
            style = MaterialTheme.typography.bodyMedium,
        )
    }

}

