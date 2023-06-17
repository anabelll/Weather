package com.example.weather.di

import com.example.weather.data.db.PreferenceRepository
import com.example.weather.data.network.WeatherApi
import com.example.weather.domain.WeatherRepository
import com.example.weather.data.network.WeatherApiHelper
import com.example.weather.data.network.WeatherApiHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    private const val BASE_URL = "https://api.openweathermap.org/"

    private const val APP_ID_PARAM = "appid"
    private const val APP_ID = "e4bd0445ef29888357772b3fee34f7a3"

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.HEADERS
        }

    @Singleton
    @Provides
    fun provideInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .addHeader(APP_ID_PARAM, APP_ID)
            .build()
        chain.proceed(request)
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor { chain ->
                val request: Request =
                    chain.request()
                val url =
                    chain.request().url.newBuilder().addQueryParameter(APP_ID_PARAM, APP_ID).build()
                request
                    .newBuilder()
                    .url(url)
                    .build()
                chain.proceed(request)
            }
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): WeatherApi =
        retrofit.create(WeatherApi::class.java)

    @Provides
    @Singleton
    fun provideWeatherApiHelper(apiHelper: WeatherApiHelperImpl): WeatherApiHelper = apiHelper

    @Singleton
    @Provides
    fun providesRepository(
        apiHelper: WeatherApiHelper,
        preferenceRepository: PreferenceRepository
    ) = WeatherRepository(apiHelper, preferenceRepository)
}