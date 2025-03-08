package com.mousa.city_input.viewmodel

import app.cash.turbine.test
import com.mousa.city_input.presentation.EnterCityViewModel
import com.mousa.city_input.state.CityInputState
import com.mousa.city_input.usecases.CacheCityUseCase
import com.mousa.city_input.utils.MainDispatcherRule
import com.mousa.core.domain.util.BaseState
import com.mousa.core.domain.util.Resource
import io.mockk.coEvery
import io.mockk.coVerifyAll
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class EnterCityViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: EnterCityViewModel
    private val cacheCityUseCase: CacheCityUseCase = mockk(relaxed = true)

    @Before
    fun setUp() {
        viewModel = EnterCityViewModel(cacheCityUseCase)
    }

    @Test
    fun `updateCityName should update state correctly`() = runTest {
        viewModel.updateCityName("Cairo")
        val state = viewModel.cityState.first()
        assert(state is BaseState.Success)
    }

    @Test
    fun `saveCity should call CacheCityUseCase`() = runTest {
        coEvery { cacheCityUseCase.invoke("Cairo") } returns Resource.Success(Unit)
        viewModel.saveCity("Cairo")
        coVerifyAll { cacheCityUseCase.invoke("Cairo") }
    }
}
