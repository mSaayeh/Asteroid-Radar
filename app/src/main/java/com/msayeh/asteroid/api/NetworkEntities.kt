package com.msayeh.asteroid.api

import com.msayeh.asteroid.database.DatabaseAsteroid
import com.msayeh.asteroid.database.DatabaseImageOfTheDay
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkAsteroidsContainer(val asteroids: MutableList<NetworkAsteroid> = mutableListOf())

@JsonClass(generateAdapter = true)
data class NetworkAsteroid(
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)

@JsonClass(generateAdapter = true)
data class NetworkImageOfTheDay(
    val url: String,
    val media_type: String,
    val title: String
) {
    enum class MediaType {IMAGE, NON_IMAGE}

    val mediaType: MediaType = when(media_type) {
        "image" -> MediaType.IMAGE
        else -> MediaType.NON_IMAGE
    }
}

fun NetworkAsteroidsContainer.asDatabaseModel(): Array<DatabaseAsteroid> {
    return asteroids.map {
        DatabaseAsteroid(
            it.id,
            it.codename,
            it.closeApproachDate,
            it.absoluteMagnitude,
            it.estimatedDiameter,
            it.relativeVelocity,
            it.distanceFromEarth,
            it.isPotentiallyHazardous
        )
    }.toTypedArray()
}

fun NetworkImageOfTheDay.asDatabaseModel(): DatabaseImageOfTheDay {
    return DatabaseImageOfTheDay(
        url = url,
        media_type = media_type,
        title = title
    )
}