package com.msayeh.asteroid.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Dao
interface AsteroidDao {
    @Query("SELECT * FROM asteroids ORDER BY closeApproachDate")
    fun getAllAsteroids(): LiveData<List<DatabaseAsteroid>>

    @Query("SELECT * FROM asteroids WHERE closeApproachDate = :current ORDER BY closeApproachDate")
    fun getTodayAsteroids(current: String): LiveData<List<DatabaseAsteroid>>

    @Query("SELECT * FROM asteroids WHERE closeApproachDate BETWEEN :start AND :end ORDER BY closeApproachDate")
    fun getAsteroidsInRange(start: String, end: String): LiveData<List<DatabaseAsteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroid: DatabaseAsteroid)

    @Query("DELETE FROM asteroids WHERE closeApproachDate<:today")
    fun deleteOldAsteroids(today: String)
}

@Dao
interface ImageOfTheDayDao {
    @Query("SELECT * FROM images LIMIT 1")
    fun getImageOfTheDay(): LiveData<DatabaseImageOfTheDay?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImage(imageOfTheDay: DatabaseImageOfTheDay)
}

@Database(entities = [DatabaseAsteroid::class, DatabaseImageOfTheDay::class], version = 1)
abstract class AsteroidsDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
    abstract val imagesDao: ImageOfTheDayDao

    companion object {
        private var INSTANCE: AsteroidsDatabase? = null

        fun getInstance(context: Context): AsteroidsDatabase {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidsDatabase::class.java,
                        "asteroids_database"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}