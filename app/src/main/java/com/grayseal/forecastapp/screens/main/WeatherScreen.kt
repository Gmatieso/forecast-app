package com.grayseal.forecastapp.screens.main

import GetCurrentLocation
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.grayseal.forecastapp.utils.getCurrentDate
import getLocationName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WeatherScreen(navController: NavController, mainViewModel: MainViewModel, context: Context) {

    val gradientColors = listOf(Color(0xFF060620), colors.primary)
    val latitude = remember {
        mutableStateOf(360.0)
    }
    val longitude = remember {
        mutableStateOf(360.0)
    }
    var locationName by remember {
        mutableStateOf("")
    }
    // cancelled when the composition is disposed
    val scope = rememberCoroutineScope()
    if (latitude.value != 360.0 && longitude.value != 360.0) {
        LaunchedEffect(latitude, longitude) {
            scope.launch {
                locationName = getLocationName(context, latitude, longitude)
            }
        }
    }
    val commaIndex = locationName.indexOf(",")
    val name = if (commaIndex >= 0) {
        locationName.substring(0, commaIndex)
    } else {
        locationName
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = gradientColors,
                    start = Offset(0f, Float.POSITIVE_INFINITY),
                    end = Offset(Float.POSITIVE_INFINITY, 0f)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp, bottom = 20.dp),
        ) {
            Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp), horizontalArrangement = Arrangement.Center) {
                Text(name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            }
            MainScreen()
            GetCurrentLocation(
                mainViewModel = mainViewModel,
                context = context,
                latitude = latitude,
                longitude = longitude
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreen() {
    Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp), horizontalArrangement = Arrangement.Center) {
        Text(getCurrentDate(), style = MaterialTheme.typography.bodySmall)
    }
    Spacer(modifier = Modifier.height(30.dp))
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        ElevatedButton(onClick = { /*TODO*/ }, shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.elevatedButtonColors(containerColor = colors.secondary, contentColor = Color.White), elevation = ButtonDefaults.buttonElevation(6.dp)) {
            Text("Forecast")

        }
        ElevatedButton(onClick = { /*TODO*/ }, shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.elevatedButtonColors(containerColor = colors.primaryVariant, contentColor = Color.White), elevation = ButtonDefaults.buttonElevation(6.dp)) {
            Text("Air Quality")

        }
    }
}

suspend fun getLocationName(
    context: Context,
    latitude: MutableState<Double>,
    longitude: MutableState<Double>
): String {
    // To specify that the geocoding operation should be performed on the IO dispatcher
    return withContext(Dispatchers.IO) {
        /*
        withContext function will automatically suspend the current coroutine and resume it
        when the operation is complete, allowing other operations to be performed in the meantime
         */
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude.value, longitude.value, 1)
        var locationName: String = ""
        if (addresses != null && addresses.size > 0) {
            locationName = addresses[0].locality
        }
        locationName
    }
}



