package com.example.weather.domain

import com.example.weather.data.db.PreferenceRepository
import com.example.weather.data.network.WeatherApiHelper
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val apiHelper: WeatherApiHelper,
    private val preferenceRepository: PreferenceRepository
) {
    suspend fun getCities(search: String) = apiHelper.getCities("$search,US")

    suspend fun getWeatherDetails(latitude: String?, longitude: String?) =
        apiHelper.getWeatherDetails(latitude, longitude)

    suspend fun getLastSearchWeatherDetails() =
        getWeatherDetails(
            preferenceRepository.getLastSearchLatitude(),
            preferenceRepository.getLastSearchLongitude()
        )

    fun saveLastSearchData(latitude: String?, longitude: String?) {
        preferenceRepository.saveLastSearchData(latitude, longitude)
    }

    fun isLastSearchDataPresent() = preferenceRepository.getLastSearchLongitude()
        ?.isNotEmpty() == true && preferenceRepository.getLastSearchLatitude()?.isNotEmpty() == true
}