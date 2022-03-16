package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParser
import com.udacity.asteroidradar.api.AsteroidRepository
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.AsteroidDao
import com.udacity.asteroidradar.data.AsteroidDatabase
import com.udacity.asteroidradar.data.PictureDao
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.PictureOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject


class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val repository: AsteroidRepository by lazy { AsteroidRepository() }
    private val _state: MutableStateFlow<AsteroidState> =
        MutableStateFlow(AsteroidState(true, emptyList()))

    val state = _state.asStateFlow()

    private val _navigateToDetail: MutableLiveData<Asteroid?> =
        MutableLiveData(null)
    val navigateToDetail: LiveData<Asteroid?> = _navigateToDetail

    private val _picture: MutableLiveData<PictureState> =
        MutableLiveData(PictureState(null))

    val picture: LiveData<PictureState> = _picture

    private lateinit var cachedAsteroids: List<Asteroid>

    val loadingState = state.map { value -> value.loading }

    private val asteroidDao: AsteroidDao by lazy {
        AsteroidDatabase.getDatabase(app).asteroidDao()
    }

    private val pictureDao: PictureDao by lazy {
        AsteroidDatabase.getDatabase(app).pictureOfDayDao()
    }

    init {
        viewModelScope.launch {
            val asteroids = getAsteroids()
            _state.value = AsteroidState(false, asteroids)
            cachedAsteroids = asteroids

            val pictureOfDay = getPicture()
            _picture.value = PictureState(pictureOfDay)
        }
    }

    private suspend fun getAsteroids(): List<Asteroid> = withContext(Dispatchers.IO) {
        try {
            val response = repository.service.getAsteroids()
            val gson = JsonParser().parse(response.toString()).asJsonObject

            val jo2 = JSONObject(gson.toString())
            val asteroids = parseAsteroidsJsonResult(jo2)

            asteroidDao.insert(asteroids)
            asteroidDao.getAsteroids()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    private suspend fun getPicture(): PictureOfDay? = withContext(Dispatchers.IO) {
        try {
            val picture = repository.service.getPicture()
            pictureDao.insert(picture)
            pictureDao.getPicture(picture.url)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToDetail.value = asteroid
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }

}

data class AsteroidState(val loading: Boolean, val asteroids: List<Asteroid>)

data class PictureState(val picture: PictureOfDay?)