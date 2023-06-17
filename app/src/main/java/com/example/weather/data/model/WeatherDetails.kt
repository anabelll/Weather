package com.example.weather.data.model

data class WeatherDetails(
    val weather: List<Weather>?,
    val main: Temperature?,
    val name: String?
)

data class Weather(
    val id: Int?,
    val main: String?,
    val description: String?,
    val icon: String?
)

data class Temperature(
    val temp: String?,
    val feels_like: String?,
    val temp_min: String?,
    val temp_max: String?,
)