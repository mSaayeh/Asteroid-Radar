package com.msayeh.asteroid.domain

data class ImageOfTheDay(
    val url: String,
    private val media_type: String,
    val title: String
)