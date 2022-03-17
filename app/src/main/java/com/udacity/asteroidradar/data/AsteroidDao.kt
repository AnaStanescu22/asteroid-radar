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

    @Query("SELECT * FROM asteroid_table WHERE closeApproachDate >= :startDay AND closeApproachDate <= :endDay ORDER BY closeApproachDate")
    suspend fun getAsteroidsFromThisWeek(startDay: String, endDay: String): List<Asteroid>

    @Query("SELECT * FROM asteroid_table WHERE closeApproachDate = :today ")
    suspend fun getAsteroidToday(today: String): List<Asteroid>
}