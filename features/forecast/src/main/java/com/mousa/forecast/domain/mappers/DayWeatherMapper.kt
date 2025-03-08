package com.mousa.forecast.domain.mappers

import com.mousa.core.ui.utils.formatTime
import com.mousa.core.ui.utils.getShortDayNameFromText
import com.mousa.data.remote.model.ForecastResponse
import com.mousa.forecast.domain.model.DayCardInfo
import com.mousa.forecast.domain.model.DayWeather
import com.mousa.forecast.domain.model.DayWeatherDetails
import com.mousa.weatherhelpers.WeatherIconUtils
import com.mousa.weatherhelpers.WeatherUtils


fun ForecastResponse.mapToDayWeatherList(): List<DayWeather> {
    return forecastList.map { dailyItem ->
        DayWeather(
            dayCardInfo = DayCardInfo(
                day = getShortDayNameFromText(dailyItem.dateTime),
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