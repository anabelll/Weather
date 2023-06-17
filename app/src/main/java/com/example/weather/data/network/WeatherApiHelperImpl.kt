package com.example.weather.data.network

import com.example.weather.data.model.City
import com.example.weather.data.model.WeatherAppResult
import com.example.weather.data.model.WeatherDetails
import javax.inject.Inject

class WeatherApiHelperImpl @Inject constructor(private val apiService: WeatherApi) : WeatherApiHelper {
    override suspend fun getWeatherDetails(
        latitude: String?,
        longitude: String?
    ): WeatherAppResult<WeatherDetails>? {
        return try {
            apiService.getWeatherDetails(latitude, longitude).let { result ->
                if (result.isSuccessful && result.body() != null) {
                    WeatherAppResult.Success(result.body()!!)
                } else {
                    WeatherAppResult.Error(Throwable(result.errorBody()?.toString()))
                }
            }
        } catch (e: Throwable) {
            WeatherAppResult.Error(e)
        }
    }

    override suspend fun getCities(search: String): WeatherAppResult<List<City>> {
        return try {
            apiService.getCities(search).let { result ->
                if (result.isSuccessful && result.body() != null) {
                    WeatherAppResult.Success(result.body()!!)
                } else {
                    WeatherAppResult.Error(Throwable(result.errorBody()?.toString()))
                }
            }
        } catch (e: Throwable) {
            WeatherAppResult.Error(e)
        }
    }
}