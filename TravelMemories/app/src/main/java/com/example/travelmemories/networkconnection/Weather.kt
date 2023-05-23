package com.example.travelmemories.networkconnection

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Weather (
    val lat:Double,
    var lon: Double,
    val current: CurrentWeather
        )

@JsonClass(generateAdapter = true)
data class CurrentWeather (
    val dt: Int,
    val temp:Double,
    val feels_like:Double
)