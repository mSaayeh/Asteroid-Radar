package com.msayeh.asteroid.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.msayeh.asteroid.Asteroid
import com.msayeh.asteroid.Constants
import com.msayeh.asteroid.api.Network
import com.msayeh.asteroid.api.asDomainModel
import com.msayeh.asteroid.api.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidsRepository {
    private val _asteroids: MutableLiveData<List<Asteroid>> = MutableLiveData()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    suspend fun updateAsteroids() {
        withContext(Dispatchers.IO) {
            val rawData = Network.feed.getAsteroids(Constants.API_KEY)
            _asteroids.postValue(parseAsteroidsJsonResult(JSONObject(rawData)))
        }
    }
}