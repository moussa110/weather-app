package com.mousa.data.remote

import com.mousa.core.utils.NetworkError
import com.mousa.core.utils.Resource
import com.mousa.data.remote.model.CityInfo
import com.mousa.data.remote.model.ForecastResponse
import com.mousa.data.remote.model.WeatherResponse
import com.mousa.data.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.io.IOException

@ExperimentalCoroutinesApi
class WeatherRemoteDataSourceTest {

    @get:Rule
    val testDispatcherRule = MainDispatcherRule()

    private lateinit var remoteDataSource: WeatherRemoteDataSource
    private val api: WeatherApi = mockk()

    @Before
    fun setUp() {
        remoteDataSource = WeatherRemoteDataSource(api)
    }

    @Test
    fun `fetchCurrentWeather returns success when API responds successfully`() = runTest {
        val mockResponse = WeatherResponse(cityName = "Cairo")
        coEvery { api.getCurrentWeather(city = "Cairo", apiKey = any()) } returns Response.success(mockResponse)

        val result = remoteDataSource.fetchCurrentWeather("Cairo")

        assert(result is Resource.Success)
        assertEquals("Cairo", (result as Resource.Success).value.cityName)
    }

    @Test
    fun `fetchCurrentWeather returns failure when API responds with server error`() = runTest {
        coEvery { api.getCurrentWeather(any(), any(), any()) } returns
                Response.error(500, "".toResponseBody())

        val result = remoteDataSource.fetchCurrentWeather("Cairo")

        assert(result is Resource.Failure)
        assert((result as Resource.Failure).networkError is NetworkError.ServerError)
    }

    @Test
    fun `fetchCurrentWeather returns failure when network connection fails`() = runTest {
        coEvery { api.getCurrentWeather(any(), any(), any()) } throws IOException("No Internet")

        val result = remoteDataSource.fetchCurrentWeather("Cairo")

        assert(result is Resource.Failure)
        assert((result as Resource.Failure).networkError is NetworkError.NetworkConnection)
    }

    @Test
    fun `fetchWeatherForecast returns success when API responds successfully`() = runTest {
        val mockResponse = ForecastResponse(city = CityInfo(name = "Cairo"))
        coEvery { api.getWeatherForecast(city = "Cairo", days = any(), apiKey = any()) } returns Response.success(mockResponse)

        val result = remoteDataSource.fetchWeatherForecast("Cairo")

        assert(result is Resource.Success)
        assertEquals("Cairo", (result as Resource.Success).value.city?.name)
    }

    @Test
    fun `fetchWeatherForecast returns failure when API responds with server error`() = runTest {
        coEvery { api.getWeatherForecast(city = any(), apiKey =  any()) } returns Response.error(500, "".toResponseBody())

        val result = remoteDataSource.fetchWeatherForecast("Cairo")

        assert(result is Resource.Failure)
        assert((result as Resource.Failure).networkError is NetworkError.ServerError)
    }

    @Test
    fun `fetchWeatherForecast returns failure when network connection fails`() = runTest {
        coEvery { api.getWeatherForecast(city = "Cairo", days = any(), apiKey = any()) } throws IOException("No Internet")

        val result = remoteDataSource.fetchWeatherForecast("Cairo")

        assert(result is Resource.Failure)
        assert((result as Resource.Failure).networkError is NetworkError.NetworkConnection)
    }

    @Test
    fun `fetchWeatherForecast returns failure when API key is invalid`() = runTest {
        coEvery { api.getWeatherForecast(city = "Cairo", days = any(), apiKey = any()) } returns Response.error(401, "".toResponseBody())

        val result = remoteDataSource.fetchWeatherForecast("Cairo")

        assert(result is Resource.Failure)
        assert((result as Resource.Failure).networkError is NetworkError.Unauthorized)
    }
}