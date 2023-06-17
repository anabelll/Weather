package com.example.weather.data.network

import com.example.weather.data.model.City
import com.example.weather.data.model.WeatherAppResult
import com.example.weather.data.model.WeatherDetails

interface WeatherApiHelper {
    suspend fun getWeatherDetails(
        latitude: String?,
        longitude: String?
    ): WeatherAppResult<WeatherDetails>?

    suspend fun getCities(
        search: String
    ): WeatherAppResult<List<City>>
}