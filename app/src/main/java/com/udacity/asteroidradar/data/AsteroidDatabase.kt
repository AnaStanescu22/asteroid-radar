package com.udacity.asteroidradar.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.PictureOfDay

@Database(entities = [Asteroid::class, PictureOfDay::class], version = 4, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase() {

    abstract fun asteroidDao(): AsteroidDao
    abstract fun pictureOfDayDao(): PictureDao

    companion object {
        @Volatile
        private var INSTANCE: AsteroidDatabase? = null
        fun getDatabase(context: Context): AsteroidDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AsteroidDatabase::class.java,
                    "asteroid_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}