package com.mousa.forecast.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mousa.core.ui.componenets.HandleState
import com.mousa.core.ui.componenets.WeatherAsyncImage
import com.mousa.core.ui.componenets.WeatherDetailsCards
import com.mousa.forecast.domain.model.DayCardInfo
import com.mousa.forecast.domain.model.DayWeatherDetails
import com.mousa.forecast.presentation.intent.ForecastIntent

@Composable
fun ForecastScreen(
    modifier: Modifier = Modifier,
    city: String,
    viewModel: ForecastViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    LaunchedEffect(city) {
        viewModel.processIntent(ForecastIntent.FetchForecast(city))
    }
    HandleState(
        state = state.value.mapItToBaseState(),
        onRetry = { viewModel.processIntent(ForecastIntent.FetchForecast(city)) }) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Forecast",
                style = MaterialTheme.typography.headlineLarge,
            )

            Spacer(modifier = Modifier.height(16.dp))

            DaysList(
                data = it.first,
                viewModel = viewModel
            )

            it.second?.let { details ->
                Spacer(modifier = Modifier.height(16.dp))
                DayWeatherDetailsCards(details)
            }
        }
    }
}

@Composable
fun DaysList(
    modifier: Modifier = Modifier,
    data: List<DayCardInfo>,
    viewModel: ForecastViewModel
) {
    var selectedWeatherIndex by remember { mutableIntStateOf(0) }
    LazyRow {
        itemsIndexed(items = data) { index, day ->
            val selectedColor =
                if (selectedWeatherIndex == index) MaterialTheme.colorScheme.inverseSurface
                else CardDefaults.cardColors().containerColor
            DailyWeatherInfoItem(
                day = day,
                backgroundColor = selectedColor,
                onClick = {
                    if (selectedWeatherIndex != index){
                        selectedWeatherIndex = index
                        viewModel.processIntent(ForecastIntent.SelectDay(day))
                    }
                }
            )
        }
    }
}

@Composable
fun DailyWeatherInfoItem(
    modifier: Modifier = Modifier,
    day: DayCardInfo,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier.padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = day.temp,
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.height(8.dp))

            WeatherAsyncImage(iconUrl = day.iconUrl, modifier = Modifier.size(48.dp))

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = day.day,
                style = MaterialTheme.typography.bodySmall,
            )

        }

    }

}

@Composable
fun DayWeatherDetailsCards(details: DayWeatherDetails) {
    WeatherDetailsCards(
        Modifier.padding(top = 16.dp),
        details.tempMax,
        details.tempMin,
        details.sunrise,
        details.sunset
    )
}