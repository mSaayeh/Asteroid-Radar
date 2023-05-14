package com.msayeh.asteroid.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.msayeh.asteroid.domain.Asteroid
import com.msayeh.asteroid.domain.ImageOfTheDay

@Entity(tableName = "asteroids")
data class DatabaseAsteroid(
    @PrimaryKey
    val id: Long,
    val codename: String, val closeApproachDate: String,
    val absoluteMagnitude: Double, val estimatedDiameter: Double,
    val relativeVelocity: Double, val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)

@Entity(tableName = "images")
data class DatabaseImageOfTheDay(
    @PrimaryKey
    val id: Long = 0,
    val url: String,
    val media_type: String,
    val title: String
)

fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(
            it.id,
            it.codename,
            it.closeApproachDate,
            it.absoluteMagnitude,
            it.estimatedDiameter,
            it.relativeVelocity,
            it.distanceFromEarth,
            it.isPotentiallyHazardous
        )
    }
}

fun DatabaseImageOfTheDay.asDomainModel(): ImageOfTheDay {
    return ImageOfTheDay(
        url,
        media_type,
        title
    )
}