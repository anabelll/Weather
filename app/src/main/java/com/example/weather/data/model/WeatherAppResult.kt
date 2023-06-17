package com.example.weather.data.model

sealed class WeatherAppResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : WeatherAppResult<T>()
    data class Error(val throwable: Throwable) : WeatherAppResult<Nothing>()
    object Loading : WeatherAppResult<Nothing>()
}