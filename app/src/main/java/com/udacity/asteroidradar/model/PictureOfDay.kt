package com.udacity.asteroidradar.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "picture_table")
@Parcelize
data class PictureOfDay(
    @ColumnInfo(name = "media_type") @Json(name = "media_type") val mediaType: String? = "",
    @ColumnInfo(name = "title") val title: String? = "",
    @PrimaryKey val url: String
) : Parcelable