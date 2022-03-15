package com.udacity.asteroidradar.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.model.Asteroid

@Dao
interface AsteroidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asteroid: List<Asteroid>)

    @Query("SELECT * from asteroid_table")
    suspend fun getAsteroids(): List<Asteroid>

}