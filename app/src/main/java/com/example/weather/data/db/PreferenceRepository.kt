package com.example.weather.data.db

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private const val APP_PREFERENCES = "APP_PREFERENCES"
private const val LAST_SEARCH_LATITUDE = "LAST_SEARCH_LATITUDE"
private const val LAST_SEARCH_LONGITUDE = "LAST_SEARCH_LONGITUDE"

//Todo: substitute with datastore
@Singleton
class PreferenceRepository @Inject constructor(@ApplicationContext context: Context) {
    private val preferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    private val editor = preferences.edit()

    fun saveLastSearchData(latitude: String?, longitude: String?) {
        editor.putString(LAST_SEARCH_LATITUDE, latitude)
        editor.putString(LAST_SEARCH_LONGITUDE, longitude)
        editor.commit()
    }

    fun getLastSearchLatitude() = preferences?.getString(LAST_SEARCH_LONGITUDE, null)

    fun getLastSearchLongitude() = preferences?.getString(LAST_SEARCH_LONGITUDE, null)
}