package com.udacity.asteroidradar.api

import com.google.gson.JsonObject
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.model.PictureOfDay
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class AsteroidRepository {

    val service: Service

    init {
        val retrofit: Retrofit = Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(Service::class.java)
    }

    interface Service {
        @GET("neo/rest/v1/feed/")
        suspend fun getAsteroids(
            @Query("start_date") startDate: String = "2022-03-15",
            @Query("end_date") endDate: String = "2022-03-22",
            @Query("api_key") apiKey: String = Constants.API_KEY
        ): JsonObject

        @GET("planetary/apod/")
        suspend fun getPicture(
            @Query("api_key") apiKey: String = Constants.API_KEY
        ): PictureOfDay
    }
}