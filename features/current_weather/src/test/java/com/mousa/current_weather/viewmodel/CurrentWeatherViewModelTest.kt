package com.mousa.current_weather.viewmodel

import app.cash.turbine.test
import com.mousa.core.domain.util.BaseState
import com.mousa.current_weather.domain.model.WeatherData
import com.mousa.current_weather.domain.usecases.GetCurrentWeatherUseCase
import com.mousa.current_weather.presentation.CurrentWeatherViewModel
import com.mousa.current_weather.utils.MainDispatcherRule
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
class CurrentWeatherViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: CurrentWeatherViewModel
    private val useCase: GetCurrentWeatherUseCase = mockk()

    @Before
    fun setUp() {
        viewModel = CurrentWeatherViewModel(useCase)
    }

    @Test
    fun `fetchCurrentWeather should update state with success`() = runTest {
        val mockWeatherData = mockk<WeatherData>(relaxed = true)
        coEvery { useCase.invoke("Cairo") } returns flowOf(BaseState.Success(mockWeatherData))

        viewModel.currentWeatherState.test {
            viewModel.fetchCurrentWeather("Cairo")

            assert(awaitItem() is BaseState.Loading)
            val successState = awaitItem()
            assert(successState is BaseState.Success)
            assertEquals(mockWeatherData, (successState as BaseState.Success).data)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchCurrentWeather should update state with error`() = runTest {
        coEvery { useCase.invoke("Cairo") } returns flowOf(BaseState.Failure("API Error"))

        viewModel.currentWeatherState.test {
            viewModel.fetchCurrentWeather("Cairo")

            assert(awaitItem() is BaseState.Loading)
            val errorState = awaitItem()
            assert(errorState is BaseState.Failure)
            assertEquals("API Error", (errorState as BaseState.Failure).errorMessage)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchCurrentWeather should call useCase`() = runTest {
        coEvery { useCase.invoke("Cairo") } returns flowOf(BaseState.Success(mockk(relaxed = true)))

        viewModel.fetchCurrentWeather("Cairo")
        advanceUntilIdle()

        coVerify(exactly = 1) { useCase.invoke("Cairo") }
    }
}

