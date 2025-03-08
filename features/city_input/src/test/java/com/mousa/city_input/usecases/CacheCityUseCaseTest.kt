package com.mousa.city_input.usecases

import com.mousa.city_input.utils.MainDispatcherRule
import com.mousa.core.domain.util.Resource
import com.mousa.data.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CacheCityUseCaseTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var cacheCityUseCase: CacheCityUseCase
    private val repository: WeatherRepository = mockk()

    @Before
    fun setUp() {
        cacheCityUseCase = CacheCityUseCase(repository)
    }

    @Test
    fun `invoke should call repository and return success`() = runTest {
        val city = "Cairo"
        coEvery { repository.saveLastSearchedCity(city) } returns Resource.Success(Unit)

        val result = cacheCityUseCase(city)

        assert(result is Resource.Success)
        coVerify { repository.saveLastSearchedCity(city) }
    }

    @Test
    fun `invoke should return failure when repository fails`() = runTest {
        val city = "Cairo"
        coEvery { repository.saveLastSearchedCity(city) } returns Resource.Failure()

        val result = cacheCityUseCase(city)

        assert(result is Resource.Failure)
        coVerify { repository.saveLastSearchedCity(city) }
    }
}
