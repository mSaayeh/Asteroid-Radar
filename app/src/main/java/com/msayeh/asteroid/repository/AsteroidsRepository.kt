package com.msayeh.asteroid.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.msayeh.asteroid.Asteroid
import com.msayeh.asteroid.Constants
import com.msayeh.asteroid.api.ImageOfTheDay
import com.msayeh.asteroid.api.Network
import com.msayeh.asteroid.api.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidsRepository {
    private val _asteroids: MutableLiveData<List<Asteroid>> = MutableLiveData()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    private val _imageOfTheDay = MutableLiveData<ImageOfTheDay>()
    val imageOfTheDay: LiveData<ImageOfTheDay>
        get() = _imageOfTheDay

    suspend fun updateAsteroids() {
        withContext(Dispatchers.IO) {
            val rawData = Network.feed.getAsteroids(Constants.API_KEY)
            _asteroids.postValue(parseAsteroidsJsonResult(JSONObject(rawData)))
        }
    }

    suspend fun updateImageOfTheDay() {
        withContext(Dispatchers.IO) {
            val imageOfTheDay = Network.feed.getImageOfTheDay(Constants.API_KEY)
            if (imageOfTheDay.mediaType == ImageOfTheDay.MediaType.IMAGE)
                _imageOfTheDay.postValue(imageOfTheDay)
        }
    }
}