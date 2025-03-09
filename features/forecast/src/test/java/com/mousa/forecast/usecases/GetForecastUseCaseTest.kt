package com.mousa.forecast.usecases
import com.mousa.core.domain.util.BaseState
import com.mousa.core.domain.util.NetworkError
import com.mousa.core.domain.util.Resource
import com.mousa.data.remote.model.ForecastResponse
import com.mousa.data.repository.WeatherRepository
import com.mousa.forecast.domain.mappers.mapToDayWeatherList
import com.mousa.forecast.domain.model.DayWeather
import com.mousa.forecast.domain.usecases.GetForecastUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetForecastUseCaseTest {

    private lateinit var useCase: GetForecastUseCase
    private val repository: WeatherRepository = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        useCase = GetForecastUseCase(repository)
    }

    @Test
    fun `invoke should return success when repository returns forecast data`() = runTest {
        val mockForecastResponse = mockk<ForecastResponse>(relaxed = true)
        val mockWeatherList = mockForecastResponse.mapToDayWeatherList()

        coEvery { repository.getWeatherForecast("Cairo") } returns Resource.Success(mockForecastResponse)

        val result = useCase("Cairo").first()

        assert(result is BaseState.Success)
        assertEquals(mockWeatherList, (result as BaseState.Success).data)
    }

    @Test
    fun `invoke should return failure when repository returns error`() = runTest {
        coEvery { repository.getWeatherForecast("Cairo") } returns Resource.Failure(NetworkError.General())

        val result = useCase("Cairo").first()

        assert(result is BaseState.Failure)
        assertEquals(NetworkError.General().message, (result as BaseState.Failure).errorMessage)
    }
}