package com.mousa.core.ui.componenets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun WeatherDetailsCards(
    modifier: Modifier = Modifier,
    maxTemp: String,
    minTemp: String,
    sunrise: String,
    sunset: String
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CardWithTitleAndSubtitle("Max Temp", maxTemp, Modifier.weight(1f))
            CardWithTitleAndSubtitle("Min Temp", minTemp, Modifier.weight(1f))
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CardWithTitleAndSubtitle("Sunrise", sunrise, Modifier.weight(1f))
            CardWithTitleAndSubtitle("Sunset", sunset, Modifier.weight(1f))
        }
    }
}

@Composable
fun CardWithTitleAndSubtitle(title: String, subtitle: String, modifier: Modifier) {
    Card(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .aspectRatio(1f),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = subtitle,
                style = MaterialTheme.typography.displayMedium,
            )
        }
    }
}