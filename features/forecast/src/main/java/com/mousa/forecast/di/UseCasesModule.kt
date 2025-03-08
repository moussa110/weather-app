package com.mousa.forecast.di

import com.mousa.data.repository.WeatherRepository
import com.mousa.forecast.domain.usecases.GetForecastUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {
    @Provides
    fun provideGetForecastUseCase(repository: WeatherRepository): GetForecastUseCase {
        return GetForecastUseCase(repository)
    }
}