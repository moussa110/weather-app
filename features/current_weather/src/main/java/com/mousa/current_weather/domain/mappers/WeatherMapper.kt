package com.mousa.current_weather.domain.mappers

import com.mousa.core.ui.utils.formatTime
import com.mousa.current_weather.domain.model.WeatherData
import com.mousa.data.remote.model.WeatherResponse
import com.mousa.weatherhelpers.WeatherIconUtils
import com.mousa.weatherhelpers.WeatherUtils

fun WeatherResponse.mapToWeatherData(): WeatherData {
    return WeatherData(
        icon = WeatherIconUtils.getIconUrl(
            weather?.firstOrNull()?.icon
                ?: WeatherIconUtils.WeatherCondition.CLEAR_SKY_DAY.iconCode,
            WeatherIconUtils.IconSize.XXXX
        ),
        degree = WeatherUtils.formatTemperature(main?.temp ?: 0.0, true),
        status = weather?.firstOrNull()?.description ?: "Unknown",
        windSpeed = "${wind?.speed} Km/h",
        windDirection = WeatherUtils.getWindDirection(wind?.deg ?: 0),
        tempMax = WeatherUtils.formatTemperature(main?.tempMax ?: 0.0, true),
        tempMin = WeatherUtils.formatTemperature(main?.tempMin ?: 0.0, true),
        city = cityName,
        country = sys?.country ?: "Unknown",
        sunrise = formatTime(sys?.sunrise ?: 0),
        sunset = formatTime(sys?.sunset ?: 0),
        latitude = coord?.lat ?: 0.0,
        longitude =coord?.lon ?: 0.0
    )
}