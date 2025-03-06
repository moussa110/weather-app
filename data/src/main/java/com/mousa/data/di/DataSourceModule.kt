package com.mousa.data.di

import com.mousa.data.local.CityDao
import com.mousa.data.local.WeatherLocalDataSource
import com.mousa.data.remote.WeatherApi
import com.mousa.data.remote.WeatherRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun provideRemoteDataSource(api: WeatherApi): WeatherRemoteDataSource {
        return WeatherRemoteDataSource(api)
    }

    @Provides
    fun provideLocalDataSource(cityDao: CityDao): WeatherLocalDataSource {
        return WeatherLocalDataSource(cityDao)
    }
}