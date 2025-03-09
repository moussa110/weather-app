package com.mousa.forecast.domain.mappers

import com.mousa.core.ui.utils.formatTime
import com.mousa.data.remote.model.ForecastResponse
import com.mousa.forecast.domain.model.DayCardInfo
import com.mousa.forecast.domain.model.DayWeather
import com.mousa.forecast.domain.model.DayWeatherDetails
import com.mousa.weatherhelpers.WeatherIconUtils
import com.mousa.weatherhelpers.WeatherUtils
import java.time.LocalDate
import java.util.Locale


fun ForecastResponse.mapToDayWeatherList(): List<DayWeather> {
    return forecastList.mapIndexed { index, dailyItem ->
        val formattedDay = getDayNameFromPosition(index)
        DayWeather(
            dayCardInfo = DayCardInfo(
                day = formattedDay,
                temp = WeatherUtils.formatTemperature(dailyItem.main.temp, true),
                iconUrl = WeatherIconUtils.getIconUrl(
                    dailyItem.weather.firstOrNull()?.icon
                        ?: WeatherIconUtils.WeatherCondition.CLEAR_SKY_DAY.iconCode,
                    WeatherIconUtils.IconSize.XXXX
                )
            ),
            dayWeatherDetails =  DayWeatherDetails(
                tempMax = WeatherUtils.formatTemperature(dailyItem.main.tempMax, true),
                tempMin = WeatherUtils.formatTemperature(dailyItem.main.tempMin, true),
                sunrise = formatTime(city?.sunrise ?: 0),
                sunset = formatTime(city?.sunset ?: 0)
            )
        )
    }
}
fun getDayNameFromPosition(position: Int): String {
    val today = LocalDate.now().plusDays(position.toLong())
    return today.dayOfWeek.getDisplayName(java.time.format.TextStyle.SHORT, Locale.getDefault())
}
