package com.example.weather.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.weather.data.model.WeatherDetails

@Composable
fun WeatherDetail(
    cityName: String,
    temperature: String,
    weatherDesc: String,
    feelsLike: String,
    logoUrl: String
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(4)
    ) {
        Row(Modifier.padding(8.dp)) {
            Column {
                Text(text = cityName)
                Spacer(Modifier.height(16.dp))
                Text(text = "Temperature $temperature°F")
                Spacer(Modifier.height(16.dp))
                Text(text = weatherDesc)
                Spacer(Modifier.height(16.dp))
                Text(text = "Feels like $feelsLike°F")
                Spacer(Modifier.height(16.dp))
            }
            AsyncImage(
                model = logoUrl,
                contentDescription = "",
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
            )
        }
    }
}
