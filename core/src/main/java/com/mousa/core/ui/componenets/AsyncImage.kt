package com.mousa.core.ui.componenets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mousa.core.R

@Composable
fun WeatherAsyncImage(modifier: Modifier = Modifier, iconUrl:String){
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(iconUrl)
            .crossfade(true)
            .build(),
        contentDescription = "Weather Icon",
        modifier = modifier,
        placeholder = painterResource(R.drawable.place_holder)
    )
}
