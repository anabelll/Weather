package com.example.weather.presentation.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.weather.data.model.WeatherAppResult
import com.example.weather.data.model.WeatherDetails
import com.example.weather.presentation.vm.WeatherViewModel
import com.example.weather.presentation.ui.theme.WeatherTheme
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            requestWeatherDetailsBasedOnLocation()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //if user has previously searched city, then location based weather is not loaded
        if (viewModel.shouldRequestPermissions())
            requestWeatherDetailsBasedOnLocation()
        else
            viewModel.getLastSearchWeatherDetails()
        setContent {
            WeatherTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(viewModel)
                }
            }
        }
    }

    private fun requestWeatherDetailsBasedOnLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    viewModel.getWeatherDetails(
                        location?.latitude.toString(),
                        location?.longitude.toString()
                    )
                }
        } else {
            startLocationPermissionRequest()
        }
    }

    private fun startLocationPermissionRequest() {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}

@Composable
fun MainScreen(viewModel: WeatherViewModel) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val weatherDetailsState by viewModel.weatherDetailsResult.observeAsState()
    Column {
        SearchView(textState)
        Box {
            if (weatherDetailsState is WeatherAppResult.Success) {
                textState.value = TextFieldValue("")
                val details = (weatherDetailsState as WeatherAppResult.Success<WeatherDetails>).data
                //todo: Create separate model for presentation layer
                WeatherDetail(
                    details.name ?: "",
                    details.main?.temp ?: "",
                    details.weather?.getOrNull(0)?.description ?: "",
                    details.main?.feels_like ?: "",
                    "https://openweathermap.org/img/wn/${details.weather?.getOrNull(0)?.icon}@2x.png"
                )
            }
            ItemList(state = textState, viewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherTheme {
    }
}