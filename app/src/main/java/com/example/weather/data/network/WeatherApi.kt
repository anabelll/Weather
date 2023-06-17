package com.example.weather.data.network

import com.example.weather.data.model.City
import com.example.weather.data.model.WeatherDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
//Todo move appid to interceptor
interface WeatherApi {
    @GET("data/2.5/weather")
    suspend fun getWeatherDetails(
        @Query("lat") latitude: String?,
        @Query("lon") longitude: String?,
        @Query("appid") appId: String? = "e4bd0445ef29888357772b3fee34f7a3"
    ): Response<WeatherDetails?>

    @GET("geo/1.0/direct")
    suspend fun getCities(
        @Query("q") search: String,
        @Query("limit") limit: Int = 10,
        @Query("appid") appId: String? = "e4bd0445ef29888357772b3fee34f7a3"
    ): Response<List<City>?>
}