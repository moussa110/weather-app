package com.mousa.forecast.presentation.intent

import com.mousa.forecast.domain.model.DayCardInfo
import com.mousa.forecast.domain.model.DayWeather

sealed class ForecastIntent {
    data class FetchForecast(val city: String) : ForecastIntent()
    data class SelectDay(val day: DayCardInfo) : ForecastIntent()
}