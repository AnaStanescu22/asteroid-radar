package com.udacity.asteroidradar.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "asteroid_table")
@Parcelize
data class Asteroid(
    @PrimaryKey val id: Long, val codename: String, val closeApproachDate: String,
    val absoluteMagnitude: Double, val estimatedDiameter: Double,
    val relativeVelocity: Double, val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
) : Parcelable