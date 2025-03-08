package com.mousa.current_weather.usecase

import com.mousa.core.domain.util.BaseState
import com.mousa.core.domain.util.Resource
import com.mousa.current_weather.domain.mappers.mapToWeatherData
import com.mousa.current_weather.domain.usecases.GetCurrentWeatherUseCase
import com.mousa.data.remote.model.WeatherResponse
import com.mousa.data.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetCurrentWeatherUseCaseTest {

    private lateinit var useCase: GetCurrentWeatherUseCase
    private val repository: WeatherRepository = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        useCase = GetCurrentWeatherUseCase(repository)
    }

    @Test
    fun `invoke should return success state when repository returns success`() = runTest {
        val mockWeatherResponse = mockk<WeatherResponse>(relaxed = true)
        val mockWeatherData = mockWeatherResponse.mapToWeatherData()

        coEvery { repository.getCurrentWeather("Cairo") } returns Resource.Success(mockWeatherResponse)

        val result = useCase("Cairo").first()

        assert(result is BaseState.Success)
        assertEquals(mockWeatherData, (result as BaseState.Success).data)
    }

    @Test
    fun `invoke should return failure state when repository returns failure`() = runTest {
        coEvery { repository.getCurrentWeather("Cairo") } returns Resource.Failure()

        val result = useCase("Cairo").first()

        assert(result is BaseState.Failure)

    }
}
