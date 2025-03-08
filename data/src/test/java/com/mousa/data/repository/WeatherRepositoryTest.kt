package com.mousa.data.repository

import com.mousa.core.domain.util.Resource
import com.mousa.data.local.WeatherLocalDataSource
import com.mousa.data.remote.WeatherRemoteDataSource
import com.mousa.data.remote.model.CityInfo
import com.mousa.data.remote.model.ForecastResponse
import com.mousa.data.remote.model.WeatherResponse
import com.mousa.data.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherRepositoryTest {

    @get:Rule
    val testDispatcherRule = MainDispatcherRule()

    private lateinit var repository: WeatherRepository
    private val remoteDataSource: WeatherRemoteDataSource = mockk()
    private val localDataSource: WeatherLocalDataSource = mockk(relaxed = true)

    @Before
    fun setUp() {
        repository = WeatherRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `getCurrentWeather returns success when remote fetch is successful`() = runTest {
        val mockResponse = WeatherResponse(cityName = "Cairo")
        coEvery { remoteDataSource.fetchCurrentWeather("Cairo") } returns Resource.Success(mockResponse)

        val result = repository.getCurrentWeather("Cairo")

        assert(result is Resource.Success)
        assertEquals("Cairo", (result as Resource.Success).value.cityName)
        coVerify { localDataSource.saveLastSearchedCity("Cairo") }
    }

    @Test
    fun `getCurrentWeather returns failure when API fail`() = runTest {
        coEvery { remoteDataSource.fetchCurrentWeather("Cairo") } returns Resource.Failure()

        val result = repository.getCurrentWeather("Cairo")

        assert(result is Resource.Failure)
    }

    @Test
    fun `getWeatherForecast returns success when remote fetch is successful`() = runTest {
        val mockResponse = ForecastResponse(city = CityInfo(name = "Cairo"))
        coEvery { remoteDataSource.fetchWeatherForecast("Cairo") } returns Resource.Success(mockResponse)

        val result = repository.getWeatherForecast("Cairo")

        assert(result is Resource.Success)
        assertEquals("Cairo", (result as Resource.Success).value.city?.name)
    }

    @Test
    fun `getWeatherForecast returns failure when API fails`() = runTest {
        coEvery { remoteDataSource.fetchWeatherForecast("Cairo") } returns Resource.Failure()

        val result = repository.getWeatherForecast("Cairo")

        assert(result is Resource.Failure)
    }

    @Test
    fun `getLastSearchedCity returns data when available`() = runTest {
        coEvery { localDataSource.getLastSearchedCity() } returns Resource.Success("Cairo")

        val result = repository.getLastSearchedCity()

        assert(result is Resource.Success)
        assertEquals("Cairo", (result as Resource.Success).value)
    }

    @Test
    fun `getLastSearchedCity returns failure when data is null`() = runTest {
        coEvery { localDataSource.getLastSearchedCity() } returns Resource.Failure()

        val result = repository.getLastSearchedCity()

        assert(result is Resource.Failure)
    }

    @Test
    fun `saveLastSearchedCity saves the city successfully`() = runTest {
        coEvery { localDataSource.saveLastSearchedCity("Cairo") } returns Resource.Success(Unit)

        val result = repository.saveLastSearchedCity("Cairo")

        assert(result is Resource.Success)
        coVerify { localDataSource.saveLastSearchedCity("Cairo") }
    }
}