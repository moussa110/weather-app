package com.mousa.city_input.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mousa.city_input.state.CityInputState
import com.mousa.core.domain.util.BaseState
import com.mousa.core.ui.componenets.HandleState
import com.mousa.core.ui.navigation.NavigationScreens


@Composable
fun EnterCityScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val viewModel: EnterCityViewModel = hiltViewModel()
    val cityState = viewModel.cityState.collectAsState()

    LaunchedEffect(cityState.value) {
        if (cityState.value is BaseState.Success) {
            if ((cityState.value as BaseState.Success<CityInputState>).data is CityInputState.NavigateToWeatherData) {
                val state =
                    (cityState.value as BaseState.Success<CityInputState>).data as CityInputState.NavigateToWeatherData
                navController.navigate(NavigationScreens.Weather.createRoute(state.cityName))

            }
        }
    }
    HandleState (state = cityState.value) { state ->
        if (state is CityInputState.EnterCityName) {
            Column(
                modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Enter City", style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = state.cityName,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { viewModel.updateCityName(it) },
                    label = { Text(text = "City Name") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (state.cityName.isNotEmpty()) {
                            viewModel.saveCity(state.cityName)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = state.cityName.isNotEmpty(),
                    content = { Text("Get Weather") }
                )
            }
        }
    }
}