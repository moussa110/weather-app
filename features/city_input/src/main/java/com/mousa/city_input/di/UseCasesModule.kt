package com.mousa.city_input.di

import com.mousa.city_input.usecases.CacheCityUseCase
import com.mousa.city_input.usecases.GetLastSearchedCityNameUseCase
import com.mousa.data.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {
    @Provides
    fun provideGetCurrentWeatherUseCase(repository: WeatherRepository): CacheCityUseCase {
        return CacheCityUseCase(repository)
    }
    @Provides
    fun provideGetLastSearchedCityUseCase(repository: WeatherRepository): GetLastSearchedCityNameUseCase {
        return GetLastSearchedCityNameUseCase(repository)
    }

}