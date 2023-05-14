package com.msayeh.asteroid.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.msayeh.asteroid.domain.Asteroid
import com.msayeh.asteroid.utlis.Constants
import com.msayeh.asteroid.domain.ImageOfTheDay
import com.msayeh.asteroid.api.Network
import com.msayeh.asteroid.api.NetworkImageOfTheDay
import com.msayeh.asteroid.api.asDatabaseModel
import com.msayeh.asteroid.utlis.getEndOfWeekFormatted
import com.msayeh.asteroid.utlis.getTodayFormattedDate
import com.msayeh.asteroid.utlis.parseAsteroidsJsonResult
import com.msayeh.asteroid.database.AsteroidsDatabase
import com.msayeh.asteroid.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidsRepository(private val database: AsteroidsDatabase) {

    val asteroids: LiveData<List<Asteroid>> = database.asteroidDao.getAllAsteroids().map {
        it.asDomainModel()
    }

    val todayAsteroids: LiveData<List<Asteroid>> = database.asteroidDao.getTodayAsteroids(
        getTodayFormattedDate()
    ).map {
        it.asDomainModel()
    }

    val weekAsteroids: LiveData<List<Asteroid>> = database.asteroidDao.getAsteroidsInRange(
        getTodayFormattedDate(),
        getEndOfWeekFormatted()
    ).map {
        it.asDomainModel()
    }

    val imageOfTheDay: LiveData<ImageOfTheDay?> = database.imagesDao.getImageOfTheDay().map {
        it?.asDomainModel()
    }

    suspend fun updateCaching() {
        updateAsteroids()
        updateImageOfTheDay()
        clearOldCache()
    }

    private suspend fun clearOldCache() {
        withContext(Dispatchers.IO) {
            database.asteroidDao.deleteOldAsteroids(getTodayFormattedDate())
        }
    }

    private suspend fun updateAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                val rawData = Network.feed.getAsteroids(Constants.API_KEY)
                val data = parseAsteroidsJsonResult(JSONObject(rawData))
                database.asteroidDao.insertAll(*data.asDatabaseModel())
            } catch (_: Exception) {
                return@withContext
            }
        }
    }

    private suspend fun updateImageOfTheDay() {
        withContext(Dispatchers.IO) {
            try {
                val imageOfTheDay = Network.feed.getImageOfTheDay(Constants.API_KEY)
                if (imageOfTheDay.mediaType == NetworkImageOfTheDay.MediaType.IMAGE)
                    database.imagesDao.insertImage(imageOfTheDay.asDatabaseModel())
            } catch (_: Exception) {
                return@withContext
            }
        }
    }
}