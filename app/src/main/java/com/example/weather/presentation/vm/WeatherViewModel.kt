package com.example.weather.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.model.City
import com.example.weather.data.model.WeatherAppResult
import com.example.weather.data.model.WeatherDetails
import com.example.weather.domain.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) :
    ViewModel() {
    private val _citiesResult = MutableLiveData<WeatherAppResult<List<City>>>()
    val citiesResult: LiveData<WeatherAppResult<List<City>>> = _citiesResult

    private val _weatherDetailsResult = MutableLiveData<WeatherAppResult<WeatherDetails>>()
    val weatherDetailsResult: LiveData<WeatherAppResult<WeatherDetails>> = _weatherDetailsResult

    fun getCities(search: String) {
        viewModelScope.launch {
            _citiesResult.postValue(WeatherAppResult.Loading)
            repository.getCities(search).let { _citiesResult.postValue(it) }
        }
    }

    fun getWeatherDetails(latitude: String?, longitude: String?) {
        viewModelScope.launch {
            _weatherDetailsResult.postValue(WeatherAppResult.Loading)
            repository.getWeatherDetails(latitude, longitude)
                .let {
                    _weatherDetailsResult.postValue(it)
                    if (it is WeatherAppResult.Success) {
                        repository.saveLastSearchData(latitude, longitude)
                    }
                }
        }
    }

    fun getLastSearchWeatherDetails() {
        viewModelScope.launch {
            _weatherDetailsResult.postValue(WeatherAppResult.Loading)
            repository.getLastSearchWeatherDetails()
                .let {
                    _weatherDetailsResult.postValue(it)
                }
        }
    }

    fun shouldRequestPermissions() = !repository.isLastSearchDataPresent()
}