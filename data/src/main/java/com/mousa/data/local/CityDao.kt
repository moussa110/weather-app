package com.mousa.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CityDao {
    @Query("SELECT cityName FROM cities LIMIT 1")
    fun getLastSearchedCity(): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLastSearchedCity(city: CityEntity)
}