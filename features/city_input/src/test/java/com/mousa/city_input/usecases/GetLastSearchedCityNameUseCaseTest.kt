package com.mousa.city_input.usecases

import com.mousa.city_input.utils.MainDispatcherRule
import com.mousa.core.domain.util.BaseState
import com.mousa.core.domain.util.Resource
import com.mousa.data.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetLastSearchedCityNameUseCaseTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var getLastSearchedCityNameUseCase: GetLastSearchedCityNameUseCase
    private val repository: WeatherRepository = mockk()

    @Before
    fun setUp() {
        getLastSearchedCityNameUseCase = GetLastSearchedCityNameUseCase(repository)
    }

    @Test
    fun `invoke should return last searched city`() = runTest {
        coEvery { repository.getLastSearchedCity() } returns Resource.Success("Cairo")

        val result = getLastSearchedCityNameUseCase()

        assert(result is BaseState.Success)
        assertEquals("Cairo", (result as BaseState.Success).data)
    }

    @Test
    fun `invoke should return failure when repository fails`() = runTest {
        coEvery { repository.getLastSearchedCity() } returns Resource.Failure()

        val result = getLastSearchedCityNameUseCase()

        assert(result is BaseState.Failure)
    }
}
