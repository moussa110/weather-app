package com.mousa.forecast.viewmodel

import app.cash.turbine.test
import com.mousa.core.domain.util.BaseState
import com.mousa.forecast.domain.model.DayCardInfo
import com.mousa.forecast.domain.model.DayWeather
import com.mousa.forecast.domain.model.DayWeatherDetails
import com.mousa.forecast.domain.usecases.GetForecastUseCase
import com.mousa.forecast.presentation.intent.ForecastIntent
import com.mousa.forecast.presentation.screen.ForecastViewModel
import com.mousa.forecast.presentation.state.ForecastState
import com.mousa.forecast.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ForecastViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: ForecastViewModel
    private val useCase: GetForecastUseCase = mockk(relaxed = true)

    @Before
    fun setUp() {
        viewModel = ForecastViewModel(useCase)
    }

    @Test
    fun `fetchForecast should update state with success`() = runTest {
        val mockDayWeatherList = listOf(
            DayWeather(
                dayCardInfo = DayCardInfo("Mon", "25°C", "icon_url"),
                dayWeatherDetails = DayWeatherDetails("30°C", "20°C", "06:00 AM", "07:00 PM")
            )
        )

        coEvery { useCase.invoke("Cairo") } returns flowOf(BaseState.Success(mockDayWeatherList))

        viewModel.state.test {
            viewModel.processIntent(ForecastIntent.FetchForecast("Cairo"))
            advanceUntilIdle()

            assert(awaitItem() is ForecastState.Loading)
            val successState = awaitItem()
            assert(successState is ForecastState.Success)
            assertEquals(mockDayWeatherList.map { it.dayCardInfo }, (successState as ForecastState.Success).forecastList)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchForecast should update state with error`() = runTest {
        coEvery { useCase.invoke("Cairo") } returns flowOf(BaseState.Failure("Network Error"))

        viewModel.state.test {
            viewModel.processIntent(ForecastIntent.FetchForecast("Cairo"))
            advanceUntilIdle()

            assert(awaitItem() is ForecastState.Loading)
            val errorState = awaitItem()
            assert(errorState is ForecastState.Error)
            assertEquals("Network Error", (errorState as ForecastState.Error).message)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
