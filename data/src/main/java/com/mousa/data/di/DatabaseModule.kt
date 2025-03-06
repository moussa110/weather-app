package com.mousa.data.di

import android.content.Context
import androidx.room.Room
import com.mousa.data.local.AppDatabase
import com.mousa.data.local.CityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "weather_db").build()

    @Provides
    fun provideCityDao(db: AppDatabase): CityDao = db.cityDao()
}