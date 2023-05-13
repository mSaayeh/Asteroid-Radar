package com.msayeh.asteroid.api

import com.msayeh.asteroid.Asteroid
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkAsteroidsContainer(val links: List<NetworkAsteroid>)

@JsonClass(generateAdapter = true)
data class NetworkAsteroid(
    val id: Long, val codename: String, val closeApproachDate: String,
    val absoluteMagnitude: Double, val estimatedDiameter: Double,
    val relativeVelocity: Double, val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)

fun NetworkAsteroidsContainer.asDomainModel(): List<Asteroid> {
    return links.map {
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