package com.mousa.data.local

import com.mousa.core.utils.Resource
import com.mousa.data.utils.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherLocalDataSourceTest {

    @get:Rule
    val testDispatcherRule = MainDispatcherRule()

    private lateinit var localDataSource: WeatherLocalDataSource
    private val cityDao: CityDao = mockk(relaxed = true)

    @Before
    fun setUp() {
        localDataSource = WeatherLocalDataSource(cityDao)
    }

    @Test
    fun `getLastSearchedCity returns data when available`() = runTest {
        every { cityDao.getLastSearchedCity() } returns "Cairo"

        val result = localDataSource.getLastSearchedCity()

        assert(result is Resource.Success)
        assertEquals("Cairo", (result as Resource.Success).value)
    }

    @Test
    fun `getLastSearchedCity returns failure when data is null`() = runTest {
        every { cityDao.getLastSearchedCity() } returns null

        val result = localDataSource.getLastSearchedCity()

        assert(result is Resource.Failure)
    }

}